package com.analyzeme.analyzers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Ольга on 14.04.2016.
 */
public class PointResult implements IResult {

    private ObjectMapper mapper = new ObjectMapper();
    private Point result;

    public PointResult(Point result) {
        this.result = result;
    }

    public Object getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        return mapper.writeValueAsString(result);
    }
}
