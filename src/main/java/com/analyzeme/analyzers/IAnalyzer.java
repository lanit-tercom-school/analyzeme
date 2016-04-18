package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.IResult;

import java.util.List;

/**
 * Created by Ольга on 10.04.2016.
 */
public interface IAnalyzer {

    IResult analyze(List<List<Point>> data);
}
