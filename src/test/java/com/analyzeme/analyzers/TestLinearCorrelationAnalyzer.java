package com.analyzeme.analyzers;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by asus on 16.05.2016.
 */
public class TestLinearCorrelationAnalyzer {
    final double e = 0.0001;//constant need to compare two double numbers

    @Test
    public void test1LinearCorrelationAnalyzer() {
        List<List<Point>> data = new ArrayList<List<Point>>();
        List<Point> list = new ArrayList<Point>();
        list.add(new Point((double) 0, (double) 10));
        list.add(new Point((double) 10, (double) 20));
        list.add(new Point((double) 20, (double) 30));
        list.add(new Point((double) 30, (double) 40));
        data.add(list);
        LinearCorrelationAnalyzer Analyze = new LinearCorrelationAnalyzer();
        assertTrue("Linear correlation of y=10+x is wrong", Math.abs(1.0 - (Double) Analyze.analyze(data).getValue()) < e);

    }

    @Test
    public void test2LinearCorrelationAnalyzer() {
        List<List<Point>> data = new ArrayList<List<Point>>();
        List<Point> list = new ArrayList<Point>();
        list.add(new Point((double) 0, (double) 10));
        list.add(new Point((double) 10, (double) 0));
        list.add(new Point((double) 20, (double) -10));
        list.add(new Point((double) 30, (double) -20));
        data.add(list);
        LinearCorrelationAnalyzer Analyze = new LinearCorrelationAnalyzer();
        assertTrue("Linear correlation of y=10-x is wrong", Math.abs(-0.999 - (Double) Analyze.analyze(data).getValue()) < e);

    }

    @Test
    public void test3LinearCorrelationAnalyzer() {
        List<List<Point>> data = new ArrayList<List<Point>>();
        List<Point> list = new ArrayList<Point>();
        list.add(new Point((double) 0, (double) 10));
        list.add(new Point((double) 10, (double) 0));
        list.add(new Point((double) 20, (double) 10));
        list.add(new Point((double) 30, (double) -20));
        data.add(list);
        LinearCorrelationAnalyzer Analyze = new LinearCorrelationAnalyzer();
        assertTrue("Linear correlation of not lineal data is wrong", Math.abs(-0.730 - (Double) Analyze.analyze(data).getValue()) < e);

    }
}