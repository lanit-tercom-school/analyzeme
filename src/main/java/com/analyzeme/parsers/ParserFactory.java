package com.analyzeme.parsers;

import com.analyzeme.repository.filerepository.TypeOfFile;

/**
 * Created by ilya on 7/6/16.
 */
public class ParserFactory {
    public static IParser createParser(TypeOfFile fileType) {
        switch (fileType) {
            case SIMPLE_JSON:
                return new JsonParser();
            case EXCEL:
                return new ExcelParser();
            case CSV:
                return new CsvParser();
            default:
                throw new IllegalArgumentException("Bad type of file!");
        }
    }
}
