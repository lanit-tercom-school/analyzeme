package com.analyzeme.analyze;

/**
 * Factory where we create new object of AnalyzeFunction interface.
 * Created by Sergey on 23.11.2015.
 */
public class AnalyzeFunctionFactory {

	/**
	 * @param functionType name of  AnalyzeFunction class
	 * @return object of some AnalyzeFunction class
	 */
	public AnalyzeFunction getFunction(String functionType) {

		if (functionType == null)
			throw new IllegalArgumentException("param ''functionType'' has illegal value.( Null is illegal value)");

		if (functionType == "GlobalMaximum") {
			return new GlobalMaximum();
		} else if (functionType == "GlobalMinimum") {
			return new GlobalMinimum();
		} else
			throw new IllegalArgumentException("param ''functionType'' has illegal value. ''" + functionType + "'' isn't name of real class");

	}
}
