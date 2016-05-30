package com.analyzeme.data;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lagroffe on 27.05.2016 18:34
 */
public class DoubleDataArray {
    private List<DoubleData> data;

    public DoubleDataArray() {
        data = new ArrayList<DoubleData>();
    }

    public void addData(DoubleData d) {
        data.add(d);
    }

    public List<DoubleData> getData() {
        return data;
    }

    public List<Double> getByKey(final String key) {
        List<Double> result = new ArrayList<Double>();
        for(DoubleData d : data) {
            result.add(d.getByKey(key));
        }
        return result;
    }

    //{"Data":[{ "x": "1","y": "1" },{"x": "20","y": "20"}]}
    public String toPointJson() {
        JSONArray result = new JSONArray();
        for (DoubleData d : data) {
            result.put(d.toJson());
        }
        return "{\"Data\":" + result.toString() + "}";
    }

    public String toCsv() {
        StringBuilder s = new StringBuilder();
        Set<String> keys = data.get(0).getKeys();
        for(String key: keys) {
            s.append(key);
            s.append(",");
        }
        s.deleteCharAt(s.length()-1);
        s.append('\n');

        for(DoubleData d : data) {
            s.append(d.toCsvWithNoHeader());
        }
        return s.toString();
    }
}
