package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.IResult;

import java.util.List;

/**
 * Created by lagroffe on 04.07.2016 14:29
 */
public interface IAnalyzer<T> {
    //now here should be List of List<Double>(columns)
    IResult analyze(List<List<T>> data);
}
