package org.resumebase.storage;

import org.resumebase.exceptions.ExistStorageException;
import org.resumebase.exceptions.NotExistStorageException;
import org.resumebase.exceptions.StorageException;
import org.resumebase.model.Resume;
import org.resumebase.sql.ABlockOfCode;
import org.resumebase.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLStorage implements Storage {

    public final ConnectionFactory connectionFactory;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        launchStatement("TRUNCATE TABLE resume CASCADE", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        launchStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)", preparedStatement -> {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.setString(2, resume.getFullName());
            preparedStatement.execute();
        });
    }

    @Override
    public Resume get(String uuid) {
        final Resume[] resume = new Resume[1];
        launchStatement("SELECT * FROM resume WHERE uuid =?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            resume[0] = new Resume(uuid, resultSet.getString("full_name"));
        });
        return resume[0];
    }


    @Override
    public void update(Resume resume) {
        launchStatement("UPDATE resume SET full_name=? WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
        });
    }

    @Override
    public void delete(String uuid) {
        launchStatement("DELETE FROM resume WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        });
    }

    @Override
    public int size() {
        final int[] size = new int[1];
        launchStatement("SELECT COUNT(*) FROM resume;", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            size[0] = resultSet.getInt(1);
        });
        return size[0];
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> sortedList = new ArrayList<>();

        launchStatement("SELECT * FROM resume ORDER BY uuid", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
            while (resultSet.next()) {
                sortedList.add(
                        new Resume(
                                resultSet.getString("uuid").trim(),
                                resultSet.getString("full_name").trim())
                );
            }
        });
        return sortedList;
    }

    protected void launchStatement(String statement, ABlockOfCode ABlockOfCode) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            ABlockOfCode.execute(preparedStatement);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }
    }

}
