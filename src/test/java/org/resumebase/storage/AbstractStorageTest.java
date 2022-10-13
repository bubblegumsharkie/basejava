package org.resumebase.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.resumebase.exceptions.ExistStorageException;
import org.resumebase.exceptions.NotExistStorageException;
import org.resumebase.exceptions.StorageException;
import org.resumebase.model.Resume;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = new Resume(UUID_1, "Name 1");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Name 2");
    private static final Resume RESUME_3 = new Resume(UUID_3, "Name 2");
    private static final Resume RESUME_4 = new Resume(UUID_4, "Name 3");
    private final Storage storage;
    private final Resume[] testResumesArray = new Resume[]{RESUME_1, RESUME_2, RESUME_3};

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private boolean ignoreOverflowTestCondition() {
        if (storage.getClass().getSimpleName().equals("ListStorage") || storage.getClass().getSimpleName().equals("MapStorage")) {
            System.out.println("List & Map storages can't be overflowed");
            return true;
        }
        return false;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_3);
        storage.save(RESUME_1);
        storage.save(RESUME_2);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        Assertions.assertArrayEquals(new Resume[0], storage.getAll());
    }

    @Test
    void save() {
        storage.save(new Resume(UUID_4, ""));
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
        Assertions.assertSame(RESUME_1, storage.get(UUID_1));
    }

    @Test
    void updateNotExist() {
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_4));
        Assertions.assertEquals(NotExistStorageException.class, thrown.getClass());
    }

    @Test
    void getAll() {
        if (storage.getClass().getSimpleName().equals("MapStorage")) {
            Assertions.assertSame(3, storage.size());
        } else {
            Assertions.assertArrayEquals(testResumesArray, storage.getAll());
        }
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    @DisabledIf("ignoreOverflowTestCondition")
    void storageOverflow() {
        storage.clear();
        Assertions.assertThrows(StorageException.class, () -> {
            try {
                for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                    storage.save(new Resume());
                }
            } catch (StorageException e) {
                Assertions.fail();
            }
            storage.save(new Resume());
        });
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }

}
