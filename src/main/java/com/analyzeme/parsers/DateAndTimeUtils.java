package com.analyzeme.parsers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ilya on 7/14/16.
 */
public class DateAndTimeUtils {

    //Taken from here http://stackoverflow.com/a/3390252/5494618
    private static final Map<String, String> TIME_REGEXPS = new HashMap<String, String>() {{
        put("\\d{2}:\\d{2}$", "HH:mm");
        put("\\d{2}:\\d{2}:\\d{2}$", "HH:mm:ss");
    }};

    private static final Map<String, String> DATE_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$", "dd.MM.yyyy");
    }};

    private static final Map<String, String> DATE_TIME_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        put("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}\\s\\d{1,2}:\\d{2}$", "dd.MM.yyyy HH:mm");

        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd.MM.yyyy HH:mm:ss");
    }};

    private static String determineFormat(String dateString, Map<String, String> regexps) {
        for (String regexp : regexps.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return regexps.get(regexp);
            }
        }
        return null;
    }

    /*
     * For time
     */
    public static boolean isValidTime(String time) {
        try {
            parseTime(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static LocalTime parseTime(String time) throws IllegalArgumentException {
        String format = determineFormat(time, TIME_REGEXPS);
        if (format == null) {
            throw new IllegalArgumentException();
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalTime.parse(time, formatter);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error while parsing time");
            }
        }
    }

    /*
     * For date
     */
    public static boolean isValidDate(String date) {
        try {
            parseDate(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static LocalDate parseDate(String date) throws IllegalArgumentException {
        String format = determineFormat(date, DATE_REGEXPS);
        if (format == null) {
            throw new IllegalArgumentException();
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalDate.parse(date, formatter);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error while parsing date");
            }
        }
    }


    /*
     * For dateTime
     */
    public static boolean isValidDateTime(String dateTime) {
        try {
            parseDateTime(dateTime);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static LocalDateTime parseDateTime(String dateTime) throws IllegalArgumentException {
        String format = determineFormat(dateTime, DATE_TIME_REGEXPS);
        if (format == null) {
            throw new IllegalArgumentException();
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalDateTime.parse(dateTime, formatter);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error while parsing dateTime");
            }
        }
    }
}
