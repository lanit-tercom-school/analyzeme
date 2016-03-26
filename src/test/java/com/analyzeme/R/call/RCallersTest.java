package com.analyzeme.R.call;

import com.analyzeme.analyze.Point;
import com.analyzeme.parsers.JsonParser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static junit.framework.Assert.assertTrue;

/**
 * Created by lagroffe on 26.03.2016 18:53
 */

//for Renjin and FakeR

public class RCallersTest {
	static double eps = 0.00001;
	static IRCaller call;
	static String testData = "{\"Data\":[{ \"x\": \"0\",\"y\": \"0\" },{ \"x\": \"1\",\"y\": \"1\" },{\"x\": \"2\",\"y\": \"2\"},{ \"x\": \"3\",\"y\": \"3\" },{ \"x\": \"4\",\"y\": \"4\" },{ \"x\": \"5\",\"y\": \"5\" },{ \"x\": \"6\",\"y\": \"6\" },{ \"x\": \"7\",\"y\": \"7\" },{ \"x\": \"8\",\"y\": \"8\" },{ \"x\": \"9\",\"y\": \"9\" },{ \"x\": \"10\",\"y\": \"10\" }]}";
	static Point[] points;

	public static boolean doubleEqual(double a, double b) {
		return Math.abs(a - b) < eps;
	}

	@BeforeClass
	public static void beforeClass() throws Exception {
		InputStream is = new ByteArrayInputStream(testData.getBytes());
		JsonParser jsonParser;
		jsonParser = new JsonParser(is);
		points = jsonParser.getPointsFromPointJson();
	}

	@Test
	public void testRenjinCommandToGetNumber() {
		try {
			call = new Renjin();
			double resX, resY;
			for (int i = 0; i < points.length; i++) {
				resX = call.runCommandToGetNumber("x[" + (int) (i + 1) + "]", testData);
				resY = call.runCommandToGetNumber("y[" + (int) (i + 1) + "]", testData);
				assertTrue("Double doesn't return correctly from Renjin", doubleEqual(resX, points[i].GetX()) && doubleEqual(resY, points[i].GetY()));
			}
		} catch (Exception e) {
			assertTrue("Double doesn't return correctly from Renjin", false);
		}
	}

	@Test
	public void testFakeRCommandToGetNumber() {
		try {
			call = new FakeR();
			double resX = call.runCommandToGetNumber("---", "---");
			double resY = call.runCommandToGetNumber("---", "---");
			assertTrue("Double doesn't return correctly from FakeR", doubleEqual(resX, 0) && doubleEqual(resY, 0));
		} catch (Exception e) {
			assertTrue("Double doesn't return correctly from FakeR", false);
		}
	}

	@Test
	public void testRenjinCommandToGetPoint() {
		try {
			call = new Renjin();
			Point res = null;
			for (int i = 0; i < points.length; i++) {
				res = call.runCommandToGetPoint("c(x[" + (int) (i + 1) + "], y[" + (int) (i + 1) + "])", testData);
				assertTrue("Points doesn't return correctly from Renjin", doubleEqual(points[i].GetX(), res.GetX()) && doubleEqual(points[i].GetY(), res.GetY()));
			}
		} catch (Exception e) {
			assertTrue("Points doesn't return correctly from Renjin", false);
		}
	}

	@Test
	public void testFakeRCommandToGetPoint() {
		try {
			call = new FakeR();
			Point res = call.runCommandToGetPoint("---", "---");
			assertTrue("FakeR to get point doesn't work properly", doubleEqual(res.GetX(), 0) && doubleEqual(res.GetY(), 0));
		} catch (Exception e) {
			assertTrue("FakeR to get point doesn't work properly", false);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFakeRIllegalArgument() throws Exception {
		call = new FakeR();
		call.runCommandToGetPoint("", (String) null);
		call.runCommandToGetPoint(null, "");

		call.runCommandToGetNumber("", (String) null);
		call.runCommandToGetNumber(null, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRenjinIllegalArgument() throws Exception {
		call = new Renjin();
		call.runCommandToGetPoint("", (String) null);
		call.runCommandToGetPoint(null, "");

		call.runCommandToGetNumber("", (String) null);
		call.runCommandToGetNumber(null, "");

	}
}
