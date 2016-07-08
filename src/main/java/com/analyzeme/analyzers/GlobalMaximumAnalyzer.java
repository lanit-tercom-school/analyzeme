package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.ScalarResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lagroffe on 05.07.2016 14:57
 */
public class GlobalMaximumAnalyzer implements IAnalyzer<Double> {
    private static final int NUMBER_OF_PARAMS = 0;

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    public IResult analyze(List<List<Double>> data) {
        if (data == null || data.isEmpty() || data.get(0).isEmpty()) {
            throw new IllegalArgumentException("No data to analyze");
        }
        if (data.size() == 1) {
            return new ScalarResult<Double>(data.get(0).get(getMaxInd(data.get(0))));
        }
        List<Double> result = new ArrayList<Double>();
        int maxInd = getMaxInd(data.get(data.size() - 1));
        for (int i = 0; i < data.size(); i++) {
            result.add(data.get(i).get(maxInd));
        }
        return new ColumnResult(result);
    }

    private int getMaxInd(final List<Double> column) {
        if (column.get(0) == null) {
            throw new IllegalArgumentException("No data to analyze");
        }
        int maxInd = 0;
        for (int i = 0; i < column.size(); i++) {
            if (column.get(maxInd) < column.get(i)) {
                maxInd = i;
            }
        }
        return maxInd;
    }
}
