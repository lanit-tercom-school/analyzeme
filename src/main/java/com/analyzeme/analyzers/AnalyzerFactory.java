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
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.AnalyzerFactory");
        supportedAnalyzers = Arrays.asList(new String[]{
                "Linear Regression", "Global Maximum",
                "Global Maximum R", "Global Minimum",
                "Linear Correlation",
                "Kolmogorov Smirnov Test", "Test File Result",
                "Rosenstein Analyzer"});
        try {
            lib = new BasicScriptLibrary();
        } catch (Exception e) {
            LOGGER.info(
                    "static block: impossible to use BasicScriptLibrary");
        }
    }

    public static List<String> getSupportedAnalyzers() {
        List<String> result = new ArrayList<String>();
        if (supportedAnalyzers != null) {
            result.addAll(supportedAnalyzers);
        }
        try {
            List<String> scripts = lib.getAllScriptsNames();
            if (scripts != null) {
                result.addAll(scripts);
            }
        } catch (Exception e) {
            LOGGER.info(
                    "getSupportedAnalyzers(): problem in BasicScriptLibrary",
                    e.toString());
        }
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
        List<String> scripts = null;
        try {
            scripts = lib.getAllScriptsNames();
        } catch (Exception e) {
            LOGGER.info(
                    "getAnalyzer(): impossible to check R analyzers",
                    e.toString());
        }
        if (scripts != null && scripts.contains(analyzerName)) {
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
        if (name.equals("Linear Regression")) {
            analyzers.put(name,
                    new LinearRegressionAnalyzer());
            return;
        }
        if (name.equals("Global Maximum")) {
            analyzers.put(name,
                    new GlobalMaximumAnalyzer());
            return;
        }
        if (name.equals("Global Maximum R")) {
            analyzers.put(name,
                    new GlobalMaximumAnalyzerR());
            return;
        }
        if (name.equals("Global Minimum")) {
            analyzers.put(name,
                    new GlobalMinimumAnalyzer());
            return;
        }
        if (name.equals("Linear Correlation")) {
            analyzers.put(name,
                    new LinearCorrelationAnalyzer());
            return;
        }
        if (name.equals("Kolmogorov Smirnov Test")) {
            analyzers.put(name,
                    new KolmogorovSmirnovTestAnalyzer());
            return;
        }
        if (name.equals("Test File Result")) {
            analyzers.put(name,
                    new TestFileResultAnalyzerR());
            return;
        }
        if (name.equals("Rosenstein Analyzer")) {
            analyzers.put(name,
                    new RosensteinAnalyzer());
            return;
        }
        LOGGER.warn("createAnalyzer(): this place should not be reached",
                name);
        throw new IllegalArgumentException(
                "Incorrect name");
    }


    private static void createRAnalyzer(final String name) throws Exception {
        LOGGER.debug("createRAnalyzer(): method started");
        Script script = lib.getScript(name);
        IAnalyzer analyzer = new RScriptAnalyzer(script);
        analyzers.put(name, analyzer);
        LOGGER.debug("createRAnalyzer(): method finished");
    }
}
