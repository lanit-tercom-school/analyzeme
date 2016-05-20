package com.analyzeme.R.call;

import com.analyzeme.analyze.Point;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.JsonPointFileInRepositoryInfo;
import com.analyzeme.data.ISourceInfo;
import com.analyzeme.repository.filerepository.FileRepository;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by lagroffe on 26.03.2016 18:53
 */

public class FakeRTest {
	private static double eps = 0.00001;
	private static IRCaller call;

	private static String testData = "{\"Data\":[{ \"x\": \"0\",\"y\": \"0\" },{ \"x\": \"1\",\"y\": \"1\" },{\"x\": \"2\",\"y\": \"2\"},{ \"x\": \"3\",\"y\": \"3\" },{ \"x\": \"4\",\"y\": \"4\" },{ \"x\": \"5\",\"y\": \"5\" },{ \"x\": \"6\",\"y\": \"6\" },{ \"x\": \"7\",\"y\": \"7\" },{ \"x\": \"8\",\"y\": \"8\" },{ \"x\": \"9\",\"y\": \"9\" },{ \"x\": \"10\",\"y\": \"10\" }]}";
	private static String testScriptForPoints = "matrix(c(x[1], y[1], x[1], y[1]), nrow = 2, ncol = 2, byrow=TRUE)";


	public static boolean doubleEqual(double a, double b) {
		return Math.abs(a - b) < eps;
	}

	private static ByteArrayInputStream convertStringToStream(String data) {
		byte[] b = new byte[data.length()];
		for (int i = 0; i < data.length(); i++) {
			b[i] = (byte) data.charAt(i);
		}
		return new ByteArrayInputStream(b);
	}

	private static ByteArrayInputStream correctFile;
	private static String correctFilename = "file.json";
	private static String correctFileId;
	private static String correctX;
	private static String correctY;
	private static ArrayList<DataSet> correct;

	private static String correctScriptForCorrectFileName;
	private static String correctScriptForCorrectFileString;
	private static ByteArrayInputStream correctScriptForCorrectFile;

	@BeforeClass
	public static void beforeClass() throws Exception {
		call = new FakeR();

		correctFile = convertStringToStream(testData);
		correctFileId = FileRepository.getRepo().persist(correctFile, correctFilename);
		if (correctFileId == null) throw new IllegalArgumentException("Repository doesn't work");
		correctX = "x_from__repo__" + correctFileId + "__";
		correctY = "y_from__repo__" + correctFileId + "__";
		correct = new ArrayList<DataSet>();
		ISourceInfo correctInfo = new JsonPointFileInRepositoryInfo(correctFileId);
		DataSet setCorrect = new DataSet(correctFilename, correctInfo);
		setCorrect.addField("x");
		setCorrect.addField("y");
		correct.add(setCorrect);

		correctScriptForCorrectFileName = "script.R";
		correctScriptForCorrectFileString = "matrix(c(" + correctX + "[1], " + correctY + "[1], " + correctX + "[1], " + correctY + "[1]), nrow = 2, ncol = 2, byrow=TRUE)";
		correctScriptForCorrectFile = convertStringToStream(correctScriptForCorrectFileString);
	}

	@AfterClass
	public static void after() throws Exception {
		FileRepository.getRepo().deleteFileById(correctFileId);
	}

