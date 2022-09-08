package org.example.storage;

import org.example.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    protected static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int countResumes = 0;

    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
        System.out.println("org.example.model.Resume base was successfully cleared");
    }

    public void save(Resume r) {
        int index = getSearchKey(r.getUuid());
        if (storage[storage.length - 1] != null) {
            System.out.println("Current storage is already full. The resume with UUID: " + r.getUuid() + " was not saved");
        } else if (index != -1) {
            System.out.println("The resume with UUID: " + r.getUuid() + " already exists.");
        } else {
            storage[countResumes] = r;
            countResumes++;
            System.out.println("The resume with UUID: " + r.getUuid() + " was successfully saved");
        }
    }

    public Resume get(String uuid) {
        int index = getSearchKey(uuid);
        if (index == -1) {
            System.out.println("The resume with UUID: " + uuid + " was not found in storage");
            return null;
        }
        return storage[index];
    }

    public void update(Resume resume) {
        int index = getSearchKey(resume.getUuid());
        if (index == -1) {
            System.out.println("The resume with UUID: " + resume.getUuid() + " was not found in the storage");
            return;
        }
        storage[index] = resume;
        System.out.println("The resume with UUID: " + resume.getUuid() + " was successfully updated");
    }

    public void delete(String uuid) {
        int index = getSearchKey(uuid);
        if (index == -1) {
            System.out.println("There resume with UUID: " + uuid + " was not found in storage");
            return;
        }
        storage[index] = storage[countResumes - 1];
        storage[countResumes - 1] = null;
        countResumes--;
        System.out.println("The resume with UUID: " + uuid + " was successfully deleted");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    public int size() {
        return countResumes;
    }

    private int getSearchKey(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return i;
            }
        }
        return -1;
    }

}
