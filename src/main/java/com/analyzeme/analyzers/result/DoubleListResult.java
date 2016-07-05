package com.analyzeme.analyzers.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by lagroffe on 05.07.2016 14:58
 */
public class DoubleListResult implements IResult<List<Double>> {
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<Double> result;

    public DoubleListResult(final List<Double> result) {
        this.result = result;
    }

    public List<Double> getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        if (result == null) {
            return null;
        }
        return mapper.writeValueAsString(result);
    }
}
