package com.analyzeme.r.call;

import com.analyzeme.analyze.Point;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.DataRepositoryInfo;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.repository.filerepository.FileRepository;
import com.analyzeme.repository.filerepository.TypeOfFile;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by lagroffe on 26.03.2016 18:53
 */

public class FakeRTest {
	private static final double EPS = 0.00001;
	private static IRCaller call;

	private static final String TEST_DATA = "{\"Data\":[{ \"x\": \"0\",\"y\": \"0\" }," +
			"{ \"x\": \"1\",\"y\": \"1\" },{\"x\": \"2\",\"y\": \"2\"},{ \"x\": \"3\",\"y\": \"3\" }," +
			"{ \"x\": \"4\",\"y\": \"4\" },{ \"x\": \"5\",\"y\": \"5\" },{ \"x\": \"6\",\"y\": \"6\" }," +
			"{ \"x\": \"7\",\"y\": \"7\" },{ \"x\": \"8\",\"y\": \"8\" },{ \"x\": \"9\",\"y\": \"9\" }," +
			"{ \"x\": \"10\",\"y\": \"10\" }]}";
	private static final String TEST_SCRIPT_FOR_POINTS = "matrix(c(x[1], " +
			"y[1], x[1], y[1]), nrow = 2, " +
			"ncol = 2, byrow=TRUE)";
	private static ByteArrayInputStream correctFile;
	private static final String CORRECT_FILENAME = "file.json";
	private static String correctFileId;
	private static String correctX;
	private static String correctY;
	private static ArrayList<DataSet> correct;

	private static String correctScriptForCorrectFileName;
	private static String correctScriptForCorrectFileString;
	private static ByteArrayInputStream correctScriptForCorrectFile;


	public static boolean doubleEqual(double a, double b) {
		return Math.abs(a - b) < EPS;
	}

	private static ByteArrayInputStream convertStringToStream(String data) {
		byte[] b = new byte[data.length()];
		for (int i = 0; i < data.length(); i++) {
			b[i] = (byte) data.charAt(i);
		}
		return new ByteArrayInputStream(b);
	}

	@BeforeClass
	public static void beforeClass() throws Exception {
		call = new FakeR();

		correctFile = convertStringToStream(TEST_DATA);
		correctFileId = FileRepository.getRepo().persist(
				correctFile,
				CORRECT_FILENAME);
		if (correctFileId == null) {
			throw new IllegalArgumentException("Repository doesn't work");
		}
		correctX = "x_from__repo__" + correctFileId + "__";
		correctY = "y_from__repo__" + correctFileId + "__";
		correct = new ArrayList<DataSet>();
		ISourceInfo correctInfo =
				new DataRepositoryInfo(correctFileId, TypeOfFile.SIMPLE_JSON);
		DataSet setCorrect = new DataSet(CORRECT_FILENAME, correctInfo);
		setCorrect.addField("x");
		setCorrect.addField("y");
		correct.add(setCorrect);

		correctScriptForCorrectFileName = "script.r";
		correctScriptForCorrectFileString = "matrix(c(" + correctX +
				"[1], " + correctY + "[1], " + correctX + "[1], " +
				correctY + "[1]), nrow = 2, ncol = 2, byrow=TRUE)";
		correctScriptForCorrectFile =
				convertStringToStream(
						correctScriptForCorrectFileString);
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
		call.runScriptToGetPoint((String) null,
				(ByteArrayInputStream) null,
				(ArrayList<DataSet>) null);
	}


	//-----------------------------------------------------------
	//-----------------------------------------------------------
	//test commands for json data
	//-----------------------------------------------------------
	//-----------------------------------------------------------

	@Test
	public void testCorrectCommandToGetNumber() {
		try {
			double resX = call.runCommandToGetNumber("x[1]", TEST_DATA);
			double resY = call.runCommandToGetNumber("y[5]", TEST_DATA);
			assertTrue("Double isn't returned correctly from FakeR",
					doubleEqual(resX, 0) && doubleEqual(resY, 0));
		} catch (Exception e) {
			fail("Double isn't returned correctly from FakeR");
		}
	}

