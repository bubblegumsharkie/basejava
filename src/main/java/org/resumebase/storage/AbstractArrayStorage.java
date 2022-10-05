package org.resumebase.storage;

import org.resumebase.exceptions.StorageException;
import org.resumebase.model.Resume;

import java.util.Arrays;


public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;

    @Override
    protected void saveToBase(Resume resume, Integer index) {
        if (size() == STORAGE_LIMIT) {
            throw new StorageException("Storage is already full", resume.getUuid());
        } else {
            saveElement(resume, index);
            countResumes++;
            System.out.println("The resume with UUID: " + resume.getUuid() + " was successfully saved");
        }
    }

    @Override
    protected Resume getFromBase(Integer index) {
        return storage[index];
    }


    @Override
    protected void updateToBase(Resume resume, Integer index) {
        storage[index] = resume;
        System.out.println("The resume with UUID: " + resume.getUuid() + " was successfully updated");
    }

    @Override
    protected void deleteFromBase(Integer index) {
        String deletedResumeUUID = storage[index].getUuid();
        deleteElementById(index);
        countResumes--;
        System.out.println("The resume with UUID: " + deletedResumeUUID + " was successfully deleted");
    }

    public final Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    public final int size() {
        return countResumes;
    }

    public final void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
        System.out.println("Resume base was successfully cleared");
    }

    @Override
    protected boolean indexExists(Integer index) {
        return index >= 0;
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void saveElement(Resume r, int index);

    protected abstract void deleteElementById(int index);

}
