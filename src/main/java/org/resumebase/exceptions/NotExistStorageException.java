package org.resumebase.exceptions;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("The resume with UUID:  + " + uuid + "doesn't exist.", uuid);
    }
}
