package org.resumebase.storage;

import org.resumebase.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        //TODO: Implement save with binary insert
    }

    @Override
    public void update(Resume resume) {
        int index = getSearchKey(resume.getUuid());
        if (index == -1) {
            System.out.println("The resume with UUID: " + resume.getUuid() + " was not found in the storage");
            return;
        }
        storage[index] = resume;
        System.out.println("The resume with UUID: " + resume.getUuid() + " was successfully updated");

    }

    @Override
    public void delete(String uuid) {
        int index = getSearchKey(uuid);
        if (index == -1) {
            System.out.println("The resume with UUID: " + uuid + " was not found in storage");
            return;
        }
        storage[index] = storage[countResumes - 1];
        storage[countResumes - 1] = null;
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
