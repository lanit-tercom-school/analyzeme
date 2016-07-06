package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.ScalarResult;

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
            return new ScalarResult<Double>(getMax(data.get(0)));
        }
        List<Double> list = new ArrayList<Double>();
        for(List<Double> column: data) {
            list.add(getMax(column));
        }
        return new ColumnResult<Double>(list);
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