	//-------------------------------
	//Tests for illegal arguments
	//-------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument1() throws Exception {
		call.runCommandToGetPoints("", (String) null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument2() throws Exception {
		call.runCommandToGetPoint((String) null, "");

	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument3() throws Exception {
		call.runCommandToGetNumber("", (String) null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument4() throws Exception {
		call.runScriptToGetPoint((String) null, (ByteArrayInputStream) null, (ArrayList<DataSet>) null);
	}


	//-----------------------------------------------------------
	//-----------------------------------------------------------
	//test commands for json data
	//-----------------------------------------------------------
	//-----------------------------------------------------------

	@Test
	public void testCorrectCommandToGetNumber() {
		try {
			double resX = call.runCommandToGetNumber("x[1]", testData);
			double resY = call.runCommandToGetNumber("y[5]", testData);
			assertTrue("Double doesn't return correctly from FakeR", doubleEqual(resX, 0) && doubleEqual(resY, 0));
		} catch (Exception e) {
			assertTrue("Double doesn't return correctly from FakeR", false);
		}
	}

	@Test
	public void testCorrectCommandToGetPoint() {
		try {
			Point res = null;
			res = call.runCommandToGetPoint("c(x[5], y[5])", testData);
			assertTrue("Points doesn't return correctly from FakeR", doubleEqual(0, res.GetX()) && doubleEqual(0, res.GetY()));
		} catch (Exception e) {
			assertTrue("Points doesn't return correctly from FakeR", false);
		}
	}

	@Test
	public void testCorrectCommandToGetPoints() {
		try {
			List<Point> res = call.runCommandToGetPoints(testScriptForPoints, testData);
			assertTrue("Points doesn't return correctly from FakeR", res.isEmpty());
		} catch (Exception e) {
			assertTrue("Points doesn't return correctly from FakeR", false);
		}
	}


	//-----------------------------------------------------------
	//-----------------------------------------------------------
	//test command and scripts for data from repository
	//-----------------------------------------------------------
	//-----------------------------------------------------------

	//-----------------------------------------------------------
	//test commands
	//-----------------------------------------------------------

	@Test
	public void testCorrectCommandToGetNumberCorrectFile() {
		try {
			double resX = call.runCommandToGetNumber(correctX + "[2]", correct);
			double resY = call.runCommandToGetNumber(correctY + "[9]", correct);
			assertTrue("Double doesn't return correctly from FakeR", doubleEqual(resX, 0) && doubleEqual(resY, 0));
		} catch (Exception e) {
			assertTrue("Double doesn't return correctly from FakeR", false);
		}
	}

	@Test
	public void testCorrectCommandToGetPointCorrectFile() {
		try {
			Point res = null;
			res = call.runCommandToGetPoint("c(" + correctX + "[5], " + correctY + "[5])", correct);
			assertTrue("Points doesn't return correctly from FakeR", doubleEqual(0, res.GetX()) && doubleEqual(0, res.GetY()));
		} catch (Exception e) {
			assertTrue("Points doesn't return correctly from FakeR", false);
		}
	}

	@Test
	public void testCorrectCommandToGetPointsCorrectFile() {
		try {
			List<Point> res = call.runCommandToGetPoints(correctScriptForCorrectFileString, correct);
			assertTrue("Points doesn't return correctly from FakeR", res.isEmpty());
		} catch (Exception e) {
			assertTrue("Points doesn't return correctly from FakeR", false);
		}
	}

	//-----------------------------------------------------------
	//test scripts
	//-----------------------------------------------------------

	@Test
	public void testCorrectScripToGetNumberCorrectFile() {
		try {
			double resX = call.runScriptToGetNumber(correctScriptForCorrectFileName, convertStringToStream(correctX + "[2]"), correct);
			double resY = call.runScriptToGetNumber(correctScriptForCorrectFileName, convertStringToStream(correctY + "[3]"), correct);
			assertTrue("Double doesn't return correctly from FakeR", doubleEqual(resX, 0) && doubleEqual(resY, 0));
		} catch (Exception e) {
			assertTrue("Double doesn't return correctly from FakeR", false);
		}
	}

	@Test
	public void testCorrectScriptToGetPointCorrectFile() {
		try {
			Point res = null;
			res = call.runScriptToGetPoint(correctScriptForCorrectFileName, convertStringToStream("c(" + correctX + "[5]," + correctY + "[5])"), correct);
			assertTrue("Points doesn't return correctly from FakeR", doubleEqual(0, res.GetX()) && doubleEqual(0, res.GetY()));
		} catch (Exception e) {
			assertTrue("Points doesn't return correctly from FakeR", false);
		}
	}

	@Test
	public void testCorrectScriptToGetPointsCorrectFile() {
		try {
			List<Point> res = call.runScriptToGetPoints(correctScriptForCorrectFileName, correctScriptForCorrectFile, correct);
			assertTrue("Points doesn't return correctly from FakeR", res.isEmpty());
		} catch (Exception e) {
			assertTrue("Points doesn't return correctly from FakeR", false);
		}
	}
}
