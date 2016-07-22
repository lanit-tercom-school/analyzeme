package com.analyzeme.analyzers.result;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by lagroffe on 22.07.2016 11:43
 */
public class JsonWriter<T> {
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

    private void addData(Double value) {
        builder.append("\"Data\": [{\"value\" : \"");
        builder.append(value);
        builder.append("\"}]");
    }

    private void addData(List<Double> value) {
        builder.append("\"Data\": [");
        for (int i = 0; i < value.size(); i++) {
            builder.append("{\"value\": \"");
            builder.append(value.get(i));
            builder.append("\"}");
            if (i != value.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
    }

    private void addColumnsDescription(Map<String, String> map) {
        builder.append("\"Metadata\": {");
        builder.append("\"columnsDescription\" : ");
        JSONArray array = new JSONArray();
        JSONObject temp;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            temp = new JSONObject();
            temp.put(entry.getKey(), entry.getValue());
            array.put(temp);
        }
        builder.append(array.toString());
        builder.append("}, ");
    }

    private Map<String, String> generateKeys(Set<String> k) {
        Iterator<String> iterator = k.iterator();
        Map<String, String> result = new HashMap<>();
        int i = 0;
        while(iterator.hasNext()) {
             result.put(iterator.next(), "col_" + i);
            i++;
        }
        addColumnsDescription(result);
        return result;
    }

    private void addData(Map<String, List<Double>> value) {
        Set<String> k = value.keySet();
        Map<String, String> keys = generateKeys(k);
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

    public String toJson(Double value) {
        if (value == null) {
            return null;
        }
        setUp();
        start();
        addType("SCALAR_DOUBLE");
        addData(value);
        end();
        return builder.toString();
    }

    public String toJson(List<Double> value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        setUp();
        start();
        addType("VECTOR_DOUBLE");
        addData(value);
        end();
        return builder.toString();
    }

    public String toJson(Map<String, List<Double>> value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        setUp();
        start();
        addType("VECTORS_DOUBLE");
        //addColumnsDescription(value);
        addData(value);
        end();
        return builder.toString();
    }
}
