package org.resumebase.storage;

import org.resumebase.exceptions.NotExistStorageException;
import org.resumebase.model.ContactType;
import org.resumebase.model.Resume;
import org.resumebase.sql.SQLHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLStorage implements Storage {
    private final SQLHelper sqlHelper;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SQLHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.launchStatement("TRUNCATE TABLE resume CASCADE", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {

        sqlHelper.<Void>launchTransaction(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO resume (uuid, full_name)" +
                    " VALUES (?,?)")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO contact (resume_uuid, type, value)" +
                    " VALUES (?,?,?)")) {
                insertContactsFromDBtoResume(resume, preparedStatement);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.launchStatement("" +
                        "SELECT * FROM resume r" +
                        " LEFT JOIN contact c " +
                        "  ON r.uuid = c.resume_uuid" +
                        " WHERE r.uuid =?;",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, resultSet.getString("full_name"));
                    do {
                        addContactToResume(resultSet, resume);
                    } while (resultSet.next());
                    return resume;
                });
    }


    @Override
    public void update(Resume resume) {
        sqlHelper.<Void>launchTransaction(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE resume SET full_name=? WHERE uuid=?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE contact SET type=?, value=? WHERE resume_uuid=?")) {
                insertContactsFromDBtoResume(resume, preparedStatement);

            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>launchStatement("DELETE FROM resume WHERE uuid=?", preparedStatement -> {
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
                            resultSet.getString("uuid").trim(),
                            resultSet.getString("full_name").trim());
                    try (PreparedStatement preparedStatementContacts = connection.prepareStatement(
                            "SELECT * FROM contact")) {
                        ResultSet resultSetContacts = preparedStatementContacts.executeQuery();
                        while (resultSetContacts.next()) {
                            addContactToResume(resultSetContacts, resume);
                        }
                    }
                    sortedList.add(resume);
                }
            }
            return sortedList;
        });
    }

    private void insertContactsFromDBtoResume(Resume resume, PreparedStatement preparedStatement) throws SQLException {
        for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.setString(2, contact.getKey().name());
            preparedStatement.setString(3, contact.getValue());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }


    private static void addContactToResume(ResultSet resultSet, Resume resume) throws SQLException {
        String value = resultSet.getString("value");
        ContactType type = ContactType.valueOf(resultSet.getString("type"));
        resume.addContact(type, value);
    }

    private void deleteContacts(Resume r) {
        sqlHelper.launchStatement("DELETE FROM contact WHERE resume_uuid=?", preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.execute();
            return null;
        });
    }

}