	@Test
	public void testCorrectCommandToGetPoint() {
		try {
			Point res = call.runCommandToGetPoint(
					"c(x[5], y[5])",
					TEST_DATA);
			assertTrue("Point isn't returned correctly from FakeR",
					doubleEqual(0, res.getX()) && doubleEqual(0, res.getY()));
		} catch (Exception e) {
			fail("Point isn't returned correctly from FakeR");
		}
	}

	@Test
	public void testCorrectCommandToGetPoints() {
		try {
			List<Point> res = call.runCommandToGetPoints(
					TEST_SCRIPT_FOR_POINTS,
					TEST_DATA);
			assertTrue("Points aren't returned correctly from FakeR",
					res.isEmpty());
		} catch (Exception e) {
			fail("Points aren't returned correctly from FakeR");
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
			double resX = call.runCommandToGetNumber(
					correctX + "[2]",
					correct);
			double resY = call.runCommandToGetNumber(
					correctY + "[9]",
					correct);
			assertTrue("Double isn't returned correctly from FakeR",
					doubleEqual(resX, 0) && doubleEqual(resY, 0));
		} catch (Exception e) {
			fail("Double isn't returned correctly from FakeR");
		}
	}

	@Test
	public void testCorrectCommandToGetPointCorrectFile() {
		try {
			Point res = call.runCommandToGetPoint(
					"c(" + correctX + "[5], " +
					correctY + "[5])", correct);
			assertTrue("Point isn't returned correctly from FakeR",
					doubleEqual(0, res.getX()) &&
							doubleEqual(0, res.getY()));
		} catch (Exception e) {
			fail("Point isn't returned correctly from FakeR");
		}
	}

	@Test
	public void testCorrectCommandToGetPointsCorrectFile() {
		try {
			List<Point> res = call.runCommandToGetPoints(
					correctScriptForCorrectFileString,
					correct);
			assertTrue("Points aren't returned correctly from FakeR", res.isEmpty());
		} catch (Exception e) {
			fail("Points aren't returned correctly from FakeR");
		}
	}

	//-----------------------------------------------------------
	//test scripts
	//-----------------------------------------------------------

	@Test
	public void testCorrectScripToGetNumberCorrectFile() {
		try {
			double resX = call.runScriptToGetNumber(correctScriptForCorrectFileName,
					convertStringToStream(correctX + "[2]"), correct);
			double resY = call.runScriptToGetNumber(correctScriptForCorrectFileName,
					convertStringToStream(correctY + "[3]"), correct);
			assertTrue("Double isn't returned correctly from FakeR",
					doubleEqual(resX, 0) &&
							doubleEqual(resY, 0));
		} catch (Exception e) {
			fail("Double isn't returned correctly from FakeR");
		}
	}

	@Test
	public void testCorrectScriptToGetPointCorrectFile() {
		try {
			Point res = call.runScriptToGetPoint(
					correctScriptForCorrectFileName,
					convertStringToStream(
							"c(" + correctX + "[5]," + correctY + "[5])"), correct);
			assertTrue("Point isn't returned correctly from FakeR",
					doubleEqual(0, res.getX()) &&
							doubleEqual(0, res.getY()));
		} catch (Exception e) {
			fail("Point isn't returned correctly from FakeR");
		}
	}

	@Test
	public void testCorrectScriptToGetPointsCorrectFile() {
		try {
			List<Point> res = call.runScriptToGetPoints(
					correctScriptForCorrectFileName,
					correctScriptForCorrectFile,
					correct);
			assertTrue("Points doesn't return correctly from FakeR", res.isEmpty());
		} catch (Exception e) {
			fail("Points aren't returned correctly from FakeR");
		}
	}
}
