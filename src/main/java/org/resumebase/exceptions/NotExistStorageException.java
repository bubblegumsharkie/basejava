package org.resumebase.exceptions;

import java.sql.SQLException;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("The resume with UUID: " + uuid + " doesn't exist.", uuid);
    }

    public NotExistStorageException(String uuid, SQLException e) {
        super(uuid, e);
    }
}
