package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.ScalarResult;

import java.util.*;

public class GlobalMaximumAnalyzer implements IAnalyzer<Double> {
    private static final int NUMBER_OF_PARAMS = 0;

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    public IResult analyze(Map<String, List<Double>> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("No data to analyze");
        }
        if (data.size() == 1) {
            List<Double> list = data.values().iterator().next();
            return new ScalarResult<>(list.get(getMaxInd(list)));
        }
        List<Double> result = new ArrayList<Double>();
        Set<String> keys = data.keySet();
        int maxInd = getMaxInd(data.get(keys.iterator().next()));
        Iterator<String> iterator = keys.iterator();
        while(iterator.hasNext()) {
            result.add(data.get(iterator.next()).get(maxInd));
        }
        return new ColumnResult(result);
    }

    private int getMaxInd(final List<Double> column) {
        if (column == null || column.get(0) == null) {
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
