package com.analyzeme.analyzers;

import com.analyzeme.analyzers.r.DataConverter;
import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataArray;
import com.analyzeme.r.facade.GetFromR;
import com.analyzeme.r.facade.GetFromRFactory;
import com.analyzeme.r.facade.TypeOfReturnValue;

import java.util.*;

/**
 * Created by lagroffe on 13.07.2016 14:30
 */
public class GlobalMaximumAnalyzerR implements IAnalyzer<Double> {
    private static final int NUMBER_OF_PARAMS = 1;

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    public IResult analyze(Map<String, List<Double>> data) throws Exception {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("No data to analyze");
        }
        if (data.size() != NUMBER_OF_PARAMS) {
            throw new IllegalArgumentException("Not correct data: should contain 1 column");
        }
        Map<String, String> keys = DataConverter.getKeysForR(data);
        Map<String, List<Double>> toR = DataConverter.translateForR(data, keys);
        GetFromR<ScalarResult<Double>> linker = GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
        return linker.runCommand(getScript(), toR);
    }

    private String getScript() {
        return "max(col_0)";
    }
}
