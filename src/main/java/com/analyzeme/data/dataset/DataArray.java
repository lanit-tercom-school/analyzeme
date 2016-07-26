package com.analyzeme.data.dataset;

import org.json.JSONArray;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents matrix of {@link DataEntry}
 */
public class DataArray {
    private final List<Data> dataList;

    public DataArray() {
        dataList = new ArrayList<>();
    }

    /**
     * Adds new data to the list
     *
     * @param data
     * @throws IllegalArgumentException if @{data} has different structure than others in lists
     */
    public void addData(Data data) {
        if (!dataList.isEmpty()) {
            if (!data.hasSameTypesAndKeySets(dataList.get(0))) {
                throw new IllegalArgumentException("Types should be same");
            }
        }
        dataList.add(data);
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public Set<String> getKeys() {
        if (!dataList.isEmpty()) {
            return dataList.get(0).getKeys();
        } else {
            return new HashSet<>();
        }
    }

    /**
     * Returns all data as map of columns where key is column name and map[key] - column
     *
     * @return newly crated amp
     */
    public Map<String, List<DataEntry>> getMap() {
        Map<String, List<DataEntry>> result = new HashMap<>();
        for (Data data : this.getDataList()) {
            for (String key : data.getKeys()) {
                if (!result.containsKey(key)) {
                    result.put(key, new ArrayList<>());
                }
                result.get(key).add(data.getByKey(key));
            }
        }
        return result;
    }

    /**
     * Gets specified columns
     *
     * @param key - column name
     * @return list of all entries in this column
     */
    public List<DataEntry> getByKey(final String key) {
        return dataList.stream().map(d -> d.getByKey(key)).collect(Collectors.toList());
    }

    /**
     * Creates JSON representation in given format:
     * <i>{"Data":[{ "x": "1","y": "1" },{"x": "20","y": "20"}]}</i>
     *
     * @return JSON representation
     */
    public String toPointJson() {
        JSONArray result = new JSONArray();
        for (Data d : dataList) {
            result.put(d.toJson());
        }
        return "{\"Data\":" + result.toString() + "}";
    }

    /**
     * @return csv representation
     */
    public String toCsv() {
        StringBuilder s = new StringBuilder();
        Set<String> keys = dataList.get(0).getKeys();
        for (String key : keys) {
            s.append(key);
            s.append(",");
        }
        s.deleteCharAt(s.length() - 1);
        s.append('\n');

        for (Data d : dataList) {
            s.append(d.toCsvWithNoHeader());
        }
        return s.toString();
    }

    /**
     * @return JSON-like representation
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[\n");
        for (Data d : dataList) {
            builder.append("   ");
            builder.append(d);
            builder.append(",\n");
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DataArray && this.dataList.equals(((DataArray) other).dataList);
    }
}
