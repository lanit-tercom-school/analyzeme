package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.DoubleResult;
import com.analyzeme.analyzers.result.IResult;
import org.apache.commons.math.util.FastMath;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by lagroffe on 04.07.2016 14:32
 */
public class LinearCorrelationAnalyzer implements IAnalyzer {
    private List<Object> x;
    private List<Object> y;
    private static final int NUMBER_OF_DECIMAL_PLACES = 3;
    private BigDecimal averageY = BigDecimal.ZERO;
    private BigDecimal averageX = BigDecimal.ZERO;
    private BigDecimal averageXY = BigDecimal.ZERO;
    private BigDecimal dispersionX = BigDecimal.ZERO;
    private BigDecimal dispersionY = BigDecimal.ZERO;
    private int size;

    public IResult analyze(List<List<Object>> data) {
        //List<List<Point>>
        if (data == null || data.isEmpty() || data.size() < 2) {
            throw new IllegalArgumentException(
                    "Null or empty data");
        }
        x = data.get(0);
        y = data.get(1);
        if (x.size() != y.size()) {
            throw new IllegalArgumentException(
                    "Illegal type of data (not equal length)");
        }
        calcAverage();
        dispersion();
        BigDecimal sqrtX = BigDecimal.valueOf(
                Double.parseDouble("" +
                        FastMath.sqrt(dispersionX.doubleValue())));
        BigDecimal sqrtY = BigDecimal.valueOf(
                Double.parseDouble("" +
                        FastMath.sqrt(dispersionY.doubleValue())));
        BigDecimal result = (averageXY.subtract(
                averageX.multiply(averageY))).
                divide(sqrtX.multiply(sqrtY),
                        NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
        return new DoubleResult(result.doubleValue());
    }

    private void calcAverage() {
        BigDecimal sumOfY = BigDecimal.ZERO;
        BigDecimal sumOfX = BigDecimal.ZERO;
        BigDecimal sumOfXY = BigDecimal.ZERO;
        for (int i = 0; i < x.size(); i++) {
            BigDecimal X = BigDecimal.valueOf(
                    Double.parseDouble("" + x.get(i)));
            BigDecimal Y = BigDecimal.valueOf(
                    Double.parseDouble("" + y.get(i)));
            sumOfX = sumOfX.add(X);
            sumOfY = sumOfY.add(Y);
            sumOfXY = sumOfXY.add(X.multiply(Y));
        }
        size = x.size();
        averageX = sumOfX.divide(
                BigDecimal.valueOf(size),
                NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
        averageY = sumOfY.divide(
                BigDecimal.valueOf(size),
                NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
        averageXY = sumOfXY.divide(
                BigDecimal.valueOf(size),
                NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
    }

    private void dispersion() {
        BigDecimal sumOfY = BigDecimal.ZERO;
        BigDecimal sumOfX = BigDecimal.ZERO;
        for (int i = 0; i < x.size(); i++) {
            BigDecimal X = BigDecimal.valueOf(
                    Double.parseDouble("" + x.get(i)));
            BigDecimal Y = BigDecimal.valueOf(
                    Double.parseDouble("" + y.get(i)));
            sumOfX = sumOfX.add(
                    (X.subtract(averageX)).pow(2));
            sumOfY = sumOfY.add(
                    (Y.subtract(averageY)).pow(2));
        }
        dispersionX = sumOfX.divide(
                BigDecimal.valueOf(size),
                NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
        dispersionY = sumOfY.divide(
                BigDecimal.valueOf(size),
                NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
    }
}
