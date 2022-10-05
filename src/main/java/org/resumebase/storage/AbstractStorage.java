package org.resumebase.storage;

import org.resumebase.exceptions.ExistStorageException;
import org.resumebase.exceptions.NotExistStorageException;
import org.resumebase.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void saveElement(Resume r, int index);

    protected abstract void deleteElementById(int index);

    protected abstract void saveToBase(Resume resume, Integer index);

    protected abstract Resume getFromBase(Integer index);

    protected abstract void updateToBase(Resume resume, Integer index);

    protected abstract boolean indexExists(Integer index);

    protected abstract void deleteFromBase(Integer index);

    public final void save(Resume resume) {
        Integer index = getIndexFromBase(resume.getUuid(), false);
        saveToBase(resume, index);
    }

    public final Resume get(String uuid) {
        int index = getIndexFromBase(uuid, true);
        return getFromBase(index);
    }

    public final void update(Resume resume) {
        int index = getIndexFromBase(resume.getUuid(), true);
        updateToBase(resume, index);
    }

    public final void delete(String uuid) {
        int index = getIndexFromBase(uuid, true);
        deleteFromBase(index);
    }

    private Integer getIndexFromBase(String uuid, boolean mustExist) {
        Integer index = getSearchKey(uuid);
        boolean boo = indexExists(index);
        if (!boo && mustExist) {
            throw new NotExistStorageException(uuid);
        } else if (indexExists(index) && !mustExist) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }
}
