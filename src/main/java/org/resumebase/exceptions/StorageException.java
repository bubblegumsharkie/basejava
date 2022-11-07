package org.resumebase.exceptions;

import java.util.logging.Logger;

public class StorageException extends RuntimeException {
    private final String uuid;


    public StorageException(String message, String uuid) {
        super(message);
        Logger.getLogger(getClass().getName()).warning(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        Logger.getLogger(getClass().getName()).warning(message);
        this.uuid = uuid;
    }

    public StorageException(String message) {
        super(message);
        this.uuid = null;
    }

    public StorageException(String message, Exception e) {
        super(message, e);
        this.uuid = null;
    }

    public String getUuid() {
        return uuid;
    }
}
