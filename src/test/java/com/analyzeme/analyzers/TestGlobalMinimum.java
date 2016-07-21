package com.analyzeme.analyzers;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		}Map<String, List<Double>> data = new HashMap();
		data.put("y", y);
		IAnalyzer tester = AnalyzerFactory.getAnalyzer("Global Minimum");
		assertTrue("Global minimum of y=sin(x^2)is wrong",
				Math.abs(-1 - (Double) tester.analyze(data).getValue()) < e);
	}
}

