package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.DoubleListResult;
import com.analyzeme.analyzers.result.DoubleResult;
import com.analyzeme.analyzers.result.IResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagroffe on 05.07.2016 15:07
 */
public class GlobalMinimumAnalyzer implements IAnalyzer<Double> {

    public IResult analyze(List<List<Double>> data) {
        if (data == null || data.isEmpty() || data.get(0).isEmpty()) {
            throw new IllegalArgumentException("No data to analyze");
        }
        if (data.size() == 1) {
            return new DoubleResult(getMin(data.get(0)));
        }
        List<Double> list = new ArrayList<Double>();
        for (List<Double> column : data) {
            list.add(getMin(column));
        }
        return new DoubleListResult(list);
    }

    private Double getMin(final List<Double> column) {
        if(column.get(0) == null) {
            throw new IllegalArgumentException("No data to analyze");
        }
        Double minEl = column.get(0);
        for (Double obj : column) {
            if (minEl > obj) {
                minEl = obj;
            }
        }
        return minEl;
    }
}
