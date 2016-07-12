package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.ScalarResult;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 05.07.2016.
 */
public class KolmogorovSmirnovTestAnalyzer implements IAnalyzer<Double> {
    private static final int NUMBER_OF_PARAMS = 2;

    private double getCoefficient() {
        final double alpha = 0.05;
        final double epsilon = 0.0001; //alphas from the table don't differ more than that
        if (Math.abs(alpha - 0.05) < epsilon) return 1.36;
        return -1.0;//other cases are unimplemented for the moment
    }

    private class EmpiricalDistributionFunction {
        private List<Double> data;

        EmpiricalDistributionFunction(List<Double> dataIn) {
            Collections.sort(dataIn);//in ascending order.
            data = dataIn;
        }

        public double getValueAt(double x) {
            int i = 0;
            while (i < data.size() && data.get(i) < x) i++;
            return (((double) i) / data.size());//case when i==data.size() is included in this.
        }
    }

    private double calcSmirnovStatistic(List<Double> firstArray, List<Double> secondArray) {
        EmpiricalDistributionFunction firstFun = new EmpiricalDistributionFunction(firstArray);
        //EmpiricalDistributionFunction sndFun = new EmpiricalDistributionFunction(secondArray);
        //can be used for calculations checking.
        double firstSize = firstArray.size();
        double secondSize = secondArray.size();
        Collections.sort(secondArray);

        double statisticPositive = 1.0 / secondSize - firstFun.getValueAt(secondArray.get(0));
        for (int i = 1; i < secondSize; i++) {
            double temp = ((double) (i + 1)) / secondSize - firstFun.getValueAt(secondArray.get(i));
            if (temp > statisticPositive) statisticPositive = temp;
        }

        double statisticNegative = firstFun.getValueAt(secondArray.get(0));
        for (int i = 1; i < secondSize; i++) {
            double temp = firstFun.getValueAt(secondArray.get(i)) - ((double) i) / secondSize;
            if (temp > statisticNegative) statisticNegative = temp;
        }

        double statisticTrue = statisticPositive > statisticNegative ? statisticPositive : statisticNegative;

        statisticTrue *= Math.sqrt((firstSize * secondSize) / (firstSize + secondSize));
        return statisticTrue;
    }

    public ScalarResult<Boolean> analyze(Map<String, List<Double>> dataSets) throws IllegalArgumentException {
        if (dataSets.size() != NUMBER_OF_PARAMS)
            throw new IllegalArgumentException("Kolmogorov-Smirnov test can only be applied " +
                    "to exactly 2 data sets. Actually got " + dataSets.size());
        Iterator<String> iterator = dataSets.keySet().iterator();
        double statistic = calcSmirnovStatistic(dataSets.get(iterator.next()), dataSets.get(iterator.next()));
        double coefficient = getCoefficient();
        return new ScalarResult<>(statistic < coefficient);
    }

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }
}
