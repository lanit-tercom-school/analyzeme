package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.ScalarResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GlobalMinimumAnalyzer implements IAnalyzer<Double> {
    private static final int NUMBER_OF_PARAMS = 0;
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.GlobalMinimumAnalyzer");
    }

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    public IResult analyze(Map<String, List<Double>> data) {
        LOGGER.debug("analyze(): method started");
        if (data == null || data.isEmpty()) {
            LOGGER.info("analyze(): empty argument");
            throw new IllegalArgumentException(
                    "No data to analyze");
        }
        if (data.size() == 1) {
            LOGGER.debug("analyze(): one-dim data");
            List<Double> list =
                    data.values().iterator().next();
            return new ScalarResult<>(
                    list.get(getMinInd(list)));
        }
        LOGGER.debug("analyze(): multi-dim data",
                data.size());
        List<Double> result = new ArrayList<Double>();
        Set<String> keys = data.keySet();
        int minInd = getMinInd(
                data.get(keys.iterator().next()));
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            result.add(
                    data.get(iterator.next())
                            .get(minInd));
        }
        LOGGER.debug("analyze(): method finished");
        return new ColumnResult(result);
    }

    private int getMinInd(final List<Double> column) {
        LOGGER.debug("getMinInd(): method started");
        if (column == null || column.get(0) == null) {
            LOGGER.info("getMinInd(): empty argument");
            throw new IllegalArgumentException(
                    "No data to analyze");
        }
        int minInd = 0;
        for (int i = 0; i < column.size(); i++) {
            if (column.get(minInd) > column.get(i)) {
                minInd = i;
            }
        }
        LOGGER.debug("getMinInd(): method finished");
        return minInd;
    }
}
