package org.resumebase.storage;

import org.resumebase.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deleteElementById(int index) {
        storage[index] = storage[countResumes - 1];
        storage[countResumes - 1] = null;
    }

    protected int getSearchKey(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveElement(Resume r, int index) {
        storage[countResumes] = r;
    }
}
