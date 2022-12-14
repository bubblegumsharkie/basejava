package org.resumebase.storage;

import org.resumebase.exceptions.StorageException;
import org.resumebase.model.Resume;

import java.util.Arrays;


public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        if (size() == STORAGE_LIMIT) {
            throw new StorageException("Storage is already full", resume.getUuid());
        } else {
            saveElement(resume, searchKey);
            countResumes++;
//            System.out.println("The resume with UUID: " + resume.getUuid() + " was successfully saved");
        }
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }


    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
//        System.out.println("The resume with UUID: " + resume.getUuid() + " was successfully updated");
    }

    @Override
    protected void doDelete(Integer searchKey) {
//        String deletedResumeUUID = storage[searchKey].getUuid();
        deleteElementById(searchKey);
        countResumes--;
//        System.out.println("The resume with UUID: " + deletedResumeUUID + " was successfully deleted");
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
    protected boolean isExisting(Integer searchKey) {
        return searchKey >= 0;
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void saveElement(Resume resume, int searchKey);

    protected abstract void deleteElementById(int index);

}
