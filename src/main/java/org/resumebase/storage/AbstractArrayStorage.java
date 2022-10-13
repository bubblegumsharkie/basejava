package org.resumebase.storage;

import org.resumebase.exceptions.StorageException;
import org.resumebase.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes = 0;

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        if (size() == STORAGE_LIMIT) {
            throw new StorageException("Storage is already full", resume.getUuid());
        } else {
            saveElement(resume, (Integer) searchKey);
            countResumes++;
            System.out.println("The resume with UUID: " + resume.getUuid() + " was successfully saved");
        }
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }


    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage[(int) searchKey] = resume;
        System.out.println("The resume with UUID: " + resume.getUuid() + " was successfully updated");
    }

    @Override
    protected void doDelete(Object searchKey) {
        String deletedResumeUUID = storage[(int) searchKey].getUuid();
        deleteElementById((Integer) searchKey);
        countResumes--;
        System.out.println("The resume with UUID: " + deletedResumeUUID + " was successfully deleted");
    }


    public final List<Resume> getAllSorted() {
        ArrayList<Resume> resumes = new ArrayList<>(List.of(Arrays.copyOf(storage, countResumes)));
        doSort(resumes);
        return resumes;
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
    protected boolean isExisting(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void saveElement(Resume resume, int searchKey);

    protected abstract void deleteElementById(int index);

}
