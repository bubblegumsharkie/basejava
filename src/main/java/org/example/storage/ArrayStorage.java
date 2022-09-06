package org.example.storage;

import org.example.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int countResumes = 0;

    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
        System.out.println("org.example.model.Resume base was successfully cleared");
    }

    public void save(Resume r) {
        if ((!checkForResume(r.getUuid())) && (storage[storage.length - 1] == null)) {
            storage[countResumes] = r;
            countResumes++;
            System.out.println("The resume with UUID: " + r.getUuid() + " was successfully saved");
        } else System.out.println("There was an error with saving resume. Check if the storage is already full or if there is a resume with the same UUID: " + r.getUuid());

    }

    public Resume get(String uuid) {
        int resumeID = findResume(uuid);
        if (resumeID != -1) {
            if (Objects.equals(storage[resumeID].getUuid(), uuid)) {
                return storage[resumeID];
            }
        } else System.out.println("The resume with UUID: " + uuid + " was not found in storage");
        return null;
    }

    public void update(Resume resume) {
        int resumeID = findResume(resume.getUuid());
        if (resumeID != -1) {
            storage[resumeID] = resume;
            System.out.println("The resume with UUID: " + resume.getUuid() + " was successfully updated");
        }
    }

    public void delete(String uuid) {
        int resumeID = findResume(uuid);
        if (resumeID != -1) {
            storage[resumeID] = storage[countResumes - 1];
            storage[countResumes - 1] = null;
            countResumes--;
            System.out.println("The resume with UUID: " + uuid + " was successfully deleted");
        } else System.out.println("There resume with UUID: " + uuid + " was not found in storage");
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

    private int findResume(String uuid) {
        if (checkForResume(uuid)) {
            for (int i = 0; i < countResumes; i++) {
                if (Objects.equals(storage[i].getUuid(), uuid)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private boolean checkForResume(String uuid) {
        if (countResumes > 0) {
            for (int i = 0; i < countResumes; i++) {
                if (Objects.equals(storage[i].getUuid(), uuid)) {
                    return true;
                }
            }
        }
        return false;
    }
}
