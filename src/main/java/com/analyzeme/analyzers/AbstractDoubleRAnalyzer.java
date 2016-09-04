package com.analyzeme.analyzers;

import com.analyzeme.analyzers.r.DataConverter;
import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.analyzers.result.VectorsResult;
import com.analyzeme.data.dataset.DataArray;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.r.facade.GetFromRFactory;
import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.r.facade.get.IFromR;
import com.analyzeme.scripts.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public abstract class AbstractDoubleRAnalyzer implements IAnalyzer {
    protected final int NUMBER_OF_PARAMS;
    protected final TypeOfReturnValue TYPE_OF_RETURN_VALUE;
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.AbstractDoubleRAnalyzer");
    }

    public AbstractDoubleRAnalyzer(final int numberOfParams,
                                   final TypeOfReturnValue type) {
        this.NUMBER_OF_PARAMS = numberOfParams;
        this.TYPE_OF_RETURN_VALUE = type;
    }

    //here String is the name of the column, and List<T> is the data in it (only T=Double is supported now)
    public IResult analyze(
            final Map<String, List<DataEntry>> data) throws Exception {
        LOGGER.debug("analyze(): method started");
        if (data == null || data.isEmpty()) {
            LOGGER.info("analyze(): no data to analyze");
            throw new IllegalArgumentException(
                    "No data to analyze");
        }
        if (data.size() != NUMBER_OF_PARAMS) {
            LOGGER.info("analyze(): incorrect number of params");
            throw new IllegalArgumentException(
                    "Not correct data: should contain 1 column");
        }
        Map<String, String> keys = DataConverter.getKeysForR(data);
        LOGGER.debug("analyze(): keys for r integration are found");
        Map<String, List<DataEntry>> toR = DataConverter
                .translateForR(data, keys);
        LOGGER.debug("analyze(): data is ready for integration");
        IFromR<IResult> linker = GetFromRFactory.getLinkToR(
                TYPE_OF_RETURN_VALUE);
        if (linker == null) {
            LOGGER.info("analyze(): IFromR<IResult> is not found",
                    TYPE_OF_RETURN_VALUE);
            throw new IllegalArgumentException(
                    "This analyzer cannot be found");
        }
        LOGGER.debug("analyze(): IFromR<IResult> is found",
                TYPE_OF_RETURN_VALUE);
        IResult result = linker.runScript(getScript(), new DataArray(toR));
        if (result == null) {
            LOGGER.info("analyze(): null result");
        }
        if (TYPE_OF_RETURN_VALUE.equals(TypeOfReturnValue.VECTORS)) {
            LOGGER.debug("analyze(): VectorsResult is ready");
            return DataConverter.translateFromR(
                    (VectorsResult) result, keys);
        } else {
            LOGGER.debug("analyze(): result is ready");
            return result;
        }
    }

    public int getNumberOfParams() {
        return NUMBER_OF_PARAMS;
    }

    abstract Script getScript() throws Exception;
}
