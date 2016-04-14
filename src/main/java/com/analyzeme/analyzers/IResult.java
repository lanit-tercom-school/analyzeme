package com.analyzeme.analyzers;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by Ольга on 14.04.2016.
 */
public interface IResult {

    Object getValue();

    String toJson() throws JsonProcessingException;
}
