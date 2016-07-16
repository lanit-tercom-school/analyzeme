package com.analyzeme.analyzers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AnalyzerFactory {
    private static Map<String, IAnalyzer> analyzers =
            new HashMap<String, IAnalyzer>();
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.AnalyzerFactory");
    }

    public static IAnalyzer getAnalyzer(final String analyzerName) {
        LOGGER.debug("getAnalyzer(): method started");
        if (!analyzers.containsKey(analyzerName)) {
            createAnalyzer(analyzerName);
            LOGGER.debug(
                    "getAnalyzer(): analyzer created");
        }
        return analyzers.get(analyzerName);
    }


    private static void createAnalyzer(final String name) {
        if (name.equals("LinearRegression")) {
            analyzers.put(name,
                    new LinearRegressionAnalyzer());
            return;
        }
        if (name.equals("GlobalMaximum")) {
            analyzers.put(name,
                    new GlobalMaximumAnalyzer());
            return;
        }
        if (name.equals("GlobalMaximumR")) {
            analyzers.put(name,
                    new GlobalMaximumAnalyzerR());
            return;
        }
        if (name.equals("GlobalMinimum")) {
            analyzers.put(name,
                    new GlobalMinimumAnalyzer());
            return;
        }
        if (name.equals("LinearCorrelation")) {
            analyzers.put(name,
                    new LinearCorrelationAnalyzer());
            return;
        }
        if (name.equals("KolmogorovSmirnovTest")) {
            analyzers.put(name,
                    new KolmogorovSmirnovTestAnalyzer());
            return;
        }
        if (name.equals("TestFileResult")) {
            analyzers.put(name,
                    new TestFileResultAnalyzerR());
            return;
        }
        throw new IllegalArgumentException(
                "Incorrect name");
    }
}
