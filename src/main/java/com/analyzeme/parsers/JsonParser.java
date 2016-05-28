package com.analyzeme.parsers;

import com.analyzeme.analyze.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Andrey Kalikin on 07.12.2015.
 */

public class JsonParser {
	private final String xName = "x";
	private final String yName = "y";
	private final String dataName = "Data";

	public JsonParser() {
	}

	/**
	 * Method for parsing string type {"x": ["1", "2.5", "4.7"],"y": ["5", "6.5", "7.7"]}
	 */
	public Point[] getPoints(InputStream inputStream) throws JsonParserException {
		if (inputStream == null) {
			throw new NullPointerException();
		}

		JSONParser parser = new JSONParser();

		try {
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			JSONObject jsonObject = (JSONObject) parser.parse(inputStreamReader);
			JSONArray xPoints = (JSONArray) jsonObject.get(xName);
			JSONArray yPoints = (JSONArray) jsonObject.get(yName);

			if (xPoints.size() != yPoints.size()) {
				throw new JsonParserException(JsonParserException.ExceptionType.DIFFERENT_LENGTH);
			}

			Point[] points = new Point[xPoints.size()];

			for (int i = 0; i < xPoints.size(); i++) {
				Double x = (Double.parseDouble((String) xPoints.get(i)));
				Double y = (Double.parseDouble((String) yPoints.get(i)));
				points[i] = new Point(x, y);
			}

			return points;
		} catch (ParseException e) {
			throw new JsonParserException(JsonParserException.ExceptionType.PARSE_FILE);
		} catch (IOException e) {
			throw new JsonParserException(e.getStackTrace().toString());
		}
	}

	/**
	 * Method for parsing string type {Data:[{"x": "1","y": "15"},{"x": "20","y": "60" }]}
	 */
	public Point[] getPointsFromPointJson(InputStream inputStream) throws JsonParserException {
		if (inputStream == null) {
			throw new NullPointerException();
		}

		JSONParser parser = new JSONParser();

		try {
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

			Object obj = parser.parse(inputStreamReader);
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray jsonArray = (JSONArray) jsonObject.get(dataName);
			Point[] points = new Point[jsonArray.size()];

			Iterator<JSONObject> iterator = jsonArray.iterator();
			int i = 0;
			while (iterator.hasNext()) {

				JSONObject jsonPoint = iterator.next();

				double x = Double.parseDouble((String) jsonPoint.get(xName));
				double y = Double.parseDouble((String) jsonPoint.get(yName));
				points[i] = new Point(x, y);
				i++;
			}

			return points;
		} catch (ParseException e) {
			throw new JsonParserException(JsonParserException.ExceptionType.PARSE_FILE);
		} catch (IOException e) {
			throw new JsonParserException(e.getStackTrace().toString());
		}
	}

	/**
	 * Method for parsing string type {Data:[{"x": "1","y": "15"},{"x": "20","y": "60" }]}
	 */
	public Map<String, List<Double>> getPointsFromJsonWithNames(InputStream inputStream, Set<String> names) throws JsonParserException {
		if (inputStream == null) {
			throw new NullPointerException();
		}

		JSONParser parser = new JSONParser();

		try {
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			Object obj = parser.parse(inputStreamReader);
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray jsonArray = (JSONArray) jsonObject.get(dataName);
			Map<String, List<Double>> result = new HashMap<String, List<Double>>();
			for(String name : names) {
				 result.put(name, new ArrayList<Double>());
			}
			Iterator<JSONObject> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				JSONObject jsonPoint = iterator.next();
				for(Map.Entry<String, List<Double>> entry : result.entrySet()) {
					entry.getValue().add(Double.parseDouble((String) jsonPoint.get(entry.getKey())));
				}
			}
			return result;
		} catch (ParseException e) {
			throw new JsonParserException(JsonParserException.ExceptionType.PARSE_FILE);
		} catch (IOException e) {
			throw new JsonParserException(e.getStackTrace().toString());
		}
	}
}
