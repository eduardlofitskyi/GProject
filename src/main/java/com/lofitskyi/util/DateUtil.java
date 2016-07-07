package com.lofitskyi.util;

import java.sql.Date;

public class DateUtil {
    public static Date parseDate(String date) {
        String[] dateArray = date.split("-");

        int year = Integer.valueOf(dateArray[0]) - 1900;
        int month = Integer.valueOf(dateArray[1]) - 1;
        int day = Integer.valueOf(dateArray[2]);

        return new Date(year, month, day);
    }
}
