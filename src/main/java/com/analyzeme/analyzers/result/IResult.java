package com.analyzeme.analyzers.result;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by Ольга on 14.04.2016.
 */
public interface IResult<T> {

    T getValue();

    String toJson() throws JsonProcessingException;
}
