package com.analyzeme.analyzers.result;

import com.analyzeme.analyzers.Point;
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

	public Point getValue() {
		return result;
	}

	public String toJson() throws JsonProcessingException {
		return mapper.writeValueAsString(result);
	}
}
