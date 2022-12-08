package org.resumebase.sql;

import org.resumebase.exceptions.ExistStorageException;
import org.resumebase.exceptions.StorageException;

import java.sql.SQLException;

public class SQLExceptionHandler {

    protected static StorageException convert(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            throw new ExistStorageException(null);
        }
        throw new StorageException(e);
    }

}
