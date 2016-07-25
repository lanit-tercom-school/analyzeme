package com.analyzeme.analyzers;

import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataEntryType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertTrue;

/**
 * Created by lagroffe on 04.07.2016 14:42
 */
public class TestLinearCorrelationAnalyzer {
    final static double E = 0.0001;//constant need to compare two double numbers

    @Test
    public void test1LinearCorrelationAnalyzer() {
        List<DataEntry> x = new ArrayList<DataEntry>();
        x.add(new DataEntry(DataEntryType.DOUBLE, 0.));
        x.add(new DataEntry(DataEntryType.DOUBLE, 10.));
        x.add(new DataEntry(DataEntryType.DOUBLE, 20.));
        x.add(new DataEntry(DataEntryType.DOUBLE, 30.));
        List<DataEntry> y = new ArrayList<DataEntry>();
        y.add(new DataEntry(DataEntryType.DOUBLE, 10.));
        y.add(new DataEntry(DataEntryType.DOUBLE, 20.));
        y.add(new DataEntry(DataEntryType.DOUBLE, 30.));
        y.add(new DataEntry(DataEntryType.DOUBLE, 40.));
        Map<String, List<DataEntry>> data = new HashMap();
        data.put("x", x);
        data.put("y", y);
        LinearCorrelationAnalyzer Analyze = new LinearCorrelationAnalyzer();
        assertTrue("Linear correlation of y=10+x is wrong",
                Math.abs(1.0 - ((ScalarResult) Analyze.analyze(data)).getValue().getDoubleValue()) < E);

    }

    @Test
    public void test2LinearCorrelationAnalyzer() {
        List<DataEntry> x = new ArrayList<DataEntry>();
        x.add(new DataEntry(DataEntryType.DOUBLE, 0.));
        x.add(new DataEntry(DataEntryType.DOUBLE, 10.));
        x.add(new DataEntry(DataEntryType.DOUBLE, 20.));
        x.add(new DataEntry(DataEntryType.DOUBLE, 30.));
        List<DataEntry> y = new ArrayList<DataEntry>();
        y.add(new DataEntry(DataEntryType.DOUBLE, 10.));
        y.add(new DataEntry(DataEntryType.DOUBLE, 0.));
        y.add(new DataEntry(DataEntryType.DOUBLE, -10.));
        y.add(new DataEntry(DataEntryType.DOUBLE, -20.));
        Map<String, List<DataEntry>> data = new HashMap();
        data.put("x", x);
        data.put("y", y);
        LinearCorrelationAnalyzer Analyze = new LinearCorrelationAnalyzer();
        assertTrue("Linear correlation of y=10-x is wrong",
                Math.abs(-0.999 - ((ScalarResult) Analyze.analyze(data)).getValue().getDoubleValue()) < E);

    }

    @Test
    public void test3LinearCorrelationAnalyzer() {
        List<DataEntry> x = new ArrayList<DataEntry>();
        x.add(new DataEntry(DataEntryType.DOUBLE, 0.));
        x.add(new DataEntry(DataEntryType.DOUBLE, 10.));
        x.add(new DataEntry(DataEntryType.DOUBLE, 20.));
        x.add(new DataEntry(DataEntryType.DOUBLE, 30.));
        List<DataEntry> y = new ArrayList<DataEntry>();
        y.add(new DataEntry(DataEntryType.DOUBLE, 10.));
        y.add(new DataEntry(DataEntryType.DOUBLE, 0.));
        y.add(new DataEntry(DataEntryType.DOUBLE, 10.));
        y.add(new DataEntry(DataEntryType.DOUBLE, -20.));
        Map<String, List<DataEntry>> data = new HashMap();
        data.put("x", x);
        data.put("y", y);
        LinearCorrelationAnalyzer Analyze = new LinearCorrelationAnalyzer();
        assertTrue("Linear correlation of not lineal data is wrong",
                Math.abs(-0.730 - ((ScalarResult) Analyze.analyze(data)).getValue().getDoubleValue()) < E);
    }
}
