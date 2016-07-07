package com.analyzeme.r.call;

import com.analyzeme.analyze.Point;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.JsonPointFileInRepositoryInfo;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.repository.filerepository.FileRepository;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by lagroffe on 26.03.2016 18:53
 */

@Ignore("Should usually be ignored: works only locally with configured r")
public class RserveTest {
	/*private static final double EPS = 0.00001;
	private static IRCaller call;
	private static Point[] points;

	private static final String TEST_DATA =
			"{\"Data\":[{ \"x\": \"0\",\"y\": \"0\" },{ \"x\": \"1\",\"y\": \"1\" }," +
					"{\"x\": \"2\",\"y\": \"2\"},{ \"x\": \"3\",\"y\": \"3\" }," +
					"{ \"x\": \"4\",\"y\": \"4\" },{ \"x\": \"5\",\"y\": \"5\" }," +
					"{ \"x\": \"6\",\"y\": \"6\" },{ \"x\": \"7\",\"y\": \"7\" }," +
					"{ \"x\": \"8\",\"y\": \"8\" },{ \"x\": \"9\",\"y\": \"9\" }," +
					"{ \"x\": \"10\",\"y\": \"10\" }]}";
	private static final String WRONG_TEST_DATA =
			"{\"Data\":[{ \"x\": \"0\",\"y\": \"0\" ,{ \"x\": \"1\",\"y\": \"1\" }," +
					"{\"x\": \"2\",\"y\": \"2\"},{ \"x\": \"3\",\"y\": \"3\" }," +
					"{ \"x\": \"4\",\"y\": \"4\" },{ \"x\": \"5\",\"y\": \"5\" }," +
					"{ \"x\": \"6\",\"y\": \"6\" },{ \"x\": \"7\",\"y\": \"7\" }," +
					"{ \"x\" \"8\",\"y\": \"8\" },{ \"x\": \"9\",\"\": \"9\" }," +
					"{ \"x\": \"10\",\"y\": \"10\" }]}";

	private static final String TEST_SCRIPT_FOR_POINTS =
			"matrix(c(x[1], y[1], x[1], y[1]), nrow = 2, ncol = 2, byrow=TRUE)";

	private static ByteArrayInputStream correctFile;
	private static final String CORRECT_FILENAME =
			"fileRserve.json";
	private static String correctFileId;
	private static String correctX;
	private static String correctY;
	private static ArrayList<DataSet> correct;

	private static ByteArrayInputStream incorrectFile;
	private static final String INCORRECT_FILENAME =
			"incorrectFileRserve.json";
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
		InputStream is = new ByteArrayInputStream(TEST_DATA.getBytes());
		JsonParser jsonParser;
		jsonParser = new JsonParser();
		points = jsonParser.getPointsFromPointJson(is);
		call = new Rserve();

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
				new JsonPointFileInRepositoryInfo(correctFileId);
		DataSet setCorrect =
				new DataSet(CORRECT_FILENAME, correctInfo);
		setCorrect.addField("x");
		setCorrect.addField("y");
		correct.add(setCorrect);

		incorrectFile = convertStringToStream(WRONG_TEST_DATA);
		incorrectFileId = FileRepository.getRepo().persist(
				incorrectFile, INCORRECT_FILENAME);
		incorrectX = "x_from__repo__" + incorrectFileId + "__";
		incorrectY = "y_from__repo__" + incorrectFileId + "__";
		incorrect = new ArrayList<DataSet>();
		ISourceInfo incorrectInfo = new JsonPointFileInRepositoryInfo(
				incorrectFileId);
		DataSet setIncorrect = new DataSet(
				INCORRECT_FILENAME, incorrectInfo);
		setIncorrect.addField("x");
		setIncorrect.addField("y");
		incorrect.add(setIncorrect);
		correctScriptForCorrectFileName = "script.r";
		correctScriptForCorrectFileString = "matrix(c(" + correctX + "[1], " +
				correctY + "[1], " + correctX + "[1], " +
				correctY + "[1]), nrow = 2, ncol = 2, byrow=TRUE)";
		correctScriptForCorrectFile =
				convertStringToStream(correctScriptForCorrectFileString);

		incorrectScriptForCorrectFileName = "incorrectScript.r";
		incorrectScriptForCorrectFileString = "matrix(c" + correctX + "[1], " +
				correctY + "[1, " + correctX + "[1], " +
				correctY + "[1]), nrow = 2, ncol = 2, byrow=TRUE)";
		incorrectScriptForCorrectFile =
				convertStringToStream(incorrectScriptForCorrectFileString);

		correctScriptForIncorrectFileName = "scriptForIncorrect.r";
		correctScriptForIncorrectFileString = "matrix(c(" + incorrectX + "[1], " +
				incorrectY + "[1], " + incorrectX + "[1], " +
				incorrectY + "[1]), nrow = 2, ncol = 2, byrow=TRUE)";
		correctScriptForIncorrectFile =
				convertStringToStream(correctScriptForIncorrectFileString);

	}

	@AfterClass
	public static void after() throws Exception {
		FileRepository.getRepo().deleteFileById(correctFileId);
		FileRepository.getRepo().deleteFileById(incorrectFileId);
	}

	//-------------------------------
	//Tests for illegal arguments
	//-------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument1() throws Exception {
		call.runCommandToGetVectors("", (String) null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument2() throws Exception {
		call.runCommandToGetVector((String) null, "");

	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument3() throws Exception {
		call.runCommandToGetScalar("", (String) null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument4() throws Exception {
		call.runScriptToGetVector((String) null,
				(ByteArrayInputStream) null,
				(ArrayList<DataSet>) null);
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
			double resX;
			double resY;
			for (int i = 0; i < points.length; i++) {
				resX = call.runCommandToGetScalar("x[" +
						(int) (i + 1) + "]", TEST_DATA);
				resY = call.runCommandToGetScalar("y[" +
						(int) (i + 1) + "]", TEST_DATA);
				assertTrue("Double isn't returned correctly from Rserve",
						doubleEqual(resX, points[i].getX()) &&
								doubleEqual(resY, points[i].getY()));
			}
		} catch (Exception e) {
			fail("Double isn't returned correctly from Rserve");
		}
	}

	@Test
	public void testCorrectCommandToGetPointCorrectJsonData() {
		try {
			Point res = call.runCommandToGetVector("c(x[5], y[5])",
					TEST_DATA);
			assertTrue("Point isn't returned correctly from Rserve",
					doubleEqual(points[4].getX(), res.getX()) &&
							doubleEqual(points[4].getY(), res.getY()));
		} catch (Exception e) {
			fail("Point isn't returnedcorrectly from Rserve");
		}
	}

	@Test
	public void testCorrectCommandToGetPointsCorrectJsonData() {
		try {
			List<Point> res = call.runCommandToGetVectors(
					TEST_SCRIPT_FOR_POINTS,
					TEST_DATA);
			assertTrue("Points aren't returned correctly from Rserve",
					doubleEqual(points[0].getX(), res.get(0).getX()) &&
							doubleEqual(points[0].getY(), res.get(0).getY()) &&
							doubleEqual(points[0].getX(), res.get(1).getX()) &&
							doubleEqual(points[0].getY(), res.get(1).getY()));
		} catch (Exception e) {
			fail("Points aren't returned correctly from Rserve");
		}
	}

	//-----------------------------------------------------------
	//test incorrect commands for correct data (json)
	//-----------------------------------------------------------

	@Test(expected = Exception.class)
	public void testIncorrectCommandToGetNumberCorrectJsonData() throws Exception {
		call.runCommandToGetScalar("y[5", TEST_DATA);
	}

	@Test(expected = Exception.class)
	public void testIncorrectCommandToGetPointCorrectJsonData() throws Exception {
		call.runCommandToGetVector("c(x[5], y[5)", TEST_DATA);

	}

	@Test(expected = Exception.class)
	public void testIncorrectCommandToGetPointsCorrectJsonData() throws Exception {
		call.runCommandToGetVectors(TEST_SCRIPT_FOR_POINTS + "]", TEST_DATA);
	}

	//-----------------------------------------------------------
	//test correct commands for incorrect data (json)
	//-----------------------------------------------------------

	@Test(expected = Exception.class)
	public void testCorrectCommandToGetNumberIncorrectJsonData() throws Exception {
		call.runCommandToGetScalar("x[5]", WRONG_TEST_DATA);
	}

	@Test(expected = Exception.class)
	public void testCorrectCommandToGetPointIncorrectJsonData() throws Exception {
		call.runCommandToGetVector("c(x[5], y[5])", WRONG_TEST_DATA);
	}

	@Test(expected = Exception.class)
	public void testCorrectCommandToGetPointsIncorrectJsonData() throws Exception {
		call.runCommandToGetVectors(TEST_SCRIPT_FOR_POINTS, WRONG_TEST_DATA);
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
			double resX;
			double resY;
			for (int i = 0; i < points.length; i++) {
				resX = call.runCommandToGetScalar(correctX + "[" +
						(int) (i + 1) + "]", correct);
				resY = call.runCommandToGetScalar(correctY + "[" +
						(int) (i + 1) + "]", correct);
				assertTrue("Double isn't returned correctly from Rserve",
						doubleEqual(resX, points[i].getX()) &&
								doubleEqual(resY, points[i].getY()));
			}
		} catch (Exception e) {
			fail("Double isn't returned correctly from Rserve");
		}
	}

	@Test
	public void testCorrectCommandToGetPointCorrectFile() {
		try {
			Point res =  call.runCommandToGetVector("c(" +
					correctX + "[5], " + correctY + "[5])",
					correct);
			assertTrue("Point  isn't returned correctly from Rserve",
					doubleEqual(points[4].getX(), res.getX()) &&
							doubleEqual(points[4].getY(), res.getY()));
		} catch (Exception e) {
			fail("Point isn't returned correctly from Rserve");
		}
	}

	@Test
	public void testCorrectCommandToGetPointsCorrectFile() {
		try {
			List<Point> res = call.runCommandToGetVectors(
					correctScriptForCorrectFileString,
					correct);
			assertTrue("Points aren't returned correctly from Rserve",
					doubleEqual(points[0].getX(), res.get(0).getX()) &&
							doubleEqual(points[0].getY(), res.get(0).getY()) &&
							doubleEqual(points[0].getX(), res.get(1).getX()) &&
							doubleEqual(points[0].getY(), res.get(1).getY()));
		} catch (Exception e) {
			fail("Points aren't returned correctly from Rserve");
		}
	}

	//-----------------------------------------------------------
	//test correct scripts for correct data (file)
	//-----------------------------------------------------------

	@Test
	public void testCorrectScripToGetNumberCorrectFile() {
		try {
			double resX;
			double resY;
			for (int i = 0; i < points.length; i++) {
				resX = call.runScriptToGetScalar(
						correctScriptForCorrectFileName,
						convertStringToStream(correctX + "[" + (int) (i + 1) + "]"),
						correct);
				resY = call.runScriptToGetScalar(
						correctScriptForCorrectFileName,
						convertStringToStream(correctY + "[" + (int) (i + 1) + "]"),
						correct);
				assertTrue("Double isn't returned correctly from Rserve",
						doubleEqual(resX, points[i].getX()) &&
								doubleEqual(resY, points[i].getY()));
			}
		} catch (Exception e) {
			fail("Double isn't returned correctly from Rserve");
		}
	}

	@Test
	public void testCorrectScriptToGetPointCorrectFile() {
		try {
			Point res = call.runScriptToGetVector(
					correctScriptForCorrectFileName,
					convertStringToStream("c(" + correctX + "[5]," +
							correctY + "[5])"),
					correct);
			assertTrue("Point isn't returned correctly from Rserve",
					doubleEqual(points[4].getX(), res.getX()) &&
							doubleEqual(points[4].getY(), res.getY()));
		} catch (Exception e) {
			fail("Point isn't returned correctly from Rserve");
		}
	}

	@Test
	public void testCorrectScriptToGetPointsCorrectFile() {
		try {
			List<Point> res = call.runScriptToGetVectors(
					correctScriptForCorrectFileName,
					correctScriptForCorrectFile,
					correct);
			assertTrue("Points aren't returned correctly from Rserve",
					doubleEqual(points[0].getX(), res.get(0).getX()) &&
							doubleEqual(points[0].getY(), res.get(0).getY()) &&
							doubleEqual(points[0].getX(), res.get(1).getX()) &&
							doubleEqual(points[0].getY(), res.get(1).getY()));
		} catch (Exception e) {
			fail("Points aren't returned correctly from Rserve");
		}
	}

	//-----------------------------------------------------------
	//test incorrect commands for correct data (file)
	//-----------------------------------------------------------

	@Test(expected = Exception.class)
	public void testIncorrectCommandToGetNumberCorrectFile() throws Exception {
		call.runCommandToGetScalar(correctX + "]", correct);
	}

	@Test(expected = Exception.class)
	public void testIncorrectCommandToGetPointCorrectFile() throws Exception {
		call.runCommandToGetVector("c" + correctX + "[5," +
				correctY + "[5)", correct);
	}

	@Test(expected = Exception.class)
	public void testIncorrectCommandToGetPointsCorrectFile() throws Exception {
		call.runCommandToGetVectors(incorrectScriptForCorrectFileString, correct);
	}

	//-----------------------------------------------------------
	//test incorrect scripts for correct data (file)
	//-----------------------------------------------------------

	@Test(expected = Exception.class)
	public void testIncorrectScriptToGetNumberCorrectFile() throws Exception {
		call.runScriptToGetScalar(incorrectScriptForCorrectFileName,
				convertStringToStream(correctX + "]"), correct);
	}

	@Test(expected = Exception.class)
	public void testIncorrectScriptToGetPointCorrectFile() throws Exception {
		call.runScriptToGetVector(incorrectScriptForCorrectFileName,
				convertStringToStream("c" + correctX + "[5," +
						correctY + "[5)"), correct);
	}

	@Test(expected = Exception.class)
	public void testIncorrectScriptToGetPointsCorrectFile() throws Exception {
		call.runScriptToGetVectors(incorrectScriptForCorrectFileName,
				incorrectScriptForCorrectFile, correct);
	}

	//-----------------------------------------------------------
	//test correct commands for incorrect data (file)
	//-----------------------------------------------------------

	@Test(expected = Exception.class)
	public void testCorrectCommandToGetNumberIncorrectFile() throws Exception {
		call.runCommandToGetScalar(incorrectX + "[5]", incorrect);
	}

	@Test(expected = Exception.class)
	public void testCorrectCommandToGetPointIncorrectFile() throws Exception {
		call.runCommandToGetVector("c(" + incorrectX + "[5], " +
				incorrectY + "[5])", incorrect);
	}

	@Test(expected = Exception.class)
	public void testCorrectCommandToGetPointsIncorrectFile() throws Exception {
		call.runCommandToGetVectors(
				correctScriptForIncorrectFileString,
				incorrect);
	}

	//-----------------------------------------------------------
	//test correct scripts for incorrect data (file)
	//-----------------------------------------------------------

	@Test(expected = Exception.class)
	public void testCorrectScripToGetNumberIncorrectFile() throws Exception {
		call.runScriptToGetScalar(
				correctScriptForIncorrectFileName,
				convertStringToStream(incorrectX + "[5]"), incorrect);
	}

	@Test(expected = Exception.class)
	public void testCorrectScriptToGetPointIncorrectFile() throws Exception {
		call.runScriptToGetVector(
				correctScriptForIncorrectFileName,
				convertStringToStream("c(" + incorrectX + "[5]," +
						incorrectY + "[5])"), incorrect);
	}

	@Test(expected = Exception.class)
	public void testCorrectScriptToGetPointsIncorrectFile() throws Exception {
		call.runScriptToGetVectors(
				correctScriptForIncorrectFileName,
				correctScriptForIncorrectFile,
				incorrect);
	}     */
}
