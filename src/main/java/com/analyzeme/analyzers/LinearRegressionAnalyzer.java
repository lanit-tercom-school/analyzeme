package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.NotParsedJsonStringResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LinearRegressionAnalyzer implements IAnalyzer<Double> {
    private static final int
            NUMBER_OF_PARAMS = 2;
    private static final int
            NUMBER_OF_DECIMAL_PLACES = 3;
    private List<Double> x;
    private List<Double> y;
    private BigDecimal sumOfX =
            BigDecimal.ZERO;
    private BigDecimal sumOfY =
            BigDecimal.ZERO;
    private BigDecimal sumOfXAndY =
            BigDecimal.ZERO;
    private BigDecimal sumOfPoweredX =
            BigDecimal.ZERO;
    private BigDecimal size;
    private BigDecimal firstCoef;
    private BigDecimal secondCoef;
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.LinearRegressionAnalyzer");
    }

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    /**
     * @param data should contain two List<Double>
     * @return null if data is Points are inappropriate
     * otherwise NotParsedJsonStringResult like: y=a*x+b
     */
    public IResult analyze(Map<String, List<Double>> data) {
        LOGGER.debug("analyze(): method started");
        if (data == null || data.isEmpty()
                || data.size() < 2) {
            LOGGER.info("analyze(): wrong argument");
            throw new IllegalArgumentException(
                    "Null or empty data");
        }

        Iterator<String> iterator
                = data.keySet().iterator();
        x = data.get(iterator.next());
        y = data.get(iterator.next());
        if (x.size() != y.size()) {
            LOGGER.info(
                    "analyze(): columns' lengths are not equal");
            throw new IllegalArgumentException(
                    "Illegal type of data (not equal length)");
        }

        if (!calculateAllIntermediateValues()) {
            LOGGER.info(
                    "analyze(): intermediate values are not calculated");
            return null;
        }

        calculateFirstCoef();
        calculateSecondCoef();
        LOGGER.debug("analyze(): coefs are ready");
        if (secondCoef.compareTo(BigDecimal.ZERO) >= 0) {
            return new NotParsedJsonStringResult(
                    String.format("y=%s*x+%s",
                            firstCoef, secondCoef));
        }
        return new NotParsedJsonStringResult(
                String.format("y=%s*x%s",
                        firstCoef, secondCoef));
    }

    private void calculateFirstCoef() {
        firstCoef = (size.multiply(sumOfXAndY).
                subtract(sumOfX.multiply(sumOfY))).divide(
                (size.multiply(sumOfPoweredX).
                        subtract(sumOfX.multiply(sumOfX))),
                NUMBER_OF_DECIMAL_PLACES,
                RoundingMode.CEILING);
    }

    private void calculateSecondCoef() {
        secondCoef = (sumOfY.divide(size,
                NUMBER_OF_DECIMAL_PLACES,
                RoundingMode.CEILING)).
                subtract((firstCoef.multiply(sumOfX)).
                        divide(size,
                                NUMBER_OF_DECIMAL_PLACES,
                                RoundingMode.CEILING));
    }

    private boolean calculateAllIntermediateValues() {
        LOGGER.debug(
                "calculateAllIntermediateValues(): method started");
        try {
            for (int i = 0; i < x.size(); i++) {
                sumOfX = sumOfX.add(BigDecimal.
                        valueOf(x.get(i)));
                sumOfY = sumOfY.add(BigDecimal.
                        valueOf(y.get(i)));
                sumOfXAndY = sumOfXAndY.add(BigDecimal.
                        valueOf(x.get(i)
                                * y.get(i)));
                sumOfPoweredX = sumOfPoweredX.
                        add(BigDecimal.valueOf(
                                x.get(i) * x.get(i)));
            }
        } catch (NumberFormatException e) {
            LOGGER.info(e.toString());
            return false;
        } catch (NullPointerException e) {
            LOGGER.info(e.toString());
            return false;
        }
        size = BigDecimal.valueOf(x.size());
        LOGGER.debug(
                "calculateAllIntermediateValues(): method finished");
        return true;
    }
}
