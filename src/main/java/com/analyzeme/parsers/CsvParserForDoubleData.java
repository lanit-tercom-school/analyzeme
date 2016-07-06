package com.analyzeme.parsers;

import com.analyzeme.data.Data;
import com.analyzeme.data.DataArray;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lagroffe on 27.05.2016 18:23
 */
public class CsvParserForDoubleData {
    private final static CsvParserSettings SETTINGS;

    static {
        SETTINGS = new CsvParserSettings();
        SETTINGS.getFormat().setLineSeparator("\n");
    }

    public static DataArray parse(InputStream stream) throws IllegalArgumentException {
        if (stream == null) {
            throw new IllegalArgumentException("CsvParserForDoubleData parse(): impossible to parse null");
        }
        CsvParser parser = new CsvParser(SETTINGS);
        List<String[]> allRows = parser.parseAll(stream);
        if (!allRows.isEmpty() || allRows.size() == 1) {
            String[] names = allRows.get(0);
            DataArray result = new DataArray();
            for (int i = 1; i < allRows.size(); i++) {
                Map<String, Double> data = new HashMap<String, Double>();
                for (int j = 0; j < allRows.get(i).length; j++) {
                    data.put(names[j], Double.parseDouble(allRows.get(i)[j]));
                }
                Data d = new Data(data);
                result.addData(d);
            }
            return result;
        } else {
            throw new IllegalArgumentException("CsvParserForDoubleData parse(): empty or incorrect data to parse");
        }
    }
}
