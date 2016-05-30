package com.analyzeme.parsers.jsonParser;

import com.analyzeme.analyze.Point;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.parsers.JsonParserException;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


/**
 * Created by ������ ������� on 07.12.2015.
 */
public class TestGetPoints {
	private JsonParser jsonParser;
	private Point[] points;

	@Test(expected = IllegalArgumentException.class)
	public void testNullArgumentInConstructor() throws Exception {
		jsonParser = new JsonParser();
		jsonParser.getPoints((InputStream)null);
	}

	@Test
	public void testIncorrectFile() throws JsonParserException {
		String s = join("\n", new String[]{
				"{",
				"\"x\":"
		});

		InputStream is = new ByteArrayInputStream(s.getBytes());
		jsonParser = new JsonParser();
		try {
			points = jsonParser.getPoints(is);
			Assert.fail();
		} catch (JsonParserException ex) {
			Assert.assertEquals(JsonParserException.ExceptionType.PARSE_FILE,
					ex.getExType());
		}
	}

	@Test
	public void testDifferentArraysLength() throws JsonParserException {
		String s = join("\n", new String[]{
				"{",
				"\"x\": [",
				"\"4.7\"",
				"],",
				"\"y\": [",
				"\"5\",",
				"\"7.7\"",
				"]",
				"}",
		});

		InputStream is = new ByteArrayInputStream(s.getBytes());
		jsonParser = new JsonParser();
		try {
			points = jsonParser.getPoints(is);
			Assert.fail();
		} catch (JsonParserException ex) {
			Assert.assertEquals(JsonParserException.ExceptionType.DIFFERENT_LENGTH,
					ex.getExType());
		}
	}

	@Test
	public void testIncorrectArrayElement() throws JsonParserException {
		String s = join("\n", new String[]{
				"{",
				"\"x\": [",
				"\"4.7\"",
				"],",
				"\"y\": [",
				"\"b",
				"]",
				"}",
		});

		InputStream is = new ByteArrayInputStream(s.getBytes());
		jsonParser = new JsonParser();
		try {
			points = jsonParser.getPoints(is);
			Assert.fail();
		} catch (JsonParserException ex) {
			Assert.assertEquals(JsonParserException.ExceptionType.PARSE_FILE,
					ex.getExType());
		}
	}

	@Test(expected = NullPointerException.class)
	public void testAnotherArrayName() throws JsonParserException {
		String s = join("\n", new String[]{
				"{",
				"\"x\": [",
				"\"4.7\"",
				"],",
				"\"z\": [",
				"\"7.7\"",
				"]",
				"}"
		});

		InputStream is = new ByteArrayInputStream(s.getBytes());
		jsonParser = new JsonParser();
		points = jsonParser.getPoints(is);
	}

	@Test
	public void testPointsDoubleWithInteger() throws JsonParserException {
		String s = join("\n", new String[]{
				"{",
				"\"x\": [",
				"\"1\",",
				"\"2.5\",",
				"\"4.7\"",
				"],",
				"\"y\": [",
				"\"5\",",
				"\"6.5\",",
				"\"7.7\"",
				"]",
				"}"
		});

		InputStream is = new ByteArrayInputStream(s.getBytes());
		jsonParser = new JsonParser();
		points = jsonParser.getPoints(is);
		Assert.assertArrayEquals(new Point[]{new Point(1.0, 5.0),
				new Point(2.5, 6.5),
				new Point(4.7, 7.7)}, points);
	}

	static public String join(String delimiter, String[] list) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String item : list) {
			if (first)
				first = false;
			else
				sb.append(delimiter);
			sb.append(item);
		}
		return sb.toString();
	}
}
