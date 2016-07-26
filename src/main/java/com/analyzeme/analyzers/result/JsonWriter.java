package com.analyzeme.analyzers.result;

import com.analyzeme.data.dataset.DataEntry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class JsonWriter {
    private final StringBuilder builder = new StringBuilder();

    private void setUp() {
        builder.setLength(0);
    }

    private void start() {
        builder.append("{");
    }

    private void addType(final String type) {
        builder.append("\"type\": ");
        builder.append("\"");
        builder.append(type);
        builder.append("\", ");
    }

    private void startMetadata() {
        builder.append("\"metadata\": {");
    }

    private void end() {
        builder.append("}");
    }

    private void addData(DataEntry value) {
        builder.append("\"Data\": {\"value\" : \"");
        builder.append(value.toString());
        builder.append("\"}");
    }

    private void addData(List<DataEntry> value) {
        builder.append("\"Data\": [");
        for (int i = 0; i < value.size(); i++) {
            builder.append("{\"value\": \"");
            builder.append(value.get(i).toString());
            builder.append("\"}");
            if (i != value.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
    }

    private void addColumnsDescription(Map<String, List<DataEntry>> value, Map<String, String> map) {
        builder.append("\"Metadata\": {");
        builder.append("\"columnsDescription\" : ");
        JSONArray array = new JSONArray();
        JSONObject temp;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            temp = new JSONObject();
            temp.put(entry.getKey(), entry.getValue());
            if (!value.get(entry.getKey()).isEmpty()) {
                temp.put("type", value.get(entry.getKey()).get(0).getType().getType());
            }
            array.put(temp);
        }
        builder.append(array.toString());
        builder.append("}, ");
    }

    private Map<String, String> generateKeys(Set<String> k) {
        Iterator<String> iterator = k.iterator();
        Map<String, String> result = new HashMap<>();
        int i = 0;
        while (iterator.hasNext()) {
            result.put(iterator.next(), "col_" + i);
            i++;
        }
        return result;
    }

    private void addData(Map<String, List<DataEntry>> value) {
        Set<String> k = value.keySet();
        Map<String, String> keys = generateKeys(k);
        addColumnsDescription(value, keys);
        builder.append("\"Data\": [");
        Iterator<String> iterator = keys.keySet().iterator();
        int length = value.get(iterator.next()).size();
        String temp;
        for (int i = 0; i < length; i++) {
            iterator = keys.keySet().iterator();
            JSONObject row = new JSONObject();
            while (iterator.hasNext()) {
                temp = iterator.next();
                row.put(keys.get(temp), value.get(temp).get(i).toString());
            }
            builder.append(row.toString());
            if (i != length - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
    }


    public String toJson(DataEntry value) {
        if (value == null) {
            return null;
        }
        setUp();
        start();
        addType(value.getType().getType());
        addData(value);
        end();
        return builder.toString();
    }

    public String toJson(List<DataEntry> value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        setUp();
        start();
        addType("VECTOR_" + value.get(0).getType());
        addData(value);
        end();
        return builder.toString();
    }

    public String toJson(Map<String, List<DataEntry>> value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        setUp();
        start();
        addType("VECTORS");
        addData(value);
        end();
        return builder.toString();
    }
}
