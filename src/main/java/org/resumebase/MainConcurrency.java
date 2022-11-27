package org.resumebase;

public class MainConcurrency {
    private static int counter;

    public static void main(String[] args) {

    }

    private static synchronized void inc() {
        counter++;
    }

}
