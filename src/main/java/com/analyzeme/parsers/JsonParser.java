package com.analyzeme.parsers;

import com.analyzeme.analyze.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Andrey Kalikin on 07.12.2015.
 */

public class JsonParser {
	private final InputStream inputStream;
	private final String xName = "x";
	private final String yName = "y";

	public JsonParser(InputStream inputStream) {
		if (inputStream == null) {
			throw new NullPointerException();
		}

		this.inputStream = inputStream;
	}

	public Point[] getPoints() throws JsonParserException {
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
}
