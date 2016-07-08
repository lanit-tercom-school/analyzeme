package com.analyzeme.data;

import org.json.JSONArray;

import java.util.*;

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

    public Set<String> getKeys() {
        if (!data.isEmpty()) {
            return data.get(0).getKeys();
        } else {
            return new HashSet<String>();
        }
    }

    public Map<String, List<T>> getMap() {
        Map<String, List<T>> result = new HashMap<String, List<T>>();
        for (Data<T> d : this.getData()) {
            for (String key : d.getKeys()) {
                if (!result.containsKey(key)) {
                    result.put(key, new ArrayList<T>());
                    result.get(key).add(d.getByKey(key));
                } else {
                    result.get(key).add(d.getByKey(key));
                }
            }
        }
        return result;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[\n");
        for (Data<T> d : data) {
            builder.append("   ");
            builder.append(d);
            builder.append(",\n");
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof DataArray) {
            DataArray that = (DataArray) other;
            for (int i = 0; i < data.size(); i++) {
                if (this.getData().get(i).equals(that.getData().get(i))) {
                    return result;
                }
            }
            result = true;
        }
        return result;
    }
}
