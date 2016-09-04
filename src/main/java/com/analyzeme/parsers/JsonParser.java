package com.analyzeme.parsers;

import com.analyzeme.data.dataset.Data;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataArray;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Parses JSON file in format like:
 * <pre>{"Data":[{ "x": "0","y": "0"}, { "x": "1","y": "1"}]}</pre>
 */
public class JsonParser implements IParser {

    @Override
    public DataArray parse(
            InputStream input) throws InvalidFileException {
        if (input == null) {
            throw new NullPointerException();
        }

        DataArray result =
                new DataArray();
        try {
            JSONParser parser = new JSONParser();
            InputStreamReader reader =
                    new InputStreamReader(input);
            JSONArray rows = (JSONArray)
                    ((JSONObject) parser.parse(reader)).get("Data");

            for (JSONObject entry : (Iterable<JSONObject>) rows) {
                Map<String, DataEntry> data = new HashMap<>();
                for (Object o : entry.keySet()) {
                    String key = (String) o;
                    data.put(key,
                            DataEntry.fromString(
                                    (String) entry.get(key)));
                }
                result.addData(
                        new Data(data));
            }
        } catch (Exception e) {
            throw new JsonParserException(
                    JsonParserException.ExceptionType.PARSE_FILE);
        }
        return result;
    }
}

