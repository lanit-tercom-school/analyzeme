package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.NotParsedJsonStringResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by lagroffe on 04.07.2016 14:33
 */
public class LinearRegressionAnalyzer implements IAnalyzer<Double> {
    private static final int NUMBER_OF_PARAMS = 2;
    private static final int NUMBER_OF_DECIMAL_PLACES = 3;
    private List<Double> x;
    private List<Double> y;
    private BigDecimal sumOfX = BigDecimal.ZERO;
    private BigDecimal sumOfY = BigDecimal.ZERO;
    private BigDecimal sumOfXAndY = BigDecimal.ZERO;
    private BigDecimal sumOfPoweredX = BigDecimal.ZERO;
    private BigDecimal size;
    private BigDecimal firstCoef;
    private BigDecimal secondCoef;

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    /**
     * @param data should contain two List<Double>
     * @return null if data is Points are inappropriate
     * otherwise NotParsedJsonStringResult like: y=a*x+b
     */
    public IResult analyze(List<List<Double>> data) {
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
        if (!calculateAllIntermediateValues()) {
            return null;
        }
        calculateFirstCoef();
        calculateSecondCoef();
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
                NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
    }

    private void calculateSecondCoef() {
        secondCoef = (sumOfY.divide(size,
                NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING)).
                subtract((firstCoef.multiply(sumOfX)).
                        divide(size, NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING));
    }

    private boolean calculateAllIntermediateValues() {
        try {
            for (int i = 0; i < x.size(); i++) {
                sumOfX = sumOfX.add(BigDecimal.
                        valueOf(Double.parseDouble("" + x.get(i))));
                sumOfY = sumOfY.add(BigDecimal.
                        valueOf(Double.parseDouble("" + y.get(i))));
                sumOfXAndY = sumOfXAndY.add(BigDecimal.
                        valueOf(Double.parseDouble("" + x.get(i))
                                * Double.parseDouble("" + y.get(i))));
                sumOfPoweredX = sumOfPoweredX.
                        add(BigDecimal.valueOf(Double.parseDouble("" + x.get(i))
                                * Double.parseDouble("" + x.get(i))));
            }
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        size = BigDecimal.valueOf(x.size());
        return true;
    }
}
