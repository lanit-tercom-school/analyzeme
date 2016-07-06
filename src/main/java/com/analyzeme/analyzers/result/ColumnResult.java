package com.analyzeme.analyzers.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Use this type of result for anonymous vectors of some kind of objects
 */

public class ColumnResult<T> implements IResult<List<T>> {

    private final ObjectMapper mapper = new ObjectMapper();
    private final List<T> result;

    public ColumnResult(final List<T> result) {
        this.result = result;
    }

    public List<T> getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        if(result == null){
            return null;
        }
        return mapper.writeValueAsString(result);
    }


}
