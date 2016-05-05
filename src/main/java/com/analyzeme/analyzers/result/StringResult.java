package com.analyzeme.analyzers.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Ольга on 28.04.2016.
 */
public class StringResult implements IResult {

    private ObjectMapper mapper = new ObjectMapper();
    private String result;

    public StringResult(String result) {
        this.result = result;
    }

    public String getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        if(result == null){
            return null;
        }
        return mapper.writeValueAsString(result);
    }
}
