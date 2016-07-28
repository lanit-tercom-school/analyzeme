package com.analyzeme.data.dataset;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Represents vector of {@link DataEntry}
 */
public class Data {
    private final Map<String, DataEntry> data;

    public Data() {
        data = new HashMap<>();
    }

    public Data(final Map<String, DataEntry> data) {
        this.data = data;
    }

    public void put(final String key, final DataEntry data) {
        this.data.put(key, data);
    }

    /**
     * Checks if entries in {@code other} has the same format as in current object
     *
     * @param other other object
     * @return true if has, false otherwise
     */
    public boolean hasSameTypesAndKeySets(Data other) {
        if (!this.data.keySet().equals(other.data.keySet())) {
            return false;
        }

        for (Map.Entry<String, DataEntry> entry : this.data.entrySet()) {
            if (other.data.get(entry.getKey()).getType() != entry.getValue().getType()) {
                return false;
            }
        }
        return true;
    }

    public Map<String, DataEntry> getData() {
        return data;
    }

    /**
     * Gets entry by its key
     *
     * @param key
     */
    public DataEntry getByKey(final String key) {
        return data.get(key);
    }

    public Set<String> getKeys() {
        return data.keySet();
    }

    /**
     * Gets JSON representation
     *
     * @return An object with given format <i>{ "x": "1","y": "1" }</i>
     */
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        for (Map.Entry<String, DataEntry> entry : data.entrySet()) {
            obj.put(entry.getKey(), entry.getValue().toString());
        }
        return obj;
    }


    /**
     * Gets CSV representation without header
     *
     * @return An object with given format
     * <i>1, 1</i>
     */
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
        if (other instanceof Data) {
            Data that = (Data) other;
            result = this.getData().equals(that.getData());
        }
        return result;
    }
}
