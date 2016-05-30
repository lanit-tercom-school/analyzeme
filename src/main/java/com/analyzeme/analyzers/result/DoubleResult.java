package com.analyzeme.analyzers.result;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Ольга on 14.04.2016.
 */
public class DoubleResult implements IResult {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Double result;

    public DoubleResult(final Double result) {
        this.result = result;
    }

    public Double getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        if(result == null){
            return null;
        }
        return mapper.writeValueAsString(result);
    }
}
