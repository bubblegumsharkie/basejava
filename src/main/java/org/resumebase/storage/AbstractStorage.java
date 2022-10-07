package org.resumebase.storage;

import org.resumebase.exceptions.ExistStorageException;
import org.resumebase.exceptions.NotExistStorageException;
import org.resumebase.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract void saveElement(Resume r, int searchKey);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Resume resume, Object searchKey);

    protected abstract boolean isExisting(Object searchKey);

    protected abstract void doDelete(Object searchKey);

    public final void save(Resume resume) {
        Object index = getNotExistingSearchKey(resume.getUuid());
        doSave(resume, index);
    }

    public final Resume get(String uuid) {
        Object index = getExistingSearchKey(uuid);
        return doGet(index);
    }

    public final void update(Resume resume) {
        Object index = getExistingSearchKey(resume.getUuid());
        doUpdate(resume, index);
    }

    public final void delete(String uuid) {
        Object index = getExistingSearchKey(uuid);
        doDelete(index);
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExisting(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExisting(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
