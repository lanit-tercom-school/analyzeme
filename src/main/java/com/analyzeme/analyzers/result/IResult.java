package com.analyzeme.analyzers.result;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IResult<T> {

    T getValue();

    String toJson() throws JsonProcessingException;
}
