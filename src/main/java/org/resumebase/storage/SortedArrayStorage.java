package org.resumebase.storage;

import org.resumebase.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected void deleteElementById(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResumes - index - 1);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, countResumes, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void saveElement(Resume resume, int searchKey) {
        searchKey = -searchKey - 1;
        System.arraycopy(storage, searchKey, storage, searchKey + 1, countResumes - searchKey);
        storage[searchKey] = resume;
    }
}
