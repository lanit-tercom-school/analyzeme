package com.analyzeme.analyzers.r;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * {@value COL_TO_R} prefix for column name that will be used in script
 * {@value COL_FROM_R} prefix for column name that is used in R.call when the name of the column is unknown
 */
public class DataConverter {

    private static final String COL_TO_R = "col_";
    private static final String COL_FROM_R = "col";

    /**
     * translates column names of data to standard form (for script execution)
     *
     * @param data - Map<String, List<T>> name of column -> data in it
     * @return Map<String, String>, where the first String is the name of column in the given data, and the second - alias that is used in script
     */
    public static <T> Map<String, String> getKeysForR(
            final Map<String, List<T>> data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "DataConverter getKeysForR: null argument");
        }
        Iterator<String> iterator = data.keySet().iterator();
        Map<String, String> result = new HashMap<String, String>();
        int i = 0;
        while (iterator.hasNext()) {
            result.put(iterator.next(), COL_TO_R + (int) i++);
        }
        return result;
    }

    /**
     * translates column names of data using keys (get by getKeysForR()) to use in R script
     *
     * @param data - Map<String, List<T>> name of column -> data in it
     * @param keys - Map<String, String>, where the first String is the name of column in the given data, and the second - alias that is used in script
     * @param <T>  - now only Double is supported
     * @return Map<String, List<Double>> with column names changed to their aliases from keys map
     */
    public static <T> Map<String, List<T>> translateForR(
            final Map<String, List<T>> data,
            final Map<String, String> keys) {
        if (data == null || keys == null) {
            throw new IllegalArgumentException(
                    "DataConverter translateForR: null argument");
        }
        Map<String, List<T>> result =
                new HashMap<String, List<T>>();
        Iterator<String> iterator = keys.keySet().iterator();
        String temp;
        while (iterator.hasNext()) {
            temp = iterator.next();
            result.put(keys.get(temp), data.get(temp));
        }
        return result;
    }

    /**
     * translates column names of data using keys (get by getKeysForR()) after R script execution
     *
     * @param data - Map<String, List<T>> alias of column -> data in it
     * @param keys - Map<String, String>, where the first String is the name of column in the given data, and the second - alias that is used in script
     * @param <T>  - now only Double is supported
     * @return Map<String, List<Double>> with aliases changed to column names from keys map
     */
    public static <T> Map<String, List<T>> translateFromR(
            final Map<String, List<T>> data,
            final Map<String, String> keys) {
        if (data == null || keys == null) {
            throw new IllegalArgumentException(
                    "DataConverter translateFromR: null argument");
        }
        Map<String, List<T>> result =
                new HashMap<String, List<T>>();
        Map<String, String> tempKeyMap = revertKeyMap(keys);
        Iterator<String> iterator = tempKeyMap.keySet().iterator();
        String temp;
        while (iterator.hasNext()) {
            temp = iterator.next();
            result.put(tempKeyMap.get(temp), data.get(temp));
        }
        if (data.size() != result.size()) {
            Iterator<String> it = data.keySet().iterator();
            while (it.hasNext()) {
                temp = it.next();
                if (!result.keySet().contains(tempKeyMap.get(temp))) {
                    if (temp.startsWith(COL_FROM_R) && !temp.startsWith(COL_TO_R)) {
                        result.put(temp, data.get(temp));
                    } else {
                        throw new IllegalArgumentException(
                                "DataConverter translateFromR: impossible to convert this name "
                                        + temp);
                    }
                }
            }
        }
        return result;
    }

    private static Map<String, String> revertKeyMap(final Map<String, String> keys) {
        if (keys == null) {
            throw new IllegalArgumentException(
                    "DataConverter revertKeyMap: null argument");
        }
        Map<String, String> result = new HashMap<String, String>();
        Iterator<String> iterator = keys.keySet().iterator();
        String temp;
        while (iterator.hasNext()) {
            temp = iterator.next();
            result.put(keys.get(temp), temp);
        }
        return result;
    }
}
