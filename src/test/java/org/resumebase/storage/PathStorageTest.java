package org.resumebase.storage;

import org.resumebase.storage.serializer.Serializer;

class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(AbstractStorageTest.STORAGE_DIR.getAbsolutePath(), new Serializer()));
    }
}