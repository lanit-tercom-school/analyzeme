package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.DoubleResult;
import com.analyzeme.analyzers.result.IResult;
import org.apache.commons.math.util.FastMath;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by asus on 02.05.2016.
 */
public class LinearCorrelationAnalyzer implements IAnalyzer {
    public List<Point> points;
    private static final int NUMBER_OF_DECIMAL_PLACES = 3;
    public BigDecimal averageY = BigDecimal.ZERO;
    public BigDecimal averageX = BigDecimal.ZERO;
    public BigDecimal averageXY = BigDecimal.ZERO;
    public BigDecimal dispersionX = BigDecimal.ZERO;
    public BigDecimal dispersionY = BigDecimal.ZERO;
    BigDecimal result;
    private int size;

    public IResult analyze(List<List<Point>> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Null or empty data");
        }
        points = data.get(0);
        calcAverage();
        dispersion();
        BigDecimal sqrtX=BigDecimal.valueOf(Double.parseDouble("" +FastMath.sqrt(dispersionX.doubleValue())));
        BigDecimal sqrtY=BigDecimal.valueOf(Double.parseDouble("" +FastMath.sqrt(dispersionY.doubleValue())));
        result= (averageXY.subtract(averageX.multiply(averageY))).divide(sqrtX.multiply(sqrtY),NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
        return new DoubleResult(result.doubleValue());
    }

    public void calcAverage() {
        BigDecimal sumOfY = BigDecimal.ZERO;
        BigDecimal sumOfX = BigDecimal.ZERO;
        BigDecimal sumOfXY = BigDecimal.ZERO;
        for (Point point : points) {
            BigDecimal X = BigDecimal.valueOf(Double.parseDouble("" + point.getX()));
            BigDecimal Y = BigDecimal.valueOf(Double.parseDouble("" + point.getY()));
            sumOfX = sumOfX.add(X);
            sumOfY = sumOfY.add(Y);
            sumOfXY = sumOfXY.add(X.multiply(Y));
        }
        size = points.size();
        averageX = sumOfX.divide(BigDecimal.valueOf(size), NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
        averageY = sumOfY.divide(BigDecimal.valueOf(size), NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
        averageXY = sumOfXY.divide(BigDecimal.valueOf(size), NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);

    }

    public void dispersion() {
        BigDecimal sumOfY = BigDecimal.ZERO;
        BigDecimal sumOfX = BigDecimal.ZERO;
        for (Point point : points) {
            BigDecimal X = BigDecimal.valueOf(Double.parseDouble("" + point.getX()));
            BigDecimal Y = BigDecimal.valueOf(Double.parseDouble("" + point.getY()));
            sumOfX = sumOfX.add((X.subtract(averageX)).pow(2));
            sumOfY = sumOfY.add((Y.subtract(averageY)).pow(2));
        }
        dispersionX=sumOfX.divide(BigDecimal.valueOf(size), NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
       // dispersionY=sumOfY.divide(BigDecimal.valueOf(size), NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
        dispersionY=sumOfY.divide(BigDecimal.valueOf(size), NUMBER_OF_DECIMAL_PLACES, RoundingMode.CEILING);
    }


}
