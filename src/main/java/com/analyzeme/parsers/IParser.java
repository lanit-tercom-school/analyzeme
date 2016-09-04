package com.analyzeme.parsers;

import com.analyzeme.data.dataset.DataArray;

import java.io.InputStream;


public interface IParser {

    /**
     * Parses given file
     *
     * @param input file to parse
     * @return matrix of entries
     * @throws InvalidFileException if file is not valid
     */
    DataArray parse(InputStream input) throws InvalidFileException;
}
