package com.analyzeme.analyzers.r;

import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataEntryType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by lagroffe on 13.07.2016 13:53
 */
public class DataConverterTest {
    private static Map<String, List<DataEntry>> simpleMap;
    private static Map<String, String> keysForToTranslate;
    private static Map<String, List<DataEntry>> toTranslate;
    private static Map<String, List<DataEntry>> resultOfToTranslate;
    private static final String COL_0 = "col_0";
    private static final String COL_1 = "col_1";

    @BeforeClass
    public static void before() {
        simpleMap = new HashMap<>();
        List<DataEntry> first = new ArrayList<>();
        first.add(new DataEntry(DataEntryType.DOUBLE, 1.));
        first.add(new DataEntry(DataEntryType.DOUBLE, 2.));
        simpleMap.put("first", first);
        List<DataEntry> second = new ArrayList<>();
        second.add(new DataEntry(DataEntryType.DOUBLE, 3.));
        second.add(new DataEntry(DataEntryType.DOUBLE, 4.));
        simpleMap.put("second", second);

        keysForToTranslate = new HashMap<>();
        keysForToTranslate.put("first", COL_0);

        toTranslate = new HashMap<>();
        toTranslate.put(COL_0, first);
        toTranslate.put("col0", second);

        resultOfToTranslate = new HashMap<>();
        resultOfToTranslate.put("first", first);
        resultOfToTranslate.put("col0", second);
    }

    @Test
    public void testGetKeysSimple() {
        Map<String, String> keys = DataConverter.getKeysForR(simpleMap);
        Iterator<String> iterator = simpleMap.keySet().iterator();
        String temp = iterator.next();
        assertEquals(keys.get(temp), COL_0);
        temp = iterator.next();
        assertEquals(keys.get(temp), COL_1);
    }

    @Test
    public void testTranslateToRAndFromRSimple() {
        Map<String, String> keys = DataConverter.getKeysForR(simpleMap);
        Map<String, List<DataEntry>> result = DataConverter.translateForR(simpleMap, keys);
        Map<String, List<DataEntry>> back = DataConverter.translateFromR(result, keys);
        assertEquals(simpleMap, back);
    }

    @Test
    public void testTranslateFromR() {
        Map<String, List<DataEntry>> result = DataConverter.translateFromR(toTranslate, keysForToTranslate);
        assertEquals(result, resultOfToTranslate);
    }
}
