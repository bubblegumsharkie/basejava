package org.resumebase.storage;

import org.resumebase.storage.serializer.DataSerializer;

class DataStorageTest extends AbstractStorageTest {
    public DataStorageTest() {
        super(new PathStorage(AbstractStorageTest.STORAGE_DIR.getAbsolutePath(), new DataSerializer()));
    }
}