package org.resumebase.storage;

import org.resumebase.config.Config;

class SQLStorageTest extends AbstractStorageTest {
    public SQLStorageTest() {
        super(new SQLStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword()));
    }
}