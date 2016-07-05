package com.analyzeme.analyzers;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class TestAnalyzeFunctionFactory {

    @Test
    public void testAnalyzeFunctionFactory() throws Exception {
        IAnalyzer functionMax = AnalyzerFactory.getAnalyzer("GlobalMaximum");
        assertTrue(functionMax instanceof GlobalMaximumAnalyzer);
        IAnalyzer functionMin = AnalyzerFactory.getAnalyzer("GlobalMinimum");
        assertTrue(functionMin instanceof GlobalMinimumAnalyzer);
        IAnalyzer functionLinearCorrelation = AnalyzerFactory.getAnalyzer("LinearCorrelation");
        assertTrue(functionLinearCorrelation instanceof LinearCorrelationAnalyzer);
        IAnalyzer functionLinearRegression = AnalyzerFactory.getAnalyzer("LinearRegression");
        assertTrue(functionLinearRegression instanceof LinearRegressionAnalyzer);
    }

}


