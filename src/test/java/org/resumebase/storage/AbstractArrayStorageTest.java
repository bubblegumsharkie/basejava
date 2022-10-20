package org.resumebase.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.resumebase.exceptions.StorageException;
import org.resumebase.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void storageOverflow() {
        storage.clear();
        Assertions.assertThrows(StorageException.class, () -> {
            try {
                for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                    storage.save(new Resume(NAME_4));
                }
            } catch (StorageException e) {
                Assertions.fail();
            }
            storage.save(new Resume(NAME_4));
        });
    }

}
