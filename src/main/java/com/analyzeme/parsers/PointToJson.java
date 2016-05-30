package com.analyzeme.parsers;

import com.analyzeme.analyze.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by lagroffe on 26.03.2016 18:21
 */

public class PointToJson {

	/**
	 * @param point - point to convert to json
	 * @return json like { "x": "0.0","y": "1.0" }
	 */
	public static String convertPoint(Point point) {
		JSONObject result = new JSONObject();
		result.put("x", Double.toString(point.getX()));
		result.put("y", Double.toString(point.getY()));
		return result.toJSONString();
	}

	/**
	 * @param point - point to convert to json
	 * @return json like { "x": "0.0","y": "1.0" }
	 */
	public static JSONObject convertPointToJsonObject(Point point) {
		JSONObject result = new JSONObject();
		result.put("x", Double.toString(point.getX()));
		result.put("y", Double.toString(point.getY()));
		return result;
	}

	/**
	 * @param points - array of points to convert to json
	 * @return json like [{ "x": "0.0","y": "1.0" },{ "x": "0.0","y": "1.0" }]
	 */
	public static String convertPoints(Point[] points) {
		JSONArray result = new JSONArray();
		for (Point point : points) {
			result.add(convertPointToJsonObject(point));
		}
		return "{ \"Points\": " + result.toJSONString() + "}";
	}

	/**
	 * @param points - array of points to convert to json
	 * @return json like [{ "x": "0.0","y": "1.0" },{ "x": "0.0","y": "1.0" }]
	 */
	public static String convertPoints(List<Point> points) {
		JSONArray result = new JSONArray();
		for (Point point : points) {
			result.add(convertPointToJsonObject(point));
		}
		return "{ \"Points\": " + result.toJSONString() + "}";
	}
}
