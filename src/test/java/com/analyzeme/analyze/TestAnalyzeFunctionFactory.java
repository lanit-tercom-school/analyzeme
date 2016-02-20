package com.analyzeme.analyze;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Test class of AnalyzeFunctionFactory
 * Created by Sergey on 06.12.2015.
 */
public class TestAnalyzeFunctionFactory {

	@Test
	public void TestAnalyzeFunctionFactory() throws Exception {
		AnalyzeFunctionFactory testFactory = new AnalyzeFunctionFactory();
		//Create object of class GlobalMaximum.
		AnalyzeFunction functionMax = testFactory.getFunction("GlobalMaximum");
		//Test: If functionMax object of class GlobalMaximum?
		assertTrue(functionMax instanceof GlobalMaximum);
		//Create object of class GlobalMinimum.
		AnalyzeFunction functionMin = testFactory.getFunction("GlobalMinimum");
		//Test: If functionMin object of class GlobalMinimum?
		assertTrue(functionMin instanceof GlobalMinimum);
	}
}


