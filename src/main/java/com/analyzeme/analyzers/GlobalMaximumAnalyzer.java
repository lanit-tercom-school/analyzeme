package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataEntryType;
import com.analyzeme.data.dataWithType.ListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GlobalMaximumAnalyzer implements IAnalyzer {
    private static final int NUMBER_OF_PARAMS = 0;
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.GlobalMaximumAnalyzer");
    }

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    public IResult analyze(Map<String, List<DataEntry>> data) {
        LOGGER.debug("analyze(): method started");
        if (data == null || data.isEmpty()) {
            LOGGER.info("analyze(): empty argument");
            throw new IllegalArgumentException(
                    "No data to analyze");
        }
        if (data.size() == 1) {
            List<Double> list =
                    ListHandler.toDoubleList(data.values().iterator().next());
            LOGGER.debug("analyze(): one-dim data");
            return new ScalarResult( new DataEntry(DataEntryType.DOUBLE,
                    list.get(getMaxInd(list))));
        }
        LOGGER.debug("analyze(): multi-dim data",
                data.size());
        List<DataEntry> result = new ArrayList<DataEntry>();
        Set<String> keys = data.keySet();
        int maxInd = getMaxInd(ListHandler.toDoubleList(data.get(
                keys.iterator().next())));
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            result.add(data.get(
                    iterator.next()).get(maxInd));
        }
        LOGGER.debug("analyze(): method finished");
        return new ColumnResult(result);
    }

    private int getMaxInd(final List<Double> column) {
        LOGGER.debug("getMaxInd(): method started");
        if (column == null || column.get(0) == null) {
            LOGGER.info("getMaxInd(): illegal argument");
            throw new IllegalArgumentException(
                    "No data to analyze");
        }
        int maxInd = 0;
        for (int i = 0; i < column.size(); i++) {
            if (column.get(maxInd) < column.get(i)) {
                maxInd = i;
            }
        }
        LOGGER.debug("getMaxInd(): method finished");
        return maxInd;
    }
}
