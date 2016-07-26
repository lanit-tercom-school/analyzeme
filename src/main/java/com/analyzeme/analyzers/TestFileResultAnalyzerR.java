package com.analyzeme.analyzers;

import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.scripts.InputType;
import com.analyzeme.scripts.Script;
import com.analyzeme.scripts.ScriptSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFileResultAnalyzerR extends AbstractDoubleRAnalyzer {
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.TestFileResultAnalyzerR");
    }

    public TestFileResultAnalyzerR() {
        super(3, TypeOfReturnValue.VECTORS);
    }

    public Script getScript() {
        LOGGER.debug("getScript(): method started");
        return new Script("test_file", null, 3,
                TypeOfReturnValue.VECTORS,
                ScriptSource.LIBRARY,
                "data.frame(col_0, col_1, col_2);", InputType.VECTORS);
    }
}
