package org.resumebase.storage;

import org.resumebase.storage.serializer.ObjectSerializer;

class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(AbstractStorageTest.STORAGE_DIR.getAbsolutePath(), new ObjectSerializer()));
    }
}