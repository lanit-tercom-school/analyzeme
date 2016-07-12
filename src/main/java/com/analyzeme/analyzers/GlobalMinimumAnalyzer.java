package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.ScalarResult;

import java.util.*;

/**
 * Created by lagroffe on 05.07.2016 15:07
 */
public class GlobalMinimumAnalyzer implements IAnalyzer<Double> {
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
            return new ScalarResult<>(list.get(getMinInd(list)));
        }
        List<Double> result = new ArrayList<Double>();
        Set<String> keys = data.keySet();
        int minInd = getMinInd(data.get(keys.iterator().next()));
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            result.add(data.get(iterator.next()).get(minInd));
        }
        return new ColumnResult(result);
    }

    private int getMinInd(final List<Double> column) {
        if (column == null || column.get(0) == null) {
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
