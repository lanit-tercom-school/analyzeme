package com.analyzeme.analyzers.result;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Ольга on 14.04.2016.
 */
public class DoubleResult implements IResult {

    private ObjectMapper mapper = new ObjectMapper();
    private Double result;

    public DoubleResult(Double result) {
        this.result = result;
    }

    public Double getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        return mapper.writeValueAsString(result);
    }
}
