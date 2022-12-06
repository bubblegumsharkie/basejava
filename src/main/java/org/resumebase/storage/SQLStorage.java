package org.resumebase.storage;

import org.resumebase.exceptions.NotExistStorageException;
import org.resumebase.model.Resume;
import org.resumebase.sql.SQLHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
        sqlHelper.launchStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)", preparedStatement -> {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.setString(2, resume.getFullName());
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.launchStatement("SELECT * FROM resume WHERE uuid =?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        });
    }


    @Override
    public void update(Resume resume) {
        sqlHelper.launchStatement("UPDATE resume SET full_name=? WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.launchStatement("DELETE FROM resume WHERE uuid=?", preparedStatement -> {
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
        return sqlHelper.launchStatement("SELECT * FROM resume ORDER BY full_name", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Resume> sortedList = new ArrayList<>();
            while (resultSet.next()) {
                sortedList.add(
                        new Resume(
                                resultSet.getString("uuid").trim(),
                                resultSet.getString("full_name").trim())
                );
            }
            return sortedList;
        });
    }

}
