package com.analyzeme.parsers;

import com.analyzeme.data.Data;
import com.analyzeme.data.DataArray;
import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataWithType;
import com.analyzeme.data.dataWithType.DataWithTypeArray;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ilya on 7/6/16.
 */

public class JsonParser implements IParser {
    public DataArray<Double> parse(InputStream input) throws InvalidFileException {
        if (input == null) {
            throw new NullPointerException();
        }

        DataArray<Double> result = new DataArray<Double>();
        try {
            JSONParser parser = new JSONParser();
            InputStreamReader reader = new InputStreamReader(input);
            JSONArray rows = (JSONArray) ((JSONObject) parser.parse(reader)).get("Data");

            for (JSONObject entry : (Iterable<JSONObject>) rows) {
                Map<String, Double> data = new HashMap<String, Double>();
                //System.out.println(entry);
                for (Object o : entry.keySet()) {
                    String key = (String) o;
                    data.put(key, Double.parseDouble((String) entry.get(key)));
                }
                result.addData(new Data<Double>(data));
            }
        } catch (Exception e) {
            throw new JsonParserException(JsonParserException.ExceptionType.PARSE_FILE);
        }
        return result;
    }

    @Override
    public DataWithTypeArray parseWithType(InputStream input) throws InvalidFileException {
        if (input == null) {
            throw new NullPointerException();
        }

        DataWithTypeArray result = new DataWithTypeArray();
        try {
            JSONParser parser = new JSONParser();
            InputStreamReader reader = new InputStreamReader(input);
            JSONArray rows = (JSONArray) ((JSONObject) parser.parse(reader)).get("Data");

            for (JSONObject entry : (Iterable<JSONObject>) rows) {
                Map<String, DataEntry> data = new HashMap<>();
                for (Object o : entry.keySet()) {
                    String key = (String) o;
                    data.put(key, DataEntry.fromString((String) entry.get(key)));
                }
                result.addData(new DataWithType(data));
            }
        } catch (Exception e) {
            throw new JsonParserException(JsonParserException.ExceptionType.PARSE_FILE);
        }
        return result;
    }
}

