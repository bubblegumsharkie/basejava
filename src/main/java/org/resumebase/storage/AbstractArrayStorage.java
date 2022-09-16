package org.resumebase.storage;

import org.resumebase.exceptions.ExistStorageException;
import org.resumebase.exceptions.NotExistStorageException;
import org.resumebase.exceptions.StorageException;
import org.resumebase.model.Resume;

import java.util.Arrays;


public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;

    public final void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
        System.out.println("Resume base was successfully cleared");
    }

    public final void save(Resume r) {
        int index = getSearchKey(r.getUuid());
        if (size() == STORAGE_LIMIT) {
            throw new StorageException("Storage is already full", r.getUuid());
        } else if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            saveElement(r, index);
            countResumes++;
            System.out.println("The resume with UUID: " + r.getUuid() + " was successfully saved");
        }
    }

    public final void delete(String uuid) {
        int index = getSearchKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteElementById(index);
        countResumes--;
        System.out.println("The resume with UUID: " + uuid + " was successfully deleted");
    }

    public final Resume get(String uuid) {
        int index = getSearchKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    public final void update(Resume resume) {
        int index = getSearchKey(resume.getUuid());
        if (index == -1) {
            throw new NotExistStorageException(resume.getUuid());
        }
        storage[index] = resume;
        System.out.println("The resume with UUID: " + resume.getUuid() + " was successfully updated");

    }

    public final Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    public final int size() {
        return countResumes;
    }

    protected abstract int getSearchKey(String uuid);

    protected abstract void saveElement(Resume r, int index);

    protected abstract void deleteElementById(int index);

}
