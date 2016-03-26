package com.analyzeme.parsers;

import com.analyzeme.analyze.Point;

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
		return ("{ \"x\": \"" + point.GetX() + "\",\"y\": \"" + point.GetY() + "\" }");
	}

	/**
	 * @param points - array of points to convert to json
	 * @return json like [{ "x": "0.0","y": "1.0" },{ "x": "0.0","y": "1.0" }]
	 */
	public static String convertPoints(Point[] points) {
		StringBuilder result = new StringBuilder();
		result.append("[");
		for (int i = 0; i < points.length; i++) {
			result.append("," + convertPoint(points[i]));
		}
		result.append("]");
		return result.toString();
	}

	/**
	 * @param points - array of points to convert to json
	 * @return json like [{ "x": "0.0","y": "1.0" },{ "x": "0.0","y": "1.0" }]
	 */
	public static String convertPoints(List<Point> points) {
		StringBuilder result = new StringBuilder();
		result.append("[");
		for (Point p : points) {
			result.append("," + convertPoint(p));
		}
		result.append("]");
		return result.toString();
	}
}
