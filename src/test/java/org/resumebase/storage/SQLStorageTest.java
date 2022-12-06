package org.resumebase.storage;

import org.resumebase.config.Config;

class SQLStorageTest extends AbstractStorageTest {
    public SQLStorageTest() {
        super(Config.get().getStorage());
    }
}