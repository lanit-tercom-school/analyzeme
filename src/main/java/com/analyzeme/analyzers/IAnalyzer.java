package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.IResult;

import java.util.List;
import java.util.Map;

public interface IAnalyzer<T> {
    //here String is the name of the column, and List<T> is the data in it (only T=Double is supported now)
    IResult analyze(Map<String, List<T>> data);

    int getNumberOfParams();
}
