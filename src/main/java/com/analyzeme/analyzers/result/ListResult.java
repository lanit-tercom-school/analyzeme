package com.analyzeme.analyzers.result;

import com.analyzeme.analyzers.Point;
import com.analyzeme.analyzers.result.IResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by Ольга on 14.04.2016.
 */
public class ListResult implements IResult {

    private ObjectMapper mapper = new ObjectMapper();
    private List<Point> result;

    public ListResult(final List<Point> result) {
        this.result = result;
    }

    public List<Point> getValue() {
        return result;
    }

    public String toJson() throws JsonProcessingException {
        if(result == null){
            return null;
        }
        return mapper.writeValueAsString(result);
    }


}
