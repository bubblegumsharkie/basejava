package org.resumebase.storage;

import java.util.logging.Logger;

class ArrayStorageTest extends AbstractArrayStorageTest {

    public ArrayStorageTest() {
        super(new ArrayStorage());
        Logger.getLogger(this.toString()).info("= ARRAY STORAGE TEST =");

    }

}