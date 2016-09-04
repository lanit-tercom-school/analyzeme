package com.analyzeme.analyzers;

import com.analyzeme.scripts.Script;

public class RScriptAnalyzer extends AbstractDoubleRAnalyzer {
    private final Script script;

    public RScriptAnalyzer(Script script) {
        super(script.getNumberOfParams(), script.getTypeOfReturnValue());
        this.script = script;
    }

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    public Script getScript() throws Exception {
        return script;
    }
}
