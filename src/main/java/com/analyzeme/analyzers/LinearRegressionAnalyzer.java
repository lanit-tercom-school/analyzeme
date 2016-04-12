package com.analyzeme.analyzers;

import org.renjin.invoke.codegen.ArgumentException;

import java.util.List;

/**
 * Created by Ольга on 12.04.2016.
 */
public class LinearRegressionAnalyzer implements IAnalyzer {

    public String analyze(Object data) {
        List<Point<Double, Double>> points = (List<Point<Double, Double>>) data;
        if (points == null) {
            throw new ArgumentException("Incorrect data");
        }

        //todo analysis here
        return null;
    }
}
