package com.analyzeme.data;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by lagroffe on 11.07.2016 11:10
 */
public class DataArrayTest {

    @Test
    public void testToJson() {
        Map map1 = new HashMap<String, Double>();
        map1.put("col1", 0.);
        map1.put("col2", 1.);
        Data<Double> d1 = new Data(map1);
        Map map2 = new HashMap<String, Double>();
        map2.put("col1", 2.);
        map2.put("col2", 3.);
        Data<Double> d2 = new Data(map2);
        DataArray<Double> data = new DataArray();
        data.addData(d1);
        data.addData(d2);
        assertTrue(data.toPointJson().equals("{\"Data\":[{\"col2\":\"1.0\",\"col1\":\"0.0\"},{\"col2\":\"3.0\",\"col1\":\"2.0\"}]}"));
    }
}

