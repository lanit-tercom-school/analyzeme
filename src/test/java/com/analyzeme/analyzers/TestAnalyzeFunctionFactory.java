package com.analyzeme.analyzers;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class TestAnalyzeFunctionFactory {

    @Test
    public void testAnalyzeFunctionFactory() throws Exception {
        IAnalyzer functionMax = AnalyzerFactory.getAnalyzer("Global Maximum");
        assertTrue(functionMax instanceof GlobalMaximumAnalyzer);
        IAnalyzer functionMin = AnalyzerFactory.getAnalyzer("Global Minimum");
        assertTrue(functionMin instanceof GlobalMinimumAnalyzer);
        IAnalyzer functionLinearCorrelation = AnalyzerFactory.getAnalyzer("Linear Correlation");
        assertTrue(functionLinearCorrelation instanceof LinearCorrelationAnalyzer);
        IAnalyzer functionLinearRegression = AnalyzerFactory.getAnalyzer("Linear Regression");
        assertTrue(functionLinearRegression instanceof LinearRegressionAnalyzer);
        IAnalyzer kolmogorovSmirnovTest = AnalyzerFactory.getAnalyzer("Kolmogorov Smirnov Test");
        assertTrue(kolmogorovSmirnovTest instanceof  KolmogorovSmirnovTestAnalyzer);
    }

}


