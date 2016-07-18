package com.analyzeme.data.dataWithType;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lagroffe on 27.05.2016 18:30
 */
public class DataWithType {
    private final Map<String, DataEntry> data;

    public DataWithType() {
        data = new HashMap<>();
    }

    public DataWithType(final Map<String, DataEntry> data) {
        this.data = data;
    }

    public boolean hasSameTypesAndKeySets(DataWithType other) {
        if (!this.data.keySet().equals(other.data.keySet())) {
            return false;
        }

        for(Map.Entry<String, DataEntry> entry : this.data.entrySet()){
            if (other.data.get(entry.getKey()).getType() != entry.getValue().getType()){
                return false;
            }
        }
        return true;
    }

    public Map<String, DataEntry> getData() {
        return data;
    }

    public DataEntry getByKey(final String key) {
        return data.get(key);
    }

    public Set<String> getKeys() {
        return data.keySet();
    }

    //{ "x": "1","y": "1" }
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        for (Map.Entry<String, DataEntry> entry : data.entrySet()) {
            obj.put(entry.getKey(), entry.getValue().toString());
        }
        return obj;
    }

    //1, 1
    public String toCsvWithNoHeader() {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<String, DataEntry> entry : data.entrySet()) {
            s.append(entry.getValue().toString());
            s.append(',');
        }
        s.deleteCharAt(s.length() - 1);
        s.append("\n");
        return s.toString();
    }

    @Override
    public String toString() {
        return toJson().toJSONString();
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof DataWithType) {
            DataWithType that = (DataWithType) other;
            result = this.getData().equals(that.getData());
        }
        return result;
    }
}
