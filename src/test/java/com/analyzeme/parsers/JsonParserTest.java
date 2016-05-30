package com.analyzeme.parsers;

import com.analyzeme.analyze.Point;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.assertTrue;


/**
 * Created by ������ ������� on 07.12.2015.
 */
public class JsonParserTest {
	private JsonParser jsonParser;
	private Point[] points;

	@Test(expected = NullPointerException.class)
	public void testNullArgumentInConstructor() throws Exception {
		jsonParser = new JsonParser();
		jsonParser.getPoints((InputStream) null);
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
		Assert.assertArrayEquals(new Point[]{new Point(1.0, 5.0), new Point(2.5, 6.5),
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

	@Test
	public void testParseWithNames() throws Exception {
		Set<String> names = new HashSet<String>();
		names.add("x");
		names.add("y");
		names.add("z");
		String s = join("\n", new String[]{
				"{\"Data\":[{ \"x\": \"1\",\"y\": \"1\",\"z\": \"1\" },{\"x\": \"20\",\"y\": \"20\",\"z\": \"1\"}]}"
		});
		List<Double> x = new ArrayList<Double>();
		x.add(1.0); x.add(20.0);

		List<Double> y = new ArrayList<Double>();
		y.add(1.0); y.add(20.0);


		List<Double> z = new ArrayList<Double>();
		z.add(1.0); z.add(1.0);

		InputStream is = new ByteArrayInputStream(s.getBytes());
		jsonParser = new JsonParser();
		Map<String, List<Double>> res = jsonParser.getPointsFromJsonWithNames(is, names);

		assertTrue(res.get("x").equals(x));
		assertTrue(res.get("y").equals(y));
		assertTrue(res.get("z").equals(z));

	}
}
