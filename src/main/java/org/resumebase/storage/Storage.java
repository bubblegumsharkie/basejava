package org.resumebase.storage;

import org.resumebase.model.Resume;

import java.util.List;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    void clear();

    void save(Resume r);

    Resume get(String uuid);

    void update(Resume resume);

    void delete(String uuid);

    int size();

    List<Resume> getAllSorted();
}
