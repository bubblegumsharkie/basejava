package org.resumebase.utils;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {

    private final static int STARTING_DAY = 1;

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, STARTING_DAY);
    }

}
