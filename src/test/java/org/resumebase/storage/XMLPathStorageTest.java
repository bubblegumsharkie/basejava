package org.resumebase.storage;

import org.resumebase.storage.serializer.XMLSerializer;

class XMLPathStorageTest extends AbstractStorageTest {
    public XMLPathStorageTest() {
        super(new PathStorage(AbstractStorageTest.STORAGE_DIR.getAbsolutePath(), new XMLSerializer()));
    }
}