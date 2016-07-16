package com.analyzeme.analyzers;

import com.analyzeme.r.facade.TypeOfReturnValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalMaximumAnalyzerR extends AbstractDoubleRAnalyzer {
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.GlobalMaximumAnalyzerR");
    }

    public GlobalMaximumAnalyzerR() {
        super(1, TypeOfReturnValue.DOUBLE);
    }

    public String getScript() {
        LOGGER.debug(
                "getScript(): method started");
        return "max(col_0)";
    }
}
