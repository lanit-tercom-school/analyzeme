package com.analyzeme.analyzers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lagroffe on 04.07.2016 14:31
 */
public class AnalyzerFactory {
    private static Map<String, IAnalyzer> analyzers =
            new HashMap<String, IAnalyzer>();

    public static IAnalyzer getAnalyzer(final String analyzerName) {
        if (!analyzers.containsKey(analyzerName)) {
            createAnalyzer(analyzerName);
        }
        return analyzers.get(analyzerName);
    }


    private static void createAnalyzer(final String name) {
        if (name.equals("LinearRegression")) {
            analyzers.put(name, new LinearRegressionAnalyzer());
            return;
        }
        throw new IllegalArgumentException("Incorrect name");
    }
}
