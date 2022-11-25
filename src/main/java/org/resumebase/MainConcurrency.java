package org.resumebase;

public class MainConcurrency {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();
    private static int counter;

    public static void main(String[] args) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                synchronized (LOCK1) {
                    System.out.println("Thread 1: LOCK1");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Thread 1: Trying to LOCK2");

                    synchronized (LOCK2) {
                        System.out.println("Thread 1: LOCK1 & LOCK2");
                    }
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                synchronized (LOCK2) {
                    System.out.println("Thread 2: LOCK2");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Thread 2: Trying to LOCK1");

                    synchronized (LOCK1) {
                        System.out.println("Thread 2: LOCK2 & LOCK1");
                    }
                }
            }
        }.start();
    }

    private static synchronized void inc() {
        counter++;
    }

}
