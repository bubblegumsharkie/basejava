package org.resumebase.exceptions;

import java.util.logging.Logger;

public class StorageException extends RuntimeException {
    private final String uuid;


    public StorageException(String message, String uuid) {
        super(message);
        Logger.getLogger(getClass().getName()).warning(message);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
