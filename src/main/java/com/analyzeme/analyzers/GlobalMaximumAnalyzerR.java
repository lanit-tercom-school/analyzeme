package com.analyzeme.analyzers;

import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.scripts.Script;
import com.analyzeme.scripts.ScriptSource;
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

    public Script getScript() {
        LOGGER.debug(
                "getScript(): method started");
        return new Script("maximum", null, 1,
                TypeOfReturnValue.DOUBLE,
                ScriptSource.LIBRARY, "max(col_0)");
    }
}
