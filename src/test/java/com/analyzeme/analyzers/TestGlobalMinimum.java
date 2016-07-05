package com.analyzeme.analyzers;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

public class TestGlobalMinimum {
	private final double e = 0.001;

	@Test
	public void testGlobalMin() throws Exception {
		double x;
		List<Double> y = new ArrayList<Double>();
		for (int i = 0; i < 1001; i++) {
			x = 4 - i * 0.008;
			y.add(Math.sin(x * x));
		}
		List<List<Double>> toAnalyze = new ArrayList<List<Double>>();
		toAnalyze.add(y);
		IAnalyzer tester = AnalyzerFactory.getAnalyzer("GlobalMinimum");
		assertTrue("Global minimum of y=sin(x^2)is wrong",
				Math.abs(-1 - (Double) tester.analyze(toAnalyze).getValue()) < e);
	}
}

