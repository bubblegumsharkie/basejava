package org.resumebase;

public class TestSingleton {

    private static TestSingleton ourInstance = new TestSingleton();

    private TestSingleton() {

    }

    public static TestSingleton getOurInstance() {
        return ourInstance;
    }

    private enum Week {
        MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6), SUNDAY(7);

        private int number;

        Week(int i) {
            this.number = i;
        }
    }
}
