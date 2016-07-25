package com.analyzeme.analyzers.r;

import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.data.dataWithType.DataEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * {@value COL_TO_R} prefix for column name that will be used in script
 * {@value COL_FROM_R} prefix for column name that is used in R.call when the name of the column is unknown
 */
public class DataConverter {
    private static final Logger LOGGER;
    private static final String COL_TO_R = "col_";
    private static final String COL_FROM_R = "col";

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.r.DataConverter");
    }

    /**
     * translates column names of data to standard form (for script execution)
     *
     * @param data - Map<String, List<DataEntry>> name of column -> data in it
     * @return Map<String, String>, where the first String is the name of column in the given data, and the second - alias that is used in script
     */
    public static Map<String, String> getKeysForR(
            final Map<String, List<DataEntry>> data) {
        LOGGER.debug("getKeysForR(): method started");
        if (data == null) {
            LOGGER.info("getKeysForR(): null argument");
            throw new IllegalArgumentException(
                    "DataConverter getKeysForR: null argument");
        }
        Iterator<String> iterator = data.keySet().iterator();
        Map<String, String> result = new HashMap<String, String>();
        int i = 0;
        while (iterator.hasNext()) {
            result.put(iterator.next(), COL_TO_R + (int) i++);
        }
        LOGGER.debug("getKeysForR(): method finished");
        return result;
    }

    /**
     * translates column names of data using keys (get by getKeysForR()) to use in R script
     *
     * @param data - Map<String, List<DataEntry>> name of column -> data in it
     * @param keys - Map<String, String>, where the first String is the name of column in the given data, and the second - alias that is used in script
     * @return Map<String, List<DataEntry>> with column names changed to their aliases from keys map
     */
    public static Map<String, List<DataEntry>> translateForR(
            final Map<String, List<DataEntry>> data,
            final Map<String, String> keys) {
        LOGGER.debug("translateForR(): method started");
        if (data == null || keys == null) {
            LOGGER.info("translateForR(): null argument");
            throw new IllegalArgumentException(
                    "DataConverter translateForR: null argument");
        }
        Map<String, List<DataEntry>> result =
                new HashMap<String, List<DataEntry>>();
        Iterator<String> iterator = keys.keySet().iterator();
        String temp;
        while (iterator.hasNext()) {
            temp = iterator.next();
            result.put(keys.get(temp), data.get(temp));
        }
        LOGGER.info("translateForR(): method finished");
        return result;
    }

    /**
     * translates column names of data using keys (get by getKeysForR()) after R script execution
     *
     * @param data - Map<String, List<DataEntry>> alias of column -> data in it
     * @param keys - Map<String, String>, where the first String is the name of column in the given data, and the second - alias that is used in script
     * @return Map<String, List<DataEntry>> with aliases changed to column names from keys map
     */
    public static Map<String, List<DataEntry>> translateFromR(
            final Map<String, List<DataEntry>> data,
            final Map<String, String> keys) {
        LOGGER.debug("translateFromR(Map): method started");
        if (data == null || keys == null) {
            LOGGER.info("translateFromR(): null argument");
            throw new IllegalArgumentException(
                    "DataConverter translateFromR: null argument");
        }
        Map<String, List<DataEntry>> result =
                new HashMap<String, List<DataEntry>>();
        Map<String, String> tempKeyMap = revertKeyMap(keys);
        LOGGER.debug("translateFromR(): keys map reverted");
        Iterator<String> iterator = tempKeyMap.keySet().iterator();
        String temp;
        while (iterator.hasNext()) {
            temp = iterator.next();
            if (data.containsKey(temp)) {
                result.put(tempKeyMap.get(temp), data.get(temp));
            }
        }
        if (data.size() != result.size()) {
            Iterator<String> it = data.keySet().iterator();
            while (it.hasNext()) {
                temp = it.next();
                if (!result.keySet().contains(tempKeyMap.get(temp))) {
                    if (temp.startsWith(COL_FROM_R)) {
                        LOGGER.debug(
                                "translateFromR(): column with auto formed name",
                                temp);
                        result.put(temp, data.get(temp));
                    } else {
                        LOGGER.debug(
                                "translateFromR(): column with new name",
                                temp);
                        result.put(temp, data.get(temp));
                    }
                }
            }
        }
        LOGGER.debug("translateFromR(Map): method finished");
        return result;
    }

    /**
     * translates column names of data using keys (get by getKeysForR()) after R script execution
     *
     * @param result - FileResult (an object that contains Map<String, List<DataEntry>> alias of column -> data in it)
     * @param keys   - Map<String, String>, where the first String is the name of column in the given data, and the second - alias that is used in script
     * @return FileResult (an object that contains Map<String, List<DataEntry>> with aliases changed to column names from keys map)
     */
    public static IResult translateFromR(final FileResult result,
                                         final Map<String, String> keys) {
        LOGGER.debug("translateFromR(FileResult): method started");
        if (result == null) {
            LOGGER.info("translateFromR(): null argument");
            throw new IllegalArgumentException(
                    "DataConverter translateFromR: null argument");
        }
        Map<String, List<DataEntry>> map = result.getValue();
        Map<String, List<DataEntry>> resultMap = translateFromR(map, keys);
        LOGGER.debug("translateFromR(FileResult): method finished");
        return new FileResult(resultMap);
    }

    private static Map<String, String> revertKeyMap(
            final Map<String, String> keys) {
        LOGGER.debug("revertKeyMap(): method started");
        if (keys == null) {
            LOGGER.info("revertKeyMap(): null argument");
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
        LOGGER.debug("revertKeyMap(): method finished");
        return result;
    }
}
