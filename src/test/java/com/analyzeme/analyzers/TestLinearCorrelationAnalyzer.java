package com.analyzeme.analyzers;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by lagroffe on 04.07.2016 14:42
 */
public class TestLinearCorrelationAnalyzer {
    final static double E = 0.0001;//constant need to compare two double numbers

    @Test
    public void test1LinearCorrelationAnalyzer() {
        List<Double> x = new ArrayList<Double>();
        x.add(0.);
        x.add(10.);
        x.add(20.);
        x.add(30.);
        List<Double> y = new ArrayList<Double>();
        y.add(10.);
        y.add(20.);
        y.add(30.);
        y.add(40.);
        List<List<Double>> data = new ArrayList<List<Double>>();
        data.add(x);
        data.add(y);
        LinearCorrelationAnalyzer Analyze = new LinearCorrelationAnalyzer();
        assertTrue("Linear correlation of y=10+x is wrong",
                Math.abs(1.0 - (Double) Analyze.analyze(data).getValue()) < E);

    }

    @Test
    public void test2LinearCorrelationAnalyzer() {
        List<Double> x = new ArrayList<Double>();
        x.add(0.);
        x.add(10.);
        x.add(20.);
        x.add(30.);
        List<Double> y = new ArrayList<Double>();
        y.add(10.);
        y.add(0.);
        y.add(-10.);
        y.add(-20.);
        List<List<Double>> data = new ArrayList<List<Double>>();
        data.add(x);
        data.add(y);
        LinearCorrelationAnalyzer Analyze = new LinearCorrelationAnalyzer();
        assertTrue("Linear correlation of y=10-x is wrong",
                Math.abs(-0.999 - (Double) Analyze.analyze(data).getValue()) < E);

    }

    @Test
    public void test3LinearCorrelationAnalyzer() {
        List<Double> x = new ArrayList<Double>();
        x.add(0.);
        x.add(10.);
        x.add(20.);
        x.add(30.);
        List<Double> y = new ArrayList<Double>();
        y.add(10.);
        y.add(0.);
        y.add(10.);
        y.add(-20.);
        List<List<Double>> data = new ArrayList<List<Double>>();
        data.add(x);
        data.add(y);
        LinearCorrelationAnalyzer Analyze = new LinearCorrelationAnalyzer();
        assertTrue("Linear correlation of not lineal data is wrong",
                Math.abs(-0.730 - (Double) Analyze.analyze(data).getValue()) < E);
    }
}
