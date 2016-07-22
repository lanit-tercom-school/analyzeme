package com.analyzeme.analyzers.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use this type of result for scalar objects (for Lists or Maps use other types of result)
 */

public class ScalarResult<T> implements IResult<T> {
    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonWriter writer = new JsonWriter();
    private final T result;
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.result.ScalarResult");
    }

    public ScalarResult(final T result) {
        this.result = result;
    }

    public T getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        LOGGER.debug("toJson(): method started");
        if (result == null) {
            return null;
        }
        if (result instanceof Double) {
            return writer.toJson((Double) result);
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
