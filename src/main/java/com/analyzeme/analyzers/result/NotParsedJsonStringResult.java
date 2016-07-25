package com.analyzeme.analyzers.result;

import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataEntryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use this type of result when you have got a result of an unknown type (from user R script, for example)
 */

public class NotParsedJsonStringResult implements IResult<String> {
    private final String result;
    private static final Logger LOGGER;
    private final JsonWriter writer = new JsonWriter();

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.result.NotParsedJsonStringResult");
    }

    public NotParsedJsonStringResult(final String result) {
        this.result = result;
    }

    public String getValue() {
        return result;
    }

    public String toJson() {
        LOGGER.debug("toJson(): method started");
        if (result == null || result.isEmpty()) {
            return null;
        }
        try {
            return writer.toJson(new DataEntry(DataEntryType.STRING, result));
        } catch (Exception e) {
            LOGGER.info("toJson(): impossible to use custom writer");
            return result;
        }
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof NotParsedJsonStringResult) {
            NotParsedJsonStringResult that = (NotParsedJsonStringResult) other;
            result = this.getValue().equals(that.getValue());
        }
        return result;
    }
}
