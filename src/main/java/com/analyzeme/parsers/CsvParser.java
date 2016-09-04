package com.analyzeme.parsers;

import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.Data;
import com.analyzeme.data.dataset.DataArray;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parses CSV file in default format
 */
public class CsvParser implements IParser {
    private final static com.univocity.parsers.csv.CsvParserSettings SETTINGS;

    static {
        SETTINGS = new com.univocity.parsers.csv.CsvParserSettings();
        SETTINGS.getFormat().setLineSeparator("\n");
    }

    @Override
    public DataArray parse(
            InputStream input) throws InvalidFileException {
        if (input == null) {
            throw new InvalidFileException(
                    "CsvParser parseTime(): impossible to parseTime null");
        }
        com.univocity.parsers.csv.CsvParser parser
                = new com.univocity.parsers.csv.CsvParser(SETTINGS);
        try {
            List<String[]> allRows = parser.parseAll(input);
            if (!allRows.isEmpty() || allRows.size() == 1) {
                String[] names = allRows.get(0);
                DataArray result
                        = new DataArray();
                for (int i = 1; i < allRows.size(); i++) {
                    Map<String, DataEntry> data = new HashMap<>();
                    for (int j = 0; j < allRows.get(i).length; j++) {
                        data.put(names[j],
                                DataEntry.fromString(allRows.get(i)[j]));
                    }
                    result.addData(new Data(data));
                }
                return result;
            } else {
                throw new InvalidFileException(
                        "CsvParser parseTime(): empty or incorrect data to parseTime");
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidFileException(
                    "CsvParser parseTime(): empty or incorrect data to parseTime");
        }

    }
}
