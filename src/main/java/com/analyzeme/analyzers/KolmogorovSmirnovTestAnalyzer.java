package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataEntryType;
import com.analyzeme.data.dataWithType.ListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class KolmogorovSmirnovTestAnalyzer implements IAnalyzer {
    private static final int NUMBER_OF_PARAMS = 2;
    private static final Logger LOGGER;
    private static final double APLHA = 0.05;
    //alphas from the table don't differ more than that
    private static final double EPSILON = 0.0001;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.KolmogorovSmirnovTestAnalyzer");
    }

    private double getCoefficient() {
        if (Math.abs(APLHA - 0.05) < EPSILON) {
            return 1.36;
        }
        return -1.0;
        //other cases are unimplemented for the moment
    }

    private class EmpiricalDistributionFunction {
        private List<Double> data;

        EmpiricalDistributionFunction(List<Double> dataIn) {
            //in ascending order.
            Collections.sort(dataIn);
            data = dataIn;
        }

        public double getValueAt(double x) {
            int i = 0;
            while (i < data.size() && data.get(i) < x) {
                i++;
            }
            return ((double) i) / data.size();
            // /case when i==data.size() is included in this.
        }
    }

    private double calcSmirnovStatistic(List<Double> firstArray,
                                        List<Double> secondArray) {
        LOGGER.debug("calcSmirnovStatistic(): method started");
        EmpiricalDistributionFunction firstFun =
                new EmpiricalDistributionFunction(
                        firstArray);
        double firstSize = firstArray.size();
        double secondSize = secondArray.size();
        Collections.sort(secondArray);

        double statisticPositive = 1.0 / secondSize
                - firstFun.getValueAt(secondArray.get(0));
        for (int i = 1; i < secondSize; i++) {
            double temp = ((double) (i + 1)) / secondSize
                    - firstFun.getValueAt(secondArray.get(i));
            if (temp > statisticPositive) {
                statisticPositive = temp;
            }
        }

        double statisticNegative =
                firstFun.getValueAt(secondArray.get(0));
        for (int i = 1; i < secondSize; i++) {
            double temp = firstFun.getValueAt(
                    secondArray.get(i)) - ((double) i) / secondSize;
            if (temp > statisticNegative) {
                statisticNegative = temp;
            }
        }

        double statisticTrue = statisticPositive
                > statisticNegative ? statisticPositive
                : statisticNegative;
        statisticTrue *= Math.sqrt(
                (firstSize * secondSize) / (firstSize + secondSize));

        LOGGER.debug("calcSmirnovStatistic(): method finished");
        return statisticTrue;
    }

    public ScalarResult analyze(
            Map<String, List<DataEntry>> dataSets) throws IllegalArgumentException {
        LOGGER.debug("analyze(): method started");
        if (dataSets.size() != NUMBER_OF_PARAMS) {
            LOGGER.info("analyze(): wrong argument");
            throw new IllegalArgumentException(
                    "Kolmogorov-Smirnov test can only be applied "
                            + "to exactly 2 data sets. Actually got "
                            + dataSets.size());
        }
        Iterator<String> iterator =
                dataSets.keySet().iterator();
        double statistic = calcSmirnovStatistic(
                ListHandler.toDoubleList(dataSets.get(iterator.next())),
                ListHandler.toDoubleList(dataSets.get(iterator.next())));
        LOGGER.debug("analyze(): Smirnov statistic is calculated");
        double coefficient = getCoefficient();
        LOGGER.debug("analyze(): coefficient is found");
        LOGGER.debug("analyze(): method finished");
        return new ScalarResult(new DataEntry(DataEntryType.BOOLEAN, statistic < coefficient));
    }

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }
}
