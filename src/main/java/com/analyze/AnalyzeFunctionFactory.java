package com.analyze;

/**
 * Factory where we create new object of AnalyzeFunction interface.
 * Created by Sergey on 23.11.2015.
 */
public class AnalyzeFunctionFactory {

    public AnalyzeFunction getFunction(String functionType) {

        if (functionType == null) return null;

        if (functionType == "GlobalMaximum") {
            return new GlobalMaximum();
        } else if (functionType == "GlobalMinimum") {
            return new GlobalMinimum();
            //if param functionType is not equal to any real class we should return null
        } else return null;

    }
}
