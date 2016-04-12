package com.analyzeme.analyzers;

import com.analyzeme.data.DataSet;

import java.util.List;

/**
 * Created by Ольга on 10.04.2016.
 */
public interface IAnalyzer {

    /**
     *
     * @param data
     * @return String with JSON inside(JSON should represent Point or List<Point>)
     */
    String analyze(List<DataSet> data);
}
