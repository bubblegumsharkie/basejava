package org.resumebase.storage;

import org.resumebase.exceptions.NotExistStorageException;
import org.resumebase.model.*;
import org.resumebase.sql.SQLHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLStorage implements Storage {
    private static final String STRING_SPLITTER = "/n";
    private final SQLHelper sqlHelper;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sqlHelper = new SQLHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.launchStatement("TRUNCATE TABLE resume CASCADE;", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {

        sqlHelper.<Void>launchTransaction(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO resume (uuid, full_name)" +
                    " VALUES (?,?);")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
            }
            insertContactsToResume(resume, connection);
            insertSectionsToResume(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.launchTransaction(connection -> {
            Resume resume;
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM resume r" +
                    " LEFT JOIN contact c " +
                    "  ON r.uuid = c.resume_uuid " +
                    " LEFT JOIN section s " +
                    "  ON r.uuid = s.resume_uuid " +
                    " WHERE r.uuid =?;")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
                do {
                    addContactsToResume(resume, resultSet);
                    addSectionsToResume(resume, resultSet);
                } while (resultSet.next());
            }
            return resume;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.launchTransaction(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE resume SET full_name=? WHERE uuid=?;")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContactsFromResume(resume, connection);
            deleteSectionsFromResume(resume, connection);
            insertContactsToResume(resume, connection);
            insertSectionsToResume(resume, connection);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>launchStatement("DELETE FROM resume WHERE uuid=?;", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public int size() {
        return sqlHelper.launchStatement("SELECT COUNT(*) FROM resume;", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> sortedList = new ArrayList<>();
        return sqlHelper.launchTransaction(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM resume r ORDER BY full_name, uuid;")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Resume resume = new Resume(
                            resultSet.getString("uuid"),
                            resultSet.getString("full_name"));
                    try (PreparedStatement preparedStatementContacts = connection.prepareStatement(
                            "SELECT * FROM contact WHERE resume_uuid=?;")) {
                        preparedStatementContacts.setString(1, resume.getUuid());
                        ResultSet resultSetContacts = preparedStatementContacts.executeQuery();
                        while (resultSetContacts.next()) {
                            addContactsToResume(resume, resultSetContacts);
                        }
                    }
                    try (PreparedStatement preparedStatementSections = connection.prepareStatement(
                            "SELECT * FROM section WHERE resume_uuid=?;")) {
                        preparedStatementSections.setString(1, resume.getUuid());
                        ResultSet resultSetSections = preparedStatementSections.executeQuery();
                        while (resultSetSections.next()) {
                            addSectionsToResume(resume, resultSetSections);
                        }
                    }
                    sortedList.add(resume);
                }
            }
            return sortedList;
        });
    }

    private void addContactsToResume(Resume resume, ResultSet resultSetContacts) throws SQLException {
        String value = resultSetContacts.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(resultSetContacts.getString("type"));
            resume.addContact(type, value);
        }
    }

    private void addSectionsToResume(Resume resume, ResultSet resultSetSections) throws SQLException {
        String value = resultSetSections.getString("section_value");
        List<String> values;
        if (value != null) {
            SectionType sectionType = SectionType.valueOf(resultSetSections.getString("section_type"));
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(sectionType, new TextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    values = List.of(value.split(STRING_SPLITTER));
                    resume.addSection(sectionType, new ListSection(values));
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    // TODO: Implement OrganizationSection here
                    break;
            }
        }
    }

    private void insertContactsToResume(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?);")) {
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, contact.getKey().name());
                preparedStatement.setString(3, contact.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void insertSectionsToResume(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(("" +
                "INSERT INTO section (resume_uuid, section_type, section_value) VALUES (?,?,?);"))) {
            for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
                AbstractSection sectionValue = section.getValue();
                SectionType sectionType = section.getKey();
                String resultString = "";
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resultString = ((TextSection) sectionValue).getText();
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection value = (ListSection) sectionValue;
                        List<String> items = value.getItems();
                        resultString = String.join(STRING_SPLITTER, items);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        // TODO: Implement OrganizationSection here
                        break;
                }
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, section.getKey().name());
                preparedStatement.setString(3, resultString);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void deleteAttributes(Connection connection, String sql, Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private void deleteContactsFromResume(Resume resume, Connection connection) throws SQLException {
        deleteAttributes(connection, "DELETE FROM contact WHERE resume_uuid=?;", resume);
    }

    private void deleteSectionsFromResume(Resume resume, Connection connection) throws SQLException {
        deleteAttributes(connection, "DELETE FROM section WHERE resume_uuid=?;", resume);
    }

}
