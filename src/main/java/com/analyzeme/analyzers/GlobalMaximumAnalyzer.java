package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.DoubleListResult;
import com.analyzeme.analyzers.result.DoubleResult;
import com.analyzeme.analyzers.result.IResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagroffe on 05.07.2016 14:57
 */
public class GlobalMaximumAnalyzer implements IAnalyzer<Double> {

    public IResult analyze(List<List<Double>> data) {
        if(data == null || data.isEmpty() || data.get(0).isEmpty()) {
            throw new IllegalArgumentException("No data to analyze");
        }
        if(data.size() == 1) {
            return new DoubleResult(getMax(data.get(0)));
        }
        List<Double> list = new ArrayList<Double>();
        for(List<Double> column: data) {
            list.add(getMax(column));
        }
        return new DoubleListResult(list);
    }

    private Double getMax(final List<Double> column) {
        if(column.get(0) == null) {
            throw new IllegalArgumentException("No data to analyze");
        }
        Double maxEl = column.get(0);
        for(Double obj: column) {
            if (maxEl < obj) {
                maxEl =obj;
            }
        }
        return maxEl;
    }
}
