package org.resumebase.storage;

import org.resumebase.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.set((Integer) searchKey, resume);
    }

    @Override
    protected boolean isExisting(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(((Integer) searchKey).intValue());
    }

    @Override
    public ArrayList<Resume> getResumes() {
        return new ArrayList<>(storage);
    }

    @Override
    public void clear() {
        storage.clear();
    }


    @Override
    public int size() {
        return storage.size();
    }


}
