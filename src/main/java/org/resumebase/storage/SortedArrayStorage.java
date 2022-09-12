package org.resumebase.storage;

import org.resumebase.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        int index = getSearchKey(r.getUuid());
        if (index >= STORAGE_LIMIT) {
            System.out.println("Current storage is already full. The resume with UUID: " + r.getUuid() + " was not saved");
            return;
        }
        if (index < 0) {
            index = -index - 1;
            System.arraycopy(storage, index, storage, index + 1, countResumes - index);
            storage[index] = r;
            countResumes++;
            System.out.println("The resume with UUID: " + r.getUuid() + " was successfully saved");
            return;
        }
        System.out.println("The resume with UUID: " + r.getUuid() + " already exists.");
    }

    @Override
    public void delete(String uuid) {
        int index = getSearchKey(uuid);
        if (index == -1) {
            System.out.println("The resume with UUID: " + uuid + " was not found in storage");
            return;
        }
        Resume[] tempStorage = new Resume[countResumes];
        System.arraycopy(storage, index + 1, tempStorage, 0, countResumes - index);
        System.arraycopy(tempStorage, 0, storage, index, tempStorage.length);
        countResumes--;
        System.out.println("The resume with UUID: " + uuid + " was successfully deleted");

    }

    @Override
    protected int getSearchKey(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, searchKey);
    }
}
