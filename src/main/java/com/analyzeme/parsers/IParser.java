package com.analyzeme.parsers;

import com.analyzeme.data.DataArray;
import com.analyzeme.data.dataWithType.DataWithTypeArray;

import java.io.InputStream;


/**
 * Created by ilya on 7/6/16.
 */
public interface IParser {
    DataArray<Double> parse(InputStream input) throws InvalidFileException;

    DataWithTypeArray parseWithType(InputStream input) throws InvalidFileException;
}
