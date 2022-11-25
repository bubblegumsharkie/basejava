package org.resumebase.utils;

public class LazySingleton {

    volatile double sin = Math.sin(13);

    private LazySingleton() {
    }

    private static final class LazySingletonInstanceHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        return LazySingletonInstanceHolder.INSTANCE;
    }

//    public static LazySingleton getInstance() {
//        if (INSTANCE == null) {
//            synchronized (LazySingleton.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
//    }


}
