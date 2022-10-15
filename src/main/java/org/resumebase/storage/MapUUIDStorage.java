package org.resumebase.storage;

import org.resumebase.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;

public class MapUUIDStorage extends AbstractStorage {

    private final HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected boolean isExisting(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    public ArrayList<Resume> getResumes() {
        return new ArrayList<>(storage.values());
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
