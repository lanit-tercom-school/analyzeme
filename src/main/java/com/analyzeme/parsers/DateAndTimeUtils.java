package com.analyzeme.parsers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ilya on 7/14/16.
 */
public class DateAndTimeUtils {

    //Taken from here http://stackoverflow.com/a/3390252/5494618
//    private static final Map<String, String> DATE_AND_TIME_FORMAT_REGEXPS = new HashMap<String, String>() {{
//        put("^\\d{8}$", "yyyyMMdd");
//        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
//        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
//        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
//        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
//        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
//        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
//        put("^\\d{12}$", "yyyyMMddHHmm");
//        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
//        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
//        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
//        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
//        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
//        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
//        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
//        put("^\\d{14}$", "yyyyMMddHHmmss");
//        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
//        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
//        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
//        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
//        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
//        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
//        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
//    }};
    private static final Map<String, String> TIME_REGEXPS = new HashMap<String, String>() {{
        put("\\d{2}:\\d{2}$", "HH:mm");
        put("\\d{2}:\\d{2}:\\d{2}$", "HH:mm:ss");
    }};

    private static String determineTimeFormat(String dateString) {
        for (String regexp : TIME_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return TIME_REGEXPS.get(regexp);
            }
        }
        return null;
    }

    public static boolean isValidTime(String time) {
        try {
            parse(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static LocalTime parse(String time) throws IllegalArgumentException {
        String format = determineTimeFormat(time);
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
}
