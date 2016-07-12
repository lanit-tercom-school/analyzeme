package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataArray;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.DataRepositoryInfo;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.repository.filerepository.FileRepository;
import com.analyzeme.repository.filerepository.TypeOfFile;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by lagroffe on 26.03.2016 18:53
 */

public class RenjinTest {
    private static final double EPS = 0.00001;
    private static IRCaller call;
    private static DataArray<Double> dataArray;

    private static final String TEST_DATA =
            "{\"Data\":[{ \"x\": \"0\",\"y\": \"0\" }, " +
                    "{ \"x\": \"1\",\"y\": \"1\" }, {\"x\": \"2\",\"y\": \"2\"}," +
                    "{ \"x\": \"3\",\"y\": \"3\" }, { \"x\": \"4\",\"y\": \"4\" }," +
                    "{ \"x\": \"5\",\"y\": \"5\" }, { \"x\": \"6\",\"y\": \"6\" }," +
                    "{ \"x\": \"7\",\"y\": \"7\" }, { \"x\": \"8\",\"y\": \"8\" }," +
                    "{ \"x\": \"9\",\"y\": \"9\" }, { \"x\": \"10\",\"y\": \"10\" }]}";
    private static final String WRONG_TEST_DATA =
            "{\"Data\":[{ \"x\": \"0\",\"y\": \"0\" ," +
                    "{ \"x\": \"1\",\"y\": \"1\" }, {\"x\": \"2\",\"y\": \"2\"}," +
                    "{ \"x\": \"3\",\"y\": \"3\" }, { \"x\": \"4\",\"y\": \"4\" }," +
                    "{ \"x\": \"5\",\"y\": \"5\" }, { \"x\": \"6\",\"y\": \"6\" }," +
                    "{ \"x\": \"7\",\"y\": \"7\" },{ \"x\" \"8\",\"y\": \"8\" }," +
                    "{ \"x\": \"9\",\"\": \"9\" }, { \"x\": \"10\",\"y\": \"10\" }]}";

    private static final String TEST_SCRIPT_FOR_VECTORS =
            "x<-c(0, 1, 2); y<-c(0, 1, 2); " +
                    "z<-data.frame(x, y); " +
                    "names(z) <- c(\"new X name\", \"new Y name\"); " +
                    "z";
    private static ByteArrayInputStream correctFile;
    private static final String CORRECT_FILENAME =
            "fileRenjin.json";
    private static String correctFileId;
    private static String correctX;
    private static String correctY;
    private static ArrayList<DataSet> correct;

    private static ByteArrayInputStream incorrectFile;
    private static final String INCORRECT_FILENAME =
            "incorrectFileRenjin.json";
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
        InputStream is = new ByteArrayInputStream(
                TEST_DATA.getBytes());
        JsonParser jsonParser;
        jsonParser = new JsonParser();
        dataArray = jsonParser.parse(is);
        call = new Renjin();

        correctFile = convertStringToStream(TEST_DATA);
        correctFileId =
                FileRepository.getRepo().persist(
                        correctFile,
                        CORRECT_FILENAME);
        if (correctFileId == null) {
            throw new IllegalArgumentException(
                    "Repository doesn't work");
        }
        correctX = "x_from__repo__" + correctFileId + "__";
        correctY = "y_from__repo__" + correctFileId + "__";
        correct = new ArrayList<DataSet>();
        ISourceInfo correctInfo =
                new DataRepositoryInfo(correctFileId,
                        TypeOfFile.SIMPLE_JSON);
        DataSet setCorrect =
                new DataSet(CORRECT_FILENAME, correctInfo);
        setCorrect.addField("x");
        setCorrect.addField("y");
        correct.add(setCorrect);

        incorrectFile = convertStringToStream(WRONG_TEST_DATA);
        incorrectFileId =
                FileRepository.getRepo().persist(
                        incorrectFile, INCORRECT_FILENAME);
        incorrectX = "x_from__repo__" + incorrectFileId + "__";
        incorrectY = "y_from__repo__" + incorrectFileId + "__";
        incorrect = new ArrayList<DataSet>();
        ISourceInfo incorrectInfo =
                new DataRepositoryInfo(incorrectFileId,
                        TypeOfFile.SIMPLE_JSON);
        DataSet setIncorrect = new DataSet(
                INCORRECT_FILENAME,
                incorrectInfo);
        setIncorrect.addField("x");
        setIncorrect.addField("y");
        incorrect.add(setIncorrect);
        correctScriptForCorrectFileName = "script.r";
        correctScriptForCorrectFileString =
                "x<-c(" + correctX + "[1], " + correctX + "[2], "
                        + correctX + "[3]); y<-c(" + correctY + "[1], "
                        + correctY + "[2], " + correctY
                        + "[3]); z<-data.frame(x, y); " +
                        "names(z) <- c(\"new X name\", \"new Y name\"); z";
        correctScriptForCorrectFile =
                convertStringToStream(correctScriptForCorrectFileString);

