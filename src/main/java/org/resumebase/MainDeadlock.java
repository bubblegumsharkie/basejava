package org.resumebase;

public class MainDeadlock {

    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {

        threadLaunch(
                LOCK1,
                LOCK2,
                "Thread 1: LOCK1",
                "Thread 1: Trying to LOCK2",
                "Thread 1: LOCK1 & LOCK2"
        );

        threadLaunch(
                LOCK2,
                LOCK1,
                "Thread 2: LOCK2",
                "Thread 2: Trying to LOCK1",
                "Thread 2: LOCK2 & LOCK1");
    }

    private static void threadLaunch(
            Object firstLock,
            Object secondLock,
            String messageFirstLockEngage,
            String messageSecondLockAttempt,
            String messageBothLocksEngaged
    ) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                synchronized (firstLock) {
                    System.out.println(messageFirstLockEngage);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(messageSecondLockAttempt);
                    synchronized (secondLock) {
                        System.out.println(messageBothLocksEngaged);
                    }
                }
            }
        }.start();
    }

}
