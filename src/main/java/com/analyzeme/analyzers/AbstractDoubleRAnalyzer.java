package com.analyzeme.analyzers;

import com.analyzeme.analyzers.r.DataConverter;
import com.analyzeme.analyzers.result.*;
import com.analyzeme.r.facade.GetFromR;
import com.analyzeme.r.facade.GetFromRFactory;
import com.analyzeme.r.facade.TypeOfReturnValue;

import java.util.List;
import java.util.Map;

public abstract class AbstractDoubleRAnalyzer implements IAnalyzer<Double> {
    protected final int NUMBER_OF_PARAMS;
    protected final TypeOfReturnValue TYPE_OF_RETURN_VALUE;

    public AbstractDoubleRAnalyzer(final int numberOfParams, final TypeOfReturnValue type) {
        this.NUMBER_OF_PARAMS = numberOfParams;
        this.TYPE_OF_RETURN_VALUE = type;
    }

    //here String is the name of the column, and List<T> is the data in it (only T=Double is supported now)
    public IResult analyze(final Map<String, List<Double>> data) throws Exception {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("No data to analyze");
        }
        if (data.size() != NUMBER_OF_PARAMS) {
            throw new IllegalArgumentException("Not correct data: should contain 1 column");
        }
        Map<String, String> keys = DataConverter.getKeysForR(data);
        Map<String, List<Double>> toR = DataConverter.translateForR(data, keys);
        GetFromR<IResult> linker = GetFromRFactory.getLinkToR(TYPE_OF_RETURN_VALUE);
        IResult result = linker.runCommand(getScript(), toR);
        if (TYPE_OF_RETURN_VALUE.equals(TypeOfReturnValue.FILE)) {
            return DataConverter.translateFromR((FileResult) result, keys);
        } else {
            return result;
        }
    }

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    abstract String getScript();
}