        incorrectScriptForCorrectFileName =
                "incorrectScript.r";
        incorrectScriptForCorrectFileString =
                "x<-c(" + correctX + "[1], " + correctX
                        + "[2], " + correctX + "[3]); y<c("
                        + correctY + "[1], " + correctY + "[2], "
                        + correctY + "[3]); z<-data.frame(x, y); " +
                        "names(z <- c(\"new X name\", \"new Y name\"); z";
        incorrectScriptForCorrectFile =
                convertStringToStream(incorrectScriptForCorrectFileString);

        correctScriptForIncorrectFileName = "scriptForIncorrect.r";
        correctScriptForIncorrectFileString =
                "x<-c(" + incorrectX + "[1], "
                        + incorrectX + "[2], " + incorrectX + "[3]); y<-c("
                        + incorrectY + "[1], " + incorrectY + "[2], "
                        + incorrectY + "[3]); z<-data.frame(x, y); " +
                        "names(z) <- c(\"new X name\", \"new Y name\"); z";
        correctScriptForIncorrectFile =
                convertStringToStream(correctScriptForIncorrectFileString);

    }

    @AfterClass
    public static void after() throws Exception {
        FileRepository.getRepo().deleteFileById(correctFileId);
        FileRepository.getRepo().deleteFileById(incorrectFileId);
    }

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

    @Test
    public void testCorrectCommandToGetScalarCorrectJsonData() {
        try {
            ScalarResult<Double> resX;
            ScalarResult<Double> resY;
            for (int i = 0; i < dataArray.getData().size(); i++) {
                resX = call.runCommandToGetScalar("x[" +
                        (int) (i + 1) + "]", TEST_DATA);
                resY = call.runCommandToGetScalar("y[" +
                        (int) (i + 1) + "]", TEST_DATA);
                assertTrue("Scalar isn't returned correctly from Renjin",
                        doubleEqual(resX.getValue(),
                                dataArray.getData().get(i).getByKey("x")) &&
                                doubleEqual(resY.getValue(),
                                        dataArray.getData().get(i).getByKey("y")));
            }
        } catch (Exception e) {
            fail("Scalar isn't returned correctly from Renjin");
        }
    }

    @Test
    public void testCorrectCommandToGetVectorCorrectJsonData() {
        try {
            ColumnResult<Double> res = call.runCommandToGetVector(
                    "c(x[5], y[5])",
                    TEST_DATA);
            assertTrue("Vector isn't returned correctly from Renjin",
                    doubleEqual(dataArray.getData().get(4).getByKey("x"),
                            res.getValue().get(0)) &&
                            doubleEqual(
                                    dataArray.getData().get(4).getByKey("y"),
                                    res.getValue().get(1)));
        } catch (Exception e) {
            fail("Vector isn't returned correctly from Renjin");
        }
    }

    @Test
    public void testCorrectCommandToGetVectorsCorrectJsonData() {
        try {
            Map<String, List<Double>> w = new HashMap<String, List<Double>>();
            w.put("new X name", new ArrayList<Double>());
            w.put("new Y name", new ArrayList<Double>());
            for (Map.Entry<String, List<Double>> entry : w.entrySet()) {
                entry.getValue().add(0.);
                entry.getValue().add(1.);
                entry.getValue().add(2.);
            }
            FileResult<Double> was = new FileResult<Double>(w);
            FileResult<Double> res = call.runCommandToGetVectors(
                    TEST_SCRIPT_FOR_VECTORS,
                    TEST_DATA);
            assertTrue("Vectors aren't returned  correctly from Renjin",
                    was.equals(res));
        } catch (Exception e) {
            fail("Vectors aren't returned correctly from Renjin");
        }
    }


    @Test(expected = Exception.class)
    public void testIncorrectCommandToGetScalarCorrectJsonData() throws Exception {
        call.runCommandToGetScalar(
                "y[5",
                TEST_DATA);
    }

    @Test(expected = Exception.class)
    public void testIncorrectCommandToGetVectorCorrectJsonData() throws Exception {
        call.runCommandToGetVector(
                "c(x[5], y[5)",
                TEST_DATA);

    }

    @Test(expected = Exception.class)
    public void testIncorrectCommandToGetVectorsCorrectJsonData() throws Exception {
        call.runCommandToGetVectors(
                TEST_SCRIPT_FOR_VECTORS + "]",
                TEST_DATA);
    }

    @Test(expected = Exception.class)
    public void testCorrectCommandToGetScalarIncorrectJsonData() throws Exception {
        call.runCommandToGetScalar(
                "x[5]",
                WRONG_TEST_DATA);
    }

    @Test(expected = Exception.class)
    public void testCorrectCommandToGetVectorIncorrectJsonData() throws Exception {
        call.runCommandToGetVector(
                "c(x[5], y[5])",
                WRONG_TEST_DATA);
    }

    @Test(expected = Exception.class)
    public void testCorrectCommandToGetVectorsIncorrectJsonData() throws Exception {
        call.runCommandToGetVectors(
                TEST_SCRIPT_FOR_VECTORS,
                WRONG_TEST_DATA);
    }

    @Test
    public void testCorrectCommandToGetScalarCorrectFile() {
        try {
            ScalarResult<Double> resX;
            ScalarResult<Double> resY;
            for (int i = 0; i < dataArray.getData().size(); i++) {
                resX = call.runCommandToGetScalar(correctX + "[" +
                        (int) (i + 1) + "]", correct);
                resY = call.runCommandToGetScalar(correctY + "[" +
                        (int) (i + 1) + "]", correct);
                assertTrue("Scalar isn't returned correctly from Renjin",
                        doubleEqual(resX.getValue(), dataArray.getData().get(i).getByKey("x")) &&
                                doubleEqual(resY.getValue(), dataArray.getData().get(i).getByKey("y")));
            }
        } catch (Exception e) {
            fail("Scalar isn't returned  correctly from Renjin");
        }
    }

    @Test
    public void testCorrectCommandToGetVectorCorrectFile() {
        try {
            ColumnResult<Double> res = call.runCommandToGetVector("c(" + correctX +
                    "[5], " + correctY + "[5])", correct);
            assertTrue("Vector isn't returned correctly from Renjin",
                    doubleEqual(dataArray.getData().get(4).getByKey("x"), res.getValue().get(0)) &&
                            doubleEqual(dataArray.getData().get(4).getByKey("y"), res.getValue().get(1)));
        } catch (Exception e) {
            fail("Vector isn't returned correctly from Renjin");
        }
    }

    @Test
    public void testCorrectCommandToGetVectorsCorrectFile() {
        try {
            Map<String, List<Double>> w = new HashMap<String, List<Double>>();
            w.put("new X name", new ArrayList<Double>());
            w.put("new Y name", new ArrayList<Double>());
            for (Map.Entry<String, List<Double>> entry : w.entrySet()) {
                entry.getValue().add(0.);
                entry.getValue().add(1.);
                entry.getValue().add(2.);
            }
            FileResult<Double> was = new FileResult<Double>(w);
            FileResult<Double> res =
                    call.runCommandToGetVectors(
                            correctScriptForCorrectFileString,
                            correct);
            assertTrue("Vectors aren't returned correctly from Renjin",
                    was.equals(res));
        } catch (Exception e) {
            fail("Vectors aren't returned correctly from Renjin");
        }
    }

    @Test
    public void testCorrectScripToGetScalarCorrectFile() {
        try {
            ScalarResult<Double> resX;
            ScalarResult<Double> resY;
            for (int i = 0; i < dataArray.getData().size(); i++) {
                resX = call.runScriptToGetScalar(
                        correctScriptForCorrectFileName,
                        convertStringToStream(correctX + "[" + (int) (i + 1) + "]"), correct);
                resY = call.runScriptToGetScalar(
                        correctScriptForCorrectFileName,
                        convertStringToStream(correctY + "[" + (int) (i + 1) + "]"), correct);
                assertTrue("Scalar isn't returned correctly from Renjin",
                        doubleEqual(resX.getValue(), dataArray.getData().get(i).getByKey("x")) &&
                                doubleEqual(resY.getValue(), dataArray.getData().get(i).getByKey("y")));
            }
        } catch (Exception e) {
            fail("Scalar isn't returned correctly from Renjin");
        }
    }

    @Test
    public void testCorrectScriptToGetVectorCorrectFile() {
        try {
            ColumnResult<Double> res =
                    call.runScriptToGetVector(correctScriptForCorrectFileName,
                            convertStringToStream("c(" + correctX + "[5]," + correctY + "[5])"),
                            correct);
            assertTrue("Vector isn't returned correctly from Renjin",
                    doubleEqual(dataArray.getData().get(4).getByKey("x"), res.getValue().get(0)) &&
                            doubleEqual(dataArray.getData().get(4).getByKey("y"), res.getValue().get(1)));
        } catch (Exception e) {
            fail("Vector isn't returned correctly from Renjin");
        }
    }

    @Test
    public void testCorrectScriptToGetVectorsCorrectFile() {
        try {
            Map<String, List<Double>> w = new HashMap<String, List<Double>>();
            w.put("new X name", new ArrayList<Double>());
            w.put("new Y name", new ArrayList<Double>());
            for (Map.Entry<String, List<Double>> entry : w.entrySet()) {
                entry.getValue().add(0.);
                entry.getValue().add(1.);
                entry.getValue().add(2.);
            }
            FileResult<Double> was = new FileResult<Double>(w);
            FileResult<Double> res =
                    call.runScriptToGetVectors(correctScriptForCorrectFileName,
                            correctScriptForCorrectFile,
                            correct);
            assertTrue("Vectors aren't returned correctly from Renjin",
                    was.equals(res));
        } catch (Exception e) {
            fail("Vectors aren't returned correctly from Renjin");
        }
    }


    @Test(expected = Exception.class)
    public void testIncorrectCommandToGetScalarCorrectFile() throws Exception {
        call.runCommandToGetScalar(correctX + "]", correct);
    }

    @Test(expected = Exception.class)
    public void testIncorrectCommandToGetVectorCorrectFile() throws Exception {
        call.runCommandToGetVector("c" + correctX + "[5," +
                correctY + "[5)", correct);
    }

    @Test(expected = Exception.class)
    public void testIncorrectCommandToGetVectorsCorrectFile() throws Exception {
        call.runCommandToGetVectors(incorrectScriptForCorrectFileString,
                correct);
    }

    @Test(expected = Exception.class)
    public void testIncorrectScriptToGetScalarCorrectFile() throws Exception {
        call.runScriptToGetScalar(
                incorrectScriptForCorrectFileName,
                convertStringToStream(correctX + "]"), correct);
    }

    @Test(expected = Exception.class)
    public void testIncorrectScriptToGetVectorCorrectFile() throws Exception {
        call.runScriptToGetVector(
                incorrectScriptForCorrectFileName,
                convertStringToStream("c" + correctX + "[5," +
                        correctY + "[5)"), correct);
    }

    @Test(expected = Exception.class)
    public void testIncorrectScriptToGetVectorsCorrectFile() throws Exception {
        call.runScriptToGetVectors(
                incorrectScriptForCorrectFileName,
                incorrectScriptForCorrectFile, correct);
    }

    @Test(expected = Exception.class)
    public void testCorrectCommandToGetScalarIncorrectFile() throws Exception {
        call.runCommandToGetScalar(incorrectX + "[5]", incorrect);
    }

    @Test(expected = Exception.class)
    public void testCorrectCommandToGetVectorIncorrectFile() throws Exception {
        call.runCommandToGetVector("c(" + incorrectX + "[5], " +
                incorrectY + "[5])", incorrect);
    }

    @Test(expected = Exception.class)
    public void testCorrectCommandToGetVectorsIncorrectFile() throws Exception {
        call.runCommandToGetVectors(correctScriptForIncorrectFileString,
                incorrect);
    }

    @Test(expected = Exception.class)
    public void testCorrectScripToGetScalarIncorrectFile() throws Exception {
        call.runScriptToGetScalar(correctScriptForIncorrectFileName,
                convertStringToStream(incorrectX + "[5]"), incorrect);
    }

    @Test(expected = Exception.class)
    public void testCorrectScriptToGetVectorIncorrectFile() throws Exception {
        call.runScriptToGetVector(correctScriptForIncorrectFileName,
                convertStringToStream("c(" + incorrectX + "[5]," +
                        incorrectY + "[5])"), incorrect);
    }

    @Test(expected = Exception.class)
    public void testCorrectScriptToGetVectorsIncorrectFile() throws Exception {
        call.runScriptToGetVectors(correctScriptForIncorrectFileName,
                correctScriptForIncorrectFile, incorrect);
    }
}
