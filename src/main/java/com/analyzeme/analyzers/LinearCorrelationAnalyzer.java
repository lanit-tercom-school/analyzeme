package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataEntryType;
import org.apache.commons.math.util.FastMath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LinearCorrelationAnalyzer implements IAnalyzer {
    private static final int NUMBER_OF_PARAMS = 2;
    private List<DataEntry> x;
    private List<DataEntry> y;
    private static final int NUMBER_OF_DECIMAL_PLACES = 3;
    private BigDecimal averageY = BigDecimal.ZERO;
    private BigDecimal averageX = BigDecimal.ZERO;
    private BigDecimal averageXY = BigDecimal.ZERO;
    private BigDecimal dispersionX = BigDecimal.ZERO;
    private BigDecimal dispersionY = BigDecimal.ZERO;
    private int size;
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.LinearCorrelationAnalyzer");
    }

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    public IResult analyze(Map<String, List<DataEntry>> data) {
        LOGGER.debug("analyze(): method started");
        if (data == null || data.isEmpty()
                || data.size() < NUMBER_OF_PARAMS) {
            LOGGER.info("analyze(): incorrect argument");
            throw new IllegalArgumentException(
                    "Null or empty data");
        }

        Iterator<String> iterator =
                data.keySet().iterator();
        x = data.get(iterator.next());
        y = data.get(iterator.next());
        if (x.size() != y.size()) {
            LOGGER.info(
                    "analyze(): columns' lengths are not equal");
            throw new IllegalArgumentException(
                    "Illegal type of data (not equal length)");
        }

        calcAverage();
        dispersion();
        BigDecimal sqrtX = BigDecimal.valueOf(FastMath.sqrt(
                dispersionX.doubleValue()));
        BigDecimal sqrtY = BigDecimal.valueOf(FastMath.sqrt(
                dispersionY.doubleValue()));
        BigDecimal result = (averageXY.subtract(
                averageX.multiply(averageY))).
                divide(sqrtX.multiply(sqrtY),
                        NUMBER_OF_DECIMAL_PLACES,
                        RoundingMode.CEILING);

        LOGGER.debug("analyze(): method finished");
        return new ScalarResult(
                new DataEntry(DataEntryType.DOUBLE, result.doubleValue()));
    }

    private void calcAverage() {
        LOGGER.debug("calcAverage(): method started");
        BigDecimal sumOfY = BigDecimal.ZERO;
        BigDecimal sumOfX = BigDecimal.ZERO;
        BigDecimal sumOfXY = BigDecimal.ZERO;
        for (int i = 0; i < x.size(); i++) {
            BigDecimal X = BigDecimal.valueOf(x.get(i).getDoubleValue());
            BigDecimal Y = BigDecimal.valueOf(y.get(i).getDoubleValue());
            sumOfX = sumOfX.add(X);
            sumOfY = sumOfY.add(Y);
            sumOfXY = sumOfXY.add(X.multiply(Y));
        }
        size = x.size();
        averageX = sumOfX.divide(
                BigDecimal.valueOf(size),
                NUMBER_OF_DECIMAL_PLACES,
                RoundingMode.CEILING);
        averageY = sumOfY.divide(
                BigDecimal.valueOf(size),
                NUMBER_OF_DECIMAL_PLACES,
                RoundingMode.CEILING);
        averageXY = sumOfXY.divide(
                BigDecimal.valueOf(size),
                NUMBER_OF_DECIMAL_PLACES,
                RoundingMode.CEILING);
        LOGGER.debug("calcAverage(): method finished");
    }

    private void dispersion() {
        LOGGER.debug("dispersion(): method started");
        BigDecimal sumOfY = BigDecimal.ZERO;
        BigDecimal sumOfX = BigDecimal.ZERO;
        for (int i = 0; i < x.size(); i++) {
            BigDecimal X = BigDecimal.valueOf(x.get(i).getDoubleValue());
            BigDecimal Y = BigDecimal.valueOf(y.get(i).getDoubleValue());
            sumOfX = sumOfX.add(
                    (X.subtract(averageX)).pow(2));
            sumOfY = sumOfY.add(
                    (Y.subtract(averageY)).pow(2));
        }
        dispersionX = sumOfX.divide(
                BigDecimal.valueOf(size),
                NUMBER_OF_DECIMAL_PLACES,
                RoundingMode.CEILING);
        dispersionY = sumOfY.divide(
                BigDecimal.valueOf(size),
                NUMBER_OF_DECIMAL_PLACES,
                RoundingMode.CEILING);
        LOGGER.debug("dispersion(): method finished");
    }
}
