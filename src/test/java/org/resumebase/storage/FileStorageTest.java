package org.resumebase.storage;

import org.resumebase.storage.serializer.ObjectSerializer;

class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(AbstractStorageTest.STORAGE_DIR, new ObjectSerializer()));
    }
}