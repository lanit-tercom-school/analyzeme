package com.analyzeme.R.call;

import com.analyzeme.analyze.Point;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.FileInRepositoryInfo;
import com.analyzeme.data.ISourceInfo;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.repository.FileRepository;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by lagroffe on 26.03.2016 18:53
 */

public class RenjinTest {
	private static double eps = 0.00001;
	private static IRCaller call;
	private static Point[] points;

	private static String testData = "{\"Data\":[{ \"x\": \"0\",\"y\": \"0\" },{ \"x\": \"1\",\"y\": \"1\" },{\"x\": \"2\",\"y\": \"2\"},{ \"x\": \"3\",\"y\": \"3\" },{ \"x\": \"4\",\"y\": \"4\" },{ \"x\": \"5\",\"y\": \"5\" },{ \"x\": \"6\",\"y\": \"6\" },{ \"x\": \"7\",\"y\": \"7\" },{ \"x\": \"8\",\"y\": \"8\" },{ \"x\": \"9\",\"y\": \"9\" },{ \"x\": \"10\",\"y\": \"10\" }]}";
	private static String wrongTestData = "{\"Data\":[{ \"x\": \"0\",\"y\": \"0\" ,{ \"x\": \"1\",\"y\": \"1\" },{\"x\": \"2\",\"y\": \"2\"},{ \"x\": \"3\",\"y\": \"3\" },{ \"x\": \"4\",\"y\": \"4\" },{ \"x\": \"5\",\"y\": \"5\" },{ \"x\": \"6\",\"y\": \"6\" },{ \"x\": \"7\",\"y\": \"7\" },{ \"x\" \"8\",\"y\": \"8\" },{ \"x\": \"9\",\"\": \"9\" },{ \"x\": \"10\",\"y\": \"10\" }]}";

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

	private static ByteArrayInputStream incorrectFile;
	private static String incorrectFilename = "incorrectFile.json";
	private static String incorrectFileId;
	private static String incorrectX;
	private static String incorrectY;
	private static ArrayList<DataSet> incorrect;

	private static String correctScriptForCorrectFileName;
	private static String correctScriptForCorrectFileString;
	private static ByteArrayInputStream correctScriptForCorrectFile;

	private static String incorrectScriptForCorrectFileName;
	private static String incorrectScriptForCorrectFileString;
	private static ByteArrayInputStream incorrectScriptForCorrectFile;

	private static String correctScriptForIncorrectFileName;
	private static String correctScriptForIncorrectFileString;
	private static ByteArrayInputStream correctScriptForIncorrectFile;

