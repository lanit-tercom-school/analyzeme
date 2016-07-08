package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.ScalarResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagroffe on 05.07.2016 15:07
 */
public class GlobalMinimumAnalyzer implements IAnalyzer<Double> {
    private static final int NUMBER_OF_PARAMS = 1;

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    public IResult analyze(List<List<Double>> data) {
        if (data == null || data.isEmpty() || data.get(0).isEmpty()) {
            throw new IllegalArgumentException("No data to analyze");
        }
        if (data.size() == 1) {
            return new ScalarResult<Double>(getMin(data.get(0)));
        }
        List<Double> list = new ArrayList<Double>();
        for (List<Double> column : data) {
            list.add(getMin(column));
        }
        return new ColumnResult<Double>(list);
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
