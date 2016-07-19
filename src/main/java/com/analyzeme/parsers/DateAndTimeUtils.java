package com.analyzeme.parsers;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for working with time and date in specific format
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

    /**
     * Checks if given string is valid time.
     *
     * @param time the string to be checked
     * @return true if valid, false otherwise
     */
    public static boolean isValidTime(String time) {
        try {
            parseTime(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Tries to parse given string as a time
     *
     * @param time time to be parsed
     * @return parses time as {@link LocalTime}
     * @throws IllegalArgumentException if {@code time} is not valid time
     */
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


    /**
     * Checks if given string is valid date.
     *
     * @param date the string to be checked
     * @return true if valid, false otherwise
     */
    public static boolean isValidDate(String date) {
        try {
            parseDate(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Tries to parse given string as a ate
     *
     * @param date date to be parsed
     * @return parses time as {@link LocalDate}
     * @throws IllegalArgumentException if {@code time} is not valid date
     */
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


    /**
     * Checks if given string is valid dateTime.
     *
     * @param dateTime the string to be checked
     * @return true if valid, false otherwise
     */
    public static boolean isValidDateTime(String dateTime) {
        try {
            parseDateTime(dateTime);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Tries to parse given string as a dateTime
     *
     * @param dateTime dateTime to be parsed
     * @return parses date as {@link LocalDateTime}
     * @throws IllegalArgumentException if {@code time} is not valid dateTime
     */
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
