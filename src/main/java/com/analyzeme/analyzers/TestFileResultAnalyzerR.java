package com.analyzeme.analyzers;

import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.scripts.InputType;
import com.analyzeme.scripts.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFileResultAnalyzerR extends AbstractDoubleRAnalyzer {
    private static final Logger LOGGER;
    private static final int NUMBER_OF_PARAMS = 3;
    private static String id;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.TestFileResultAnalyzerR");
    }

    public TestFileResultAnalyzerR() {
        super(NUMBER_OF_PARAMS, TypeOfReturnValue.VECTORS);
    }

    public Script getScript() throws Exception {
        LOGGER.debug("getScript(): method started");
        if (id == null) {
            Script res = Script.builder()
                    .fromParts()
                    .name("test_file")
                    .inputType(InputType.VECTORS)
                    .numberOfParams(NUMBER_OF_PARAMS)
                    .returnValue(TypeOfReturnValue.VECTORS)
                    .uploadText("data.frame(col_0, col_1, col_2);")
                    .build();
            this.id = res.getId();
            return res;
        } else {
            return Script.builder()
                    .fromRepo()
                    .name("test_file")
                    .inputType(InputType.VECTORS)
                    .numberOfParams(NUMBER_OF_PARAMS)
                    .returnValue(TypeOfReturnValue.VECTORS)
                    .setId(id)
                    .build();
        }
    }
}
