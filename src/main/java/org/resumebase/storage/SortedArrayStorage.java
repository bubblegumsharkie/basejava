package org.resumebase.storage;

import org.resumebase.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deleteElementById(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResumes - index - 1);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, searchKey);
    }

    @Override
    protected void saveElement(Resume r, int index) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, countResumes - index);
        storage[index] = r;
    }
}
