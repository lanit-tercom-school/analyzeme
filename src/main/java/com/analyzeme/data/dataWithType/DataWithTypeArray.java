package com.analyzeme.data.dataWithType;

import org.json.JSONArray;

import java.util.*;

/**
 * Created by lagroffe on 27.05.2016 18:34
 */
public class DataWithTypeArray {
    private final List<DataWithType> data;

    public DataWithTypeArray() {
        data = new ArrayList<>();
    }

    public void addData(DataWithType d) {
        data.add(d);
    }

    public List<DataWithType> getData() {
        return data;
    }

    public Set<String> getKeys() {
        if (!data.isEmpty()) {
            return data.get(0).getKeys();
        } else {
            return new HashSet<>();
        }
    }

    public Map<String, List<DataEntry>> getMap() {
        Map<String, List<DataEntry>> result = new HashMap<>();
        for (DataWithType d : this.getData()) {
            for (String key : d.getKeys()) {
                if (!result.containsKey(key)) {
                    result.put(key, new ArrayList<>());
                    result.get(key).add(d.getByKey(key));
                } else {
                    result.get(key).add(d.getByKey(key));
                }
            }
        }
        return result;
    }

    public List getByKey(final String key) {
        List result = new ArrayList();
        for (DataWithType d : data) {
            result.add(d.getByKey(key));
        }
        return result;
    }

    //{"DataWithType":[{ "x": "1","y": "1" },{"x": "20","y": "20"}]}
    public String toPointJson() {
        JSONArray result = new JSONArray();
        for (DataWithType d : data) {
            result.put(d.toJson());
        }
        return "{\"DataWithType\":" + result.toString() + "}";
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

        for (DataWithType d : data) {
            s.append(d.toCsvWithNoHeader());
        }
        return s.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[\n");
        for (DataWithType d : data) {
            builder.append("   ");
            builder.append(d);
            builder.append(",\n");
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DataWithTypeArray && this.data.equals(((DataWithTypeArray) other).data);
    }
}
