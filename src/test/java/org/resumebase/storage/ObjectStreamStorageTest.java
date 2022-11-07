package org.resumebase.storage;

import org.junit.jupiter.api.BeforeEach;

class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(AbstractStorageTest.STORAGE_DIR));
    }

    @BeforeEach
    public void setUp() {
        STORAGE_DIR.mkdir();
    }

}