package com.analyzeme.parsers;

import com.analyzeme.data.DataArray;

import java.io.InputStream;


/**
 * Created by ilya on 7/6/16.
 */
public interface IParser {
    DataArray<Double> parse(InputStream input) throws InvalidFileException;
}
