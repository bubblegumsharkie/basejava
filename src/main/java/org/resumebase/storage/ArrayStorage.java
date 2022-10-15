package org.resumebase.storage;

import org.resumebase.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ArrayList<Resume> getResumes() {
        return new ArrayList<>(List.of(Arrays.copyOf(storage, countResumes)));
    }

    @Override
    protected void saveElement(Resume resume, int searchKey) {
        storage[countResumes] = resume;
    }
}
