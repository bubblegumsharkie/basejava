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
    protected void saveElement(Resume resume, int index) {
        storage.add(index, resume);
    }

    @Override
    protected void deleteElementById(int index) {
        storage.remove(index);
    }

    @Override
    protected void saveToBase(Resume resume, Integer index) {
        storage.add(index, resume);
    }

    @Override
    protected Resume getFromBase(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void updateToBase(Resume resume, Integer index) {
        storage.set(index, resume);
    }

    @Override
    protected boolean indexExists(Integer index) {
        boolean boo = index == null;
        return boo;
    }

    @Override
    protected void deleteFromBase(Integer index) {
        storage.remove(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
