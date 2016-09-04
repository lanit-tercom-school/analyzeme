package com.analyzeme.analyzers.result;

import com.analyzeme.data.dataset.DataEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use this type of result for scalar objects (for Lists or Maps use other types of result)
 */

public class ScalarResult implements IResult<DataEntry> {
    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonWriter writer = new JsonWriter();
    private final DataEntry result;
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.result.ScalarResult");
    }

    public ScalarResult(final DataEntry result) {
        this.result = result;
    }

    public DataEntry getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        LOGGER.debug("toJson(): method started");
        if (result == null) {
            return null;
        }
        try {
            return writer.toJson(result);
        } catch (Exception e) {
            LOGGER.info("toJson(): impossible to use custom writer");
        }
        return mapper.writeValueAsString(result);
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof ScalarResult) {
            ScalarResult that = (ScalarResult) other;
            result = this.getValue().equals(that.getValue());
        }
        return result;
    }
}
