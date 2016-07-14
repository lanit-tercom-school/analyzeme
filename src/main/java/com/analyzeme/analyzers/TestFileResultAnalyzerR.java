package com.analyzeme.analyzers;

import com.analyzeme.r.facade.TypeOfReturnValue;

public class TestFileResultAnalyzerR extends AbstractDoubleRAnalyzer {
    public TestFileResultAnalyzerR() {
        super(3, TypeOfReturnValue.FILE);
    }

    public String getScript() {
        return "data.frame(col_0, col_1, col_2);";
    }
}
