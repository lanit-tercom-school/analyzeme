package com.analyzeme.analyze;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;


/**
 * Test class of AnalyzeFunctionFactory
 * Created by Sergey on 06.12.2015.
 */
public class TestAnalyzeFunctionFactory {

	final double e = 0.001;//constant need to compare two double numbers

	@Test
	public void TestAnalyzeFunctionFactory() throws Exception {

		double[] X_array = new double[1001];
		double[] Y_array = new double[1001];

		AnalyzeFunctionFactory testFactory = new AnalyzeFunctionFactory();

		AnalyzeFunction functionGlobalMaximum = testFactory.getFunction("GlobalMaximum");

		//generate array of  y=sin(x^2)
		for (int i = 0; i < 1001; i++) {
			X_array[i] = 4 - i * 0.008;
			Y_array[i] = Math.sin(X_array[i] * X_array[i]);

		}
		//Compare 1 and maximum of y=sin(x^2)
		assertTrue("Global maximum of y=sin(x^2) is wrong", Math.abs(1 - Y_array[functionGlobalMaximum.Calc(X_array, Y_array)]) < e);


		AnalyzeFunction functionGlobalMinimum = testFactory.getFunction("GlobalMinimum");
		//generate array of  y=sin(x^2)
		for (int i = 0; i < 1001; i++) {
			X_array[i] = 4 - i * 0.008;
			Y_array[i] = Math.sin(X_array[i] * X_array[i]);

		}
		//Compare -1 and minimum of y=sin(x^2)
		assertTrue("Global minimum of y=sin(x^2)is wrong", Math.abs(-1 - Y_array[functionGlobalMinimum.Calc(X_array, Y_array)]) < e);

	}

}


