package com.analyzeme.analyzers;

import com.analyzeme.r.facade.TypeOfReturnValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFileResultAnalyzerR extends AbstractDoubleRAnalyzer {
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.TestFileResultAnalyzerR");
    }

    public TestFileResultAnalyzerR() {
        super(3, TypeOfReturnValue.FILE);
    }

    public String getScript() {
        LOGGER.debug("getScript(): method started");
        return "data.frame(col_0, col_1, col_2);";
    }
}
