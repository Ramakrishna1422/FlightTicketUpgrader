package com.sahaj.flight.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

    public static String DATE_FORMAT = "yyyy-MM-dd";

    public static Calendar getCalendar(String source, String format) throws Exception {
        Calendar date = Calendar.getInstance();
        date.setTime(new SimpleDateFormat(format).parse(source));
        return date;
    }

    public static Calendar getCalendar(String source) throws Exception {
        return getCalendar(source, DATE_FORMAT);
    }

    public static boolean isBefore(String start, String end) {
        try {
            return getCalendar(start).getTime().before(getCalendar(end).getTime());
        } catch (Exception e) {
            return false;
        }
    }
}
