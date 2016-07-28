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

    public DataArray(final Map<String, List<DataEntry>> map) {
        dataList = new ArrayList<>();
        Iterator<String> it = map.keySet().iterator();
        if (it.hasNext()) {
            for (int i = 0; i < map.get(it.next()).size(); i++) {
                Iterator<String> iterator = map.keySet().iterator();
                Data d = new Data();
                while (iterator.hasNext()) {
                    String temp = iterator.next();
                    d.put(temp, map.get(temp).get(i));
                }
                dataList.add(d);
            }
        }
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

    public List<DataEntry> getDate() {
        Iterator<String> iterator = this.getKeys().iterator();
        while (iterator.hasNext()) {
            String temp = iterator.next();
            List<DataEntry> tempList = this.getByKey(temp);
            if (tempList != null && !tempList.isEmpty()
                    && tempList.get(0).getType() == DataEntryType.DATE) {
                return tempList;
            }
        }
        throw new IllegalArgumentException("No time column in this data array");
    }

    public List<Double> getDouble() {
        Iterator<String> iterator = this.getKeys().iterator();
        while (iterator.hasNext()) {
            String temp = iterator.next();
            List<DataEntry> tempList = this.getByKey(temp);
            if (tempList != null && !tempList.isEmpty()
                    && tempList.get(0).getType() == DataEntryType.DOUBLE) {
                List<Double> res = new ArrayList<Double>();
                for (DataEntry entry : tempList) {
                    res.add(entry.getDoubleValue());
                }
                return res;
            }
        }
        throw new IllegalArgumentException("No double column in this data array");
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
