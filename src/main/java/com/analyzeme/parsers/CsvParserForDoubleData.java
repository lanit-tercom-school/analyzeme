package com.analyzeme.parsers;

import com.analyzeme.data.DoubleData;
import com.analyzeme.data.DoubleDataArray;
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
    private static CsvParserSettings settings;

    static {
        settings = new CsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
    }

    public static DoubleDataArray parse(InputStream stream) throws IllegalArgumentException {
        if (stream == null)
            throw new IllegalArgumentException("CsvParserForDoubleData parse(): impossible to parse null");
        CsvParser parser = new CsvParser(settings);
        List<String[]> allRows = parser.parseAll(stream);
        if (!allRows.isEmpty() || allRows.size() == 1) {
            String[] names = allRows.get(0);
            DoubleDataArray result = new DoubleDataArray();
            for (int i = 1; i < allRows.size(); i++) {
                Map<String, Double> data = new HashMap<String, Double>();
                for (int j = 0; j < allRows.get(i).length; j++) {
                    data.put(names[j], Double.parseDouble(allRows.get(i)[j]));
                }
                DoubleData d = new DoubleData(data);
                result.addData(d);
            }
            return result;
        } else {
            throw new IllegalArgumentException("CsvParserForDoubleData parse(): empty or incorrect data to parse");
        }
    }
}
