package org.resumebase.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.resumebase.config.Config;
import org.resumebase.exceptions.ExistStorageException;
import org.resumebase.exceptions.NotExistStorageException;
import org.resumebase.model.Resume;
import org.resumebase.model.ResumeTestData;

import java.io.File;
import java.util.List;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected static final String NAME_4 = "Name 4";
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String NAME_1 = "Name Bean";
    private static final String NAME_2 = "Name Alex";
    private static final String NAME_3 = "Name Calcium";
    private static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, NAME_1);
    private static final Resume RESUME_2 = ResumeTestData.createResume(UUID_2, NAME_2);
    private static final Resume RESUME_3 = ResumeTestData.createResume(UUID_3, NAME_3);
    private static final Resume RESUME_4 = ResumeTestData.createResume(UUID_4, NAME_4);
    protected final Storage storage;
    private final List<Resume> testSortedResumesArray = List.of(RESUME_1, RESUME_2, RESUME_3);

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        STORAGE_DIR.mkdir();
        storage.save(RESUME_3);
        storage.save(RESUME_1);
        storage.save(RESUME_2);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    void save() {
        storage.save(ResumeTestData.createResume(UUID_4, NAME_4));
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test
    void saveAlreadyExists() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
        assertSize(2);
    }

    @Test
    void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
    }

    @Test
    void update() {
        storage.update(RESUME_1);
        Assertions.assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test
    void updateNotExist() {
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_4));
        Assertions.assertEquals(NotExistStorageException.class, thrown.getClass());
    }

    @Test
    void getAllSorted() {
        List<Resume> sortedStorage = storage.getAllSorted();
        Assertions.assertEquals(3, sortedStorage.size());
        Assertions.assertEquals(testSortedResumesArray, sortedStorage);
    }

    @Test
    void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }

}
