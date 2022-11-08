package org.resumebase.storage;

import org.resumebase.storage.serializer.Serializer;

class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(AbstractStorageTest.STORAGE_DIR, new Serializer()));
    }
}