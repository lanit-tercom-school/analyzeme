package com.analyzeme.analyzers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by Ольга on 14.04.2016.
 */
public class ListResult implements IResult {

    private ObjectMapper mapper = new ObjectMapper();
    private List<Point> result;

    public ListResult(List<Point> result) {
        this.result = result;
    }

    public Object getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        return mapper.writeValueAsString(result);
    }


}
