package com.analyzeme.analyzers.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Use this type of result for anonymous vectors of some kind of objects
 */

public class ColumnResult<T> implements IResult<List<T>> {
    private static final Logger LOGGER;
    private final JsonWriter writer = new JsonWriter();

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.result.ColumnResult");
    }

    private final ObjectMapper mapper = new ObjectMapper();
    private final List<T> result;

    public ColumnResult(final List<T> result) {
        this.result = result;
    }

    public List<T> getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        LOGGER.debug("toJson(): method started");
        if (result == null || result.isEmpty()) {
            return null;
        }
        if (result.get(0) instanceof Double) {
            return writer.toJson(result);
        }
        return mapper.writeValueAsString(result);
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof ColumnResult) {
            ColumnResult that = (ColumnResult) other;
            if (that.getValue().size()
                    != this.getValue().size()) {
                return result;
            }
            for (int i = 0; i < that.getValue().size(); i++) {
                if (!that.getValue().get(i).equals(
                        this.getValue().get(i))) {
                    return result;
                }
            }
            result = true;
        }
        return result;
    }
}
