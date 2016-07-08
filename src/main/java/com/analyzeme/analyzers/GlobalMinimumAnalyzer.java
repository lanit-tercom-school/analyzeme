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
    private static final int NUMBER_OF_PARAMS = 0;

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    public IResult analyze(List<List<Double>> data) {
        if (data == null || data.isEmpty() || data.get(0).isEmpty()) {
            throw new IllegalArgumentException("No data to analyze");
        }
        if (data.size() == 1) {
            return new ScalarResult<Double>(data.get(0).get(getMinInd(data.get(0))));
        }
        List<Double> result = new ArrayList<Double>();
        int minInd = getMinInd(data.get(data.size() - 1));
        for (int i = 0; i < data.size(); i++) {
            result.add(data.get(i).get(minInd));
        }
        return new ColumnResult(result);
    }

    private int getMinInd(final List<Double> column) {
        if (column.get(0) == null) {
            throw new IllegalArgumentException("No data to analyze");
        }
        int minInd = 0;
        for (int i = 0; i < column.size(); i++) {
            if (column.get(minInd) > column.get(i)) {
                minInd = i;
            }
        }
        return minInd;
    }
}
