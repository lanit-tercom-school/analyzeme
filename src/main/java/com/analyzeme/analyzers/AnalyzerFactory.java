package com.analyzeme.analyzers;

import com.analyzeme.scripts.BasicScriptLibrary;
import com.analyzeme.scripts.ILibrary;
import com.analyzeme.scripts.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class AnalyzerFactory {
    //info for js
    private static List<String> supportedAnalyzers;
    private static Map<String, IAnalyzer> analyzers =
            new HashMap<String, IAnalyzer>();
    private static ILibrary lib;
    private static List<String> scripts;
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.AnalyzerFactory");
        supportedAnalyzers = Arrays.asList(new String[]{
                "LinearRegression", "GlobalMaximum",
                "GlobalMaximumR", "GlobalMinimum",
                "LinearCorrelation",
                "KolmogorovSmirnovTest", "TestFileResult"});
        try {
            lib = new BasicScriptLibrary();
            scripts = lib.getAllScriptsNames();
        } catch (Exception e) {
            LOGGER.info(
                    "static block: impossible to use BasicScriptLibrary");
        }
    }

    public static List<String> getSupportedAnalyzers() {
        List<String> result = new ArrayList<String>();
        result.addAll(supportedAnalyzers);
        result.addAll(scripts);
        return result;
    }

    public static IAnalyzer getAnalyzer(final String analyzerName) throws Exception {
        LOGGER.debug("getAnalyzer(): method started");
        if (analyzers.containsKey(analyzerName)) {
            return analyzers.get(analyzerName);
        }
        if (supportedAnalyzers.contains(analyzerName)) {
            createAnalyzer(analyzerName);
            LOGGER.debug(
                    "getAnalyzer(): analyzer created");
            return analyzers.get(analyzerName);
        }
        if (scripts.contains(analyzerName)) {
            createRAnalyzer(analyzerName);
            LOGGER.debug(
                    "getAnalyzer(): r analyzer created");
            return analyzers.get(analyzerName);
        }
        LOGGER.info("getAnalyzer(): this analyzer is not supported",
                analyzerName);
        throw new IllegalArgumentException("not supported");
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
        LOGGER.warn("createAnalyzer(): this place should not be reached",
                name);
        throw new IllegalArgumentException(
                "Incorrect name");
    }


    public static void createRAnalyzer(final String name) throws Exception {
        LOGGER.debug("createRAnalyzer(): method started");
        Script script = lib.getScript(name);
        IAnalyzer analyzer = new RScriptAnalyzer(script);
        analyzers.put(name, analyzer);
        LOGGER.debug("createRAnalyzer(): method finished");
        return;
    }
}
