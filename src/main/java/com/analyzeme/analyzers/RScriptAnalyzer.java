package com.analyzeme.analyzers;

import com.analyzeme.scripts.Script;
import org.apache.commons.io.IOUtils;

public class RScriptAnalyzer extends AbstractDoubleRAnalyzer {
    private final Script script;

    public RScriptAnalyzer(Script script) {
        super(script.getNumberOfParams(), script.getTypeOfReturnValue());
        this.script = script;
    }

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    public String getScript() throws Exception {
        return IOUtils.toString(script.getScript());
    }
}
