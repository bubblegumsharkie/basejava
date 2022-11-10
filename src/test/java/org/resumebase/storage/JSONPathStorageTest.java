package org.resumebase.storage;

import org.resumebase.storage.serializer.JSONSerializer;

class JSONPathStorageTest extends AbstractStorageTest {
    public JSONPathStorageTest() {
        super(new PathStorage(AbstractStorageTest.STORAGE_DIR.getAbsolutePath(), new JSONSerializer()));
    }
}