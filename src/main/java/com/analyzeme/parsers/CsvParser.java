package com.analyzeme.parsers;

import com.analyzeme.data.Data;
import com.analyzeme.data.DataArray;


import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lagroffe on 27.05.2016 18:23
 */
public class CsvParser implements IParser{
    private final static com.univocity.parsers.csv.CsvParserSettings SETTINGS;

    static {
        SETTINGS = new com.univocity.parsers.csv.CsvParserSettings();
        SETTINGS.getFormat().setLineSeparator("\n");
    }

    public  DataArray<Double> parse(InputStream stream) throws InvalidFileException{
        if (stream == null) {
            throw new InvalidFileException("CsvParser parse(): impossible to parse null");
        }
        com.univocity.parsers.csv.CsvParser parser = new com.univocity.parsers.csv.CsvParser(SETTINGS);
        List<String[]> allRows = parser.parseAll(stream);
        if (!allRows.isEmpty() || allRows.size() == 1) {
            String[] names = allRows.get(0);
            DataArray<Double> result = new DataArray<Double>();
            for (int i = 1; i < allRows.size(); i++) {
                Map<String, Double> data = new HashMap<String, Double>();
                for (int j = 0; j < allRows.get(i).length; j++) {
                    data.put(names[j], Double.parseDouble(allRows.get(i)[j]));
                }
                Data<Double> d = new Data<Double>(data);
                result.addData(d);
            }
            return result;
        } else {
            throw new InvalidFileException("CsvParser parse(): empty or incorrect data to parse");
        }
    }
}
