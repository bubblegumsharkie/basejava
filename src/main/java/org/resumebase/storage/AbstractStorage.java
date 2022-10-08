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
        Object searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    public final Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public final void update(Resume resume) {
        Object searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    public final void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
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
