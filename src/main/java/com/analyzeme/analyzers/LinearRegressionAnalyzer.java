package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.StringResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by Ольга on 12.04.2016.
 */
public class LinearRegressionAnalyzer implements IAnalyzer {

    private static final int NUMBER_OF_DECIMAL_PLACES = 3;
    private List<Point> points;
    private BigDecimal sumOfX = BigDecimal.ZERO;
    private BigDecimal sumOfY = BigDecimal.ZERO;
    private BigDecimal sumOfXAndY = BigDecimal.ZERO;
    private BigDecimal sumOfPoweredX = BigDecimal.ZERO;
    private BigDecimal size;
    private BigDecimal firstCoef;
    private BigDecimal secondCoef;

    /**
     * @param data should contain only one List<Point>
     * @return null if data is Points are inappropriate
     * otherwise StringResult like: y=a*x+b
     */
    public IResult analyze(List<List<Point>> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Null or empty data");
        }
        points = data.get(0);
        if (!calculateAllIntermediateValues()) {
            return null;
        }
        calculateFirstCoef();
        calculateSecondCoef();
        if (secondCoef.compareTo(BigDecimal.ZERO) >= 0) {
            return new StringResult(String.format("y=%s*x+%s", firstCoef, secondCoef));
        }
        return new StringResult(String.format("y=%s*x%s", firstCoef, secondCoef));
    }

    private void calculateFirstCoef() {
        firstCoef = (size.multiply(sumOfXAndY).subtract(sumOfX.multiply(sumOfY))).divide(
                (size.multiply(sumOfPoweredX).subtract(sumOfX.multiply(sumOfX))), NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
    }

    private void calculateSecondCoef() {
        secondCoef = (sumOfY.divide(size, NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING)).subtract
                ((firstCoef.multiply(sumOfX)).divide(size, NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING));
    }

    private boolean calculateAllIntermediateValues() {
        try {
            for (Point point : points) {
                sumOfX = sumOfX.add(BigDecimal.valueOf(Double.parseDouble("" + point.getX())));
                sumOfY = sumOfY.add(BigDecimal.valueOf(Double.parseDouble("" + point.getY())));
                sumOfXAndY = sumOfXAndY.add(BigDecimal.valueOf(Double.parseDouble("" + point.getX())
                        * Double.parseDouble("" + point.getY())));
                sumOfPoweredX = sumOfPoweredX.add(BigDecimal.valueOf(Double.parseDouble("" + point.getX())
                        * Double.parseDouble("" + point.getX())));
            }
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        size = BigDecimal.valueOf(points.size());
        return true;
    }
}
