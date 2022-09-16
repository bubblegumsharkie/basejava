package org.resumebase.exceptions;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("The resume with UUID:  + " + uuid + " already exists.", uuid);
    }
}
