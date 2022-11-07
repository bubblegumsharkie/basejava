package org.resumebase.storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapUUIDStorageTest.class,
        MapResumeStorageTest.class,
        ObjectStreamStorageTest.class
})

public class AllStorageTests {
}
