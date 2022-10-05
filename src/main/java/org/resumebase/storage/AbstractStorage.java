package org.resumebase.storage;

import org.resumebase.exceptions.ExistStorageException;
import org.resumebase.exceptions.NotExistStorageException;
import org.resumebase.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract int getSearchKey(String uuid);

    protected abstract void saveElement(Resume r, int index);

    protected abstract void deleteElementById(int index);

    protected abstract void saveToBase(Resume resume, int index);

    protected abstract Resume getFromBase(int index);

    protected abstract void updateToBase(Resume resume, int index);


    protected abstract void deleteFromBase(int index);

    public void save(Resume resume) {
        int index = getIndexFromBase(resume.getUuid(), false);
        saveToBase(resume, index);
    }

    public Resume get(String uuid) {
        int index = getIndexFromBase(uuid, true);
        return getFromBase(index);
    }

    public void update(Resume resume) {
        int index = getIndexFromBase(resume.getUuid(), true);
        updateToBase(resume, index);
    }

    public void delete(String uuid) {
        int index = getIndexFromBase(uuid, true);
        deleteFromBase(index);
    }

    private int getIndexFromBase(String uuid, boolean mustExist) {
        int index = getSearchKey(uuid);
        if (!indexExists(index) && mustExist) {
            throw new NotExistStorageException(uuid);
        } else if (indexExists(index) && !mustExist) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    protected abstract boolean indexExists(int index);
}
