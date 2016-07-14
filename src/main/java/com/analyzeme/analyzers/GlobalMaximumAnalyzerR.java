package com.analyzeme.analyzers;

import com.analyzeme.r.facade.TypeOfReturnValue;

public class GlobalMaximumAnalyzerR extends AbstractDoubleRAnalyzer {

    public GlobalMaximumAnalyzerR() {
        super(1, TypeOfReturnValue.DOUBLE);
    }

    public String getScript() {
        return "max(col_0)";
    }
}
