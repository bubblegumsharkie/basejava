package org.resumebase.storage;

import org.resumebase.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        int index = getSearchKey(r.getUuid());
        if (index >= STORAGE_LIMIT) {
            System.out.println("Current storage is already full. The resume with UUID: " + r.getUuid() + " was not saved");
        } else if (index != -1) {
            System.out.println("The resume with UUID: " + r.getUuid() + " already exists.");
        } else {
            storage[countResumes] = r;
            countResumes++;
            System.out.println("The resume with UUID: " + r.getUuid() + " was successfully saved");
        }
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
            System.out.println("The resume with UUID: " + uuid + " was not found in storage");
            return;
        }
        storage[index] = storage[countResumes - 1];
        storage[countResumes - 1] = null;
        countResumes--;
        System.out.println("The resume with UUID: " + uuid + " was successfully deleted");
    }

    protected int getSearchKey(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return i;
            }
        }
        return -1;
    }

}
