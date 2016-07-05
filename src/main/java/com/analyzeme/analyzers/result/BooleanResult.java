package com.analyzeme.analyzers.result;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BooleanResult implements IResult {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Boolean result;

    public BooleanResult(final Boolean result) {
        this.result = result;
    }

    public Boolean getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        if(result == null){
            return null;
        }
        return mapper.writeValueAsString(result);
    }
}