	@BeforeClass
	public static void beforeClass() throws Exception {
		InputStream is = new ByteArrayInputStream(testData.getBytes());
		JsonParser jsonParser;
		jsonParser = new JsonParser();
		points = jsonParser.getPointsFromPointJson(is);
		call = new Renjin();

		correctFile = convertStringToStream(testData);
		correctFileId = FileRepository.getRepo().addNewFileForTests(correctFile, correctFilename);
		if (correctFileId == null) throw new IllegalArgumentException("Repository doesn't work");
		correctX = "x_from__repo__" + correctFileId + "__";
		correctY = "y_from__repo__" + correctFileId + "__";
		correct = new ArrayList<DataSet>();
		ISourceInfo correctInfo = new FileInRepositoryInfo(correctFileId);
		DataSet setCorrect = new DataSet(correctFilename, correctInfo);
		setCorrect.addField("x");
		setCorrect.addField("y");
		correct.add(setCorrect);

		incorrectFile = convertStringToStream(wrongTestData);
		incorrectFileId = FileRepository.getRepo().addNewFileForTests(incorrectFile, incorrectFilename);
		incorrectX = "x_from__repo__" + incorrectFileId + "__";
		incorrectY = "y_from__repo__" + incorrectFileId + "__";
		incorrect = new ArrayList<DataSet>();
		ISourceInfo incorrectInfo = new FileInRepositoryInfo(incorrectFileId);
		DataSet setIncorrect = new DataSet(incorrectFilename, incorrectInfo);
		setIncorrect.addField("x");
		setIncorrect.addField("y");
		incorrect.add(setIncorrect);
		correctScriptForCorrectFileName = "script.R";
		correctScriptForCorrectFileString = "matrix(c(" + correctX + "[1], " + correctY + "[1], " + correctX + "[1], " + correctY + "[1]), nrow = 2, ncol = 2, byrow=TRUE)";
		correctScriptForCorrectFile = convertStringToStream(correctScriptForCorrectFileString);

		incorrectScriptForCorrectFileName = "incorrectScript.R";
		incorrectScriptForCorrectFileString = "matrix(c" + correctX + "[1], " + correctY + "[1, " + correctX + "[1], " + correctY + "[1]), nrow = 2, ncol = 2, byrow=TRUE)";
		incorrectScriptForCorrectFile = convertStringToStream(incorrectScriptForCorrectFileString);

		correctScriptForIncorrectFileName = "scriptForIncorrect.R";
		correctScriptForIncorrectFileString = "matrix(c(" + incorrectX + "[1], " + incorrectY + "[1], " + incorrectX + "[1], " + incorrectY + "[1]), nrow = 2, ncol = 2, byrow=TRUE)";
		correctScriptForIncorrectFile = convertStringToStream(correctScriptForIncorrectFileString);

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


	//-----------------------------------------------------------
	//test correct commands for correct data (json)
	//-----------------------------------------------------------

	@Test
	public void testCorrectCommandToGetNumberCorrectJsonData() {
		try {
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
	public void testCorrectCommandToGetPointCorrectJsonData() {
		try {
			Point res = null;
			res = call.runCommandToGetPoint("c(x[5], y[5])", testData);
			assertTrue("Points doesn't return correctly from Renjin", doubleEqual(points[4].GetX(), res.GetX()) && doubleEqual(points[4].GetY(), res.GetY()));
		} catch (Exception e) {
			assertTrue("Points doesn't return correctly from Renjin", false);
		}
	}

	@Test
	public void testCorrectCommandToGetPointsCorrectJsonData() {
		try {
			List<Point> res = call.runCommandToGetPoints(testScriptForPoints, testData);
			assertTrue("Points doesn't return correctly from Renjin", doubleEqual(points[0].GetX(), res.get(0).GetX()) && doubleEqual(points[0].GetY(), res.get(0).GetY()) && doubleEqual(points[0].GetX(), res.get(1).GetX()) && doubleEqual(points[0].GetY(), res.get(1).GetY()));
		} catch (Exception e) {
			assertTrue("Points doesn't return correctly from Renjin", false);
		}
	}

	//-----------------------------------------------------------
	//test incorrect commands for correct data (json)
	//-----------------------------------------------------------

	@Test(expected = Exception.class)
	public void testIncorrectCommandToGetNumberCorrectJsonData() throws Exception {
		call.runCommandToGetNumber("y[5", testData);
	}

	@Test(expected = Exception.class)
	public void testIncorrectCommandToGetPointCorrectJsonData() throws Exception {
		call.runCommandToGetPoint("c(x[5], y[5)", testData);

	}

	@Test(expected = Exception.class)
	public void testIncorrectCommandToGetPointsCorrectJsonData() throws Exception {
		call.runCommandToGetPoints(testScriptForPoints + "]", testData);
	}

	//-----------------------------------------------------------
	//test correct commands for incorrect data (json)
	//-----------------------------------------------------------

	@Test(expected = Exception.class)
	public void testCorrectCommandToGetNumberIncorrectJsonData() throws Exception {
		call.runCommandToGetNumber("x[5]", wrongTestData);
	}

	@Test(expected = Exception.class)
	public void testCorrectCommandToGetPointIncorrectJsonData() throws Exception {
		call.runCommandToGetPoint("c(x[5], y[5])", wrongTestData);
	}

	@Test(expected = Exception.class)
	public void testCorrectCommandToGetPointsIncorrectJsonData() throws Exception {
		call.runCommandToGetPoints(testScriptForPoints, wrongTestData);
	}


	//-----------------------------------------------------------
	//-----------------------------------------------------------
	//test command and scripts for data from repository
	//-----------------------------------------------------------
	//-----------------------------------------------------------

	//-----------------------------------------------------------
	//test correct commands for correct data (file)
	//-----------------------------------------------------------

	@Test
	public void testCorrectCommandToGetNumberCorrectFile() {
		try {
			double resX, resY;
			for (int i = 0; i < points.length; i++) {
				resX = call.runCommandToGetNumber(correctX + "[" + (int) (i + 1) + "]", correct);
				resY = call.runCommandToGetNumber(correctY + "[" + (int) (i + 1) + "]", correct);
				assertTrue("Double doesn't return correctly from Renjin", doubleEqual(resX, points[i].GetX()) && doubleEqual(resY, points[i].GetY()));
			}
		} catch (Exception e) {
			assertTrue("Double doesn't return correctly from Renjin", false);
		}
	}

	@Test
	public void testCorrectCommandToGetPointCorrectFile() {
		try {
			Point res = null;
			res = call.runCommandToGetPoint("c(" + correctX + "[5], " + correctY + "[5])", correct);
			assertTrue("Points doesn't return correctly from Renjin", doubleEqual(points[4].GetX(), res.GetX()) && doubleEqual(points[4].GetY(), res.GetY()));
		} catch (Exception e) {
			assertTrue("Points doesn't return correctly from Renjin", false);
		}
	}

	@Test
	public void testCorrectCommandToGetPointsCorrectFile() {
		try {
			List<Point> res = call.runCommandToGetPoints(correctScriptForCorrectFileString, correct);
			assertTrue("Points doesn't return correctly from Renjin", doubleEqual(points[0].GetX(), res.get(0).GetX()) && doubleEqual(points[0].GetY(), res.get(0).GetY()) && doubleEqual(points[0].GetX(), res.get(1).GetX()) && doubleEqual(points[0].GetY(), res.get(1).GetY()));
		} catch (Exception e) {
			assertTrue("Points doesn't return correctly from Renjin", false);
		}
	}

	//-----------------------------------------------------------
	//test correct scripts for correct data (file)
	//-----------------------------------------------------------

	@Test
	public void testCorrectScripToGetNumberCorrectFile() {
		try {
			double resX, resY;
			for (int i = 0; i < points.length; i++) {
				resX = call.runScriptToGetNumber(correctScriptForCorrectFileName, convertStringToStream(correctX + "[" + (int) (i + 1) + "]"), correct);
				resY = call.runScriptToGetNumber(correctScriptForCorrectFileName, convertStringToStream(correctY + "[" + (int) (i + 1) + "]"), correct);
				assertTrue("Double doesn't return correctly from Renjin", doubleEqual(resX, points[i].GetX()) && doubleEqual(resY, points[i].GetY()));
			}
		} catch (Exception e) {
			assertTrue("Double doesn't return correctly from Renjin", false);
		}
	}

	@Test
	public void testCorrectScriptToGetPointCorrectFile() {
		try {
			Point res = null;
			res = call.runScriptToGetPoint(correctScriptForCorrectFileName, convertStringToStream("c(" + correctX + "[5]," + correctY + "[5])"), correct);
			assertTrue("Points doesn't return correctly from Renjin", doubleEqual(points[4].GetX(), res.GetX()) && doubleEqual(points[4].GetY(), res.GetY()));
		} catch (Exception e) {
			assertTrue("Points doesn't return correctly from Renjin", false);
		}
	}

	@Test
	public void testCorrectScriptToGetPointsCorrectFile() {
		try {
			List<Point> res = call.runScriptToGetPoints(correctScriptForCorrectFileName, correctScriptForCorrectFile, correct);
			assertTrue("Points doesn't return correctly from Renjin", doubleEqual(points[0].GetX(), res.get(0).GetX()) && doubleEqual(points[0].GetY(), res.get(0).GetY()) && doubleEqual(points[0].GetX(), res.get(1).GetX()) && doubleEqual(points[0].GetY(), res.get(1).GetY()));
		} catch (Exception e) {
			assertTrue("Points doesn't return correctly from Renjin", false);
		}
	}

	//-----------------------------------------------------------
	//test incorrect commands for correct data (file)
	//-----------------------------------------------------------

	@Test(expected = Exception.class)
	public void testIncorrectCommandToGetNumberCorrectFile() throws Exception {
		call.runCommandToGetNumber(correctX + "]", correct);
	}

	@Test(expected = Exception.class)
	public void testIncorrectCommandToGetPointCorrectFile() throws Exception {
		call.runCommandToGetPoint("c" + correctX + "[5," + correctY + "[5)", correct);
	}

	@Test(expected = Exception.class)
	public void testIncorrectCommandToGetPointsCorrectFile() throws Exception {
		call.runCommandToGetPoints(incorrectScriptForCorrectFileString, correct);
	}

	//-----------------------------------------------------------
	//test incorrect scripts for correct data (file)
	//-----------------------------------------------------------

	@Test(expected = Exception.class)
	public void testIncorrectScriptToGetNumberCorrectFile() throws Exception {
		call.runScriptToGetNumber(incorrectScriptForCorrectFileName, convertStringToStream(correctX + "]"), correct);
	}

	@Test(expected = Exception.class)
	public void testIncorrectScriptToGetPointCorrectFile() throws Exception {
		call.runScriptToGetPoint(incorrectScriptForCorrectFileName, convertStringToStream("c" + correctX + "[5," + correctY + "[5)"), correct);
	}

	@Test(expected = Exception.class)
	public void testIncorrectScriptToGetPointsCorrectFile() throws Exception {
		call.runScriptToGetPoints(incorrectScriptForCorrectFileName, incorrectScriptForCorrectFile, correct);
	}

	//-----------------------------------------------------------
	//test correct commands for incorrect data (file)
	//-----------------------------------------------------------

	@Test(expected = Exception.class)
	public void testCorrectCommandToGetNumberIncorrectFile() throws Exception {
		call.runCommandToGetNumber(incorrectX + "[5]", incorrect);
	}

	@Test(expected = Exception.class)
	public void testCorrectCommandToGetPointIncorrectFile() throws Exception {
		call.runCommandToGetPoint("c(" + incorrectX + "[5], " + incorrectY + "[5])", incorrect);
	}

	@Test(expected = Exception.class)
	public void testCorrectCommandToGetPointsIncorrectFile() throws Exception {
		call.runCommandToGetPoints(correctScriptForIncorrectFileString, incorrect);
	}

	//-----------------------------------------------------------
	//test correct scripts for incorrect data (file)
	//-----------------------------------------------------------

	@Test(expected = Exception.class)
	public void testCorrectScripToGetNumberIncorrectFile() throws Exception {
		call.runScriptToGetNumber(correctScriptForIncorrectFileName, convertStringToStream(incorrectX + "[5]"), incorrect);
	}

	@Test(expected = Exception.class)
	public void testCorrectScriptToGetPointIncorrectFile() throws Exception {
		call.runScriptToGetPoint(correctScriptForIncorrectFileName, convertStringToStream("c(" + incorrectX + "[5]," + incorrectY + "[5])"), incorrect);
	}

	@Test(expected = Exception.class)
	public void testCorrectScriptToGetPointsIncorrectFile() throws Exception {
		call.runScriptToGetPoints(correctScriptForIncorrectFileName, correctScriptForIncorrectFile, incorrect);
	}
}
