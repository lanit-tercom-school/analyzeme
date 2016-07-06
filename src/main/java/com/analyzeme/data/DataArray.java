package com.analyzeme.data;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lagroffe on 27.05.2016 18:34
 */
public class DataArray<T> {
    private final List<Data<T>> data;

    public DataArray() {
        data = new ArrayList<Data<T>>();
    }

    public void addData(Data<T> d) {
        data.add(d);
    }

    public List<Data<T>> getData() {
        return data;
    }

    public List<T> getByKey(final String key) {
        List<T> result = new ArrayList<T>();
        for (Data<T> d : data) {
            result.add(d.getByKey(key));
        }
        return result;
    }

    //{"Data":[{ "x": "1","y": "1" },{"x": "20","y": "20"}]}
    public String toPointJson() {
        JSONArray result = new JSONArray();
        for (Data<T> d : data) {
            result.put(d.toJson());
        }
        return "{\"Data\":" + result.toString() + "}";
    }

    public String toCsv() {
        StringBuilder s = new StringBuilder();
        Set<String> keys = data.get(0).getKeys();
        for (String key : keys) {
            s.append(key);
            s.append(",");
        }
        s.deleteCharAt(s.length() - 1);
        s.append('\n');

        for (Data<T> d : data) {
            s.append(d.toCsvWithNoHeader());
        }
        return s.toString();
    }
}
