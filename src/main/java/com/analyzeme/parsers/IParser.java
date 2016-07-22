package com.analyzeme.parsers;

import com.analyzeme.data.DataArray;
import com.analyzeme.data.dataWithType.DataWithTypeArray;

import java.io.InputStream;


public interface IParser {
    @Deprecated
    DataArray<Double> parse(InputStream input) throws InvalidFileException;

    /**
     * Parses given file
     * @param input file to parse
     * @return matrix of entries
     * @throws InvalidFileException if file is not valid
     */
    DataWithTypeArray parseWithType(InputStream input) throws InvalidFileException;
}
