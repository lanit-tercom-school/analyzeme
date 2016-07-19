package com.analyzeme.parsers;

import com.analyzeme.repository.filerepository.TypeOfFile;


public class ParserFactory {
    /**
     * Gets parser for specified type
     * @param fileType type for parser
     * @return
     * @throws IllegalArgumentException if type is not supported
     */
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
