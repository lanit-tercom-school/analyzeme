package com.analyzeme.data;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lagroffe on 27.05.2016 18:30
 */
public class Data<T> {
    private final Map<String, T> data;

    public Data() {
        data = new HashMap<String, T>();
    }

    public Data(final Map<String, T> data) {
        this.data = data;
    }

    public Map<String, T> getData() {
        return data;
    }

    public T getByKey(final String key) {
        return data.get(key);
    }

    public Set<String> getKeys() {
        return data.keySet();
    }

    //{ "x": "1","y": "1" }
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        for (Map.Entry<String, T> entry : data.entrySet()) {
            obj.put(entry.getKey(), entry.getValue().toString());
        }
        return obj;
    }

    //1, 1
    public String toCsvWithNoHeader() {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<String, T> entry : data.entrySet()) {
            s.append(entry.getValue().toString());
            s.append(',');
        }
        s.deleteCharAt(s.length() - 1);
        s.append("\n");
        return s.toString();
    }
}
