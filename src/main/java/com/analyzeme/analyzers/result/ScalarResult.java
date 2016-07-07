package com.analyzeme.analyzers.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Use this type of result for scalar objects (for Lists or Maps use other types of result)
 */

public class ScalarResult<T> implements IResult<T> {
    private final ObjectMapper mapper = new ObjectMapper();
    private final T result;

    public ScalarResult(final T result) {
        this.result = result;
    }

    public T getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        if (result == null) {
            return null;
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
