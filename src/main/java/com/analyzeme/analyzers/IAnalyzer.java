package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.data.dataWithType.DataEntry;

import java.util.List;
import java.util.Map;

public interface IAnalyzer {
    //here String is the name of the column, and List<T> is the data in it (only T=Double is supported now)
    IResult analyze(Map<String, List<DataEntry>> data) throws Exception;

    int getNumberOfParams();
}
