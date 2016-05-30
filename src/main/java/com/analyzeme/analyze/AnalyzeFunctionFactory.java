package com.analyzeme.analyze;

import java.util.Objects;

/**
 * Factory where we create new object of AnalyzeFunction interface.
 * Created by Sergey on 23.11.2015.
 */
public class AnalyzeFunctionFactory {

	/**
	 * @param functionType name of  AnalyzeFunction class
	 * @return object of some AnalyzeFunction class
	 */
	public AnalyzeFunction getFunction(final String functionType) throws IllegalArgumentException {

		if (functionType == null) {
			throw new IllegalArgumentException("param ''functionType'' has illegal value.( Null is illegal value)");
		}

		if (Objects.equals(functionType, "GlobalMaximum")) {
			return new GlobalMaximum();
		} else if (Objects.equals(functionType, "GlobalMinimum")) {
			return new GlobalMinimum();
		} else {
			throw new IllegalArgumentException("param ''functionType'' has illegal value. ''" + functionType + "'' isn't name of real class");
		}

	}
}
