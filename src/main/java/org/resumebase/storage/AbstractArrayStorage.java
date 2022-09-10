package org.resumebase.storage;

import org.resumebase.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;

    public Resume get(String uuid) {
        int index = getSearchKey(uuid);
        if (index == -1) {
            System.out.println("The resume with UUID: " + uuid + " was not found in storage");
            return null;
        }
        return storage[index];
    }

    protected abstract int getSearchKey(String uuid);

}
