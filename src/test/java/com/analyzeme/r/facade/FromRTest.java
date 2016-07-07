package com.analyzeme.r.facade;

import com.analyzeme.analyze.Point;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.data.resolvers.sourceinfo.DataRepositoryInfo;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.r.call.IRCaller;
import com.analyzeme.r.call.Renjin;
import com.analyzeme.repository.filerepository.FileRepository;
import com.analyzeme.repository.filerepository.TypeOfFile;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* TODO:
//for non-ex user, non-ex project, ex user, ex project
//correct script, incorrect script
public String runScript(String rScriptName,
        ByteArrayInputStream rScript,
        int userId, String projectId)

//for non-ex user, non-ex project, ex user, ex project
//existing script, non-ex script
public String runScript(String rScriptId, int userId,
        String projectId)


//for non-ex user, non-ex project, ex user, ex project
//correct command, incorrect command
public String runCommand(String rCommand, int userId,
        String projectId)

//parameters (one for correct data, one for correct command)
//result of a command
public String runCommand(String rCommand,

 */

/**
 * Created by lagroffe on 05.07.2016 12:05
 */
public class FromRTest {
    private static final double EPS = 0.00001;
    private static IRCaller call;
    private static Point[] points;

    private static final String TEST_DATA =
            "{\"Data\":[{ \"x\": \"0\",\"y\": \"0\" }," +
                    "{ \"x\": \"1\",\"y\": \"1\" }," +
                    "{\"x\": \"2\",\"y\": \"2\"}," +
                    "{ \"x\": \"3\",\"y\": \"3\" }," +
                    "{ \"x\": \"4\",\"y\": \"4\" }," +
                    "{ \"x\": \"5\",\"y\": \"5\" }," +
                    "{ \"x\": \"6\",\"y\": \"6\" }," +
                    "{ \"x\": \"7\",\"y\": \"7\" }," +
                    "{ \"x\": \"8\",\"y\": \"8\" }," +
                    "{ \"x\": \"9\",\"y\": \"9\" }," +
                    "{ \"x\": \"10\",\"y\": \"10\" }]}";
    private static final String WRONG_TEST_DATA =
            "{\"Data\":[{ \"x\": \"0\",\"y\": \"0\" ," +
                    "{ \"x\": \"1\",\"y\": \"1\" }," +
                    "{\"x\": \"2\",\"y\": \"2\"}," +
                    "{ \"x\": \"3\",\"y\": \"3\" }," +
                    "{ \"x\": \"4\",\"y\": \"4\" }," +
                    "{ \"x\": \"5\",\"y\": \"5\" }," +
                    "{ \"x\": \"6\",\"y\": \"6\" }," +
                    "{ \"x\": \"7\",\"y\": \"7\" }," +
                    "{ \"x\" \"8\",\"y\": \"8\" }," +
                    "{ \"x\": \"9\",\"\": \"9\" }," +
                    "{ \"x\": \"10\",\"y\": \"10\" }]}";

    private static final String TEST_SCRIPT_FOR_POINTS =
            "matrix(c(x[1], y[1], x[1], y[1]), " +
                    "nrow = 2, ncol = 2, byrow=TRUE)";

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

    private static String
            correctScriptForCorrectFileName;
    private static String
            correctScriptForCorrectFileString;
    private static ByteArrayInputStream
            correctScriptForCorrectFile;

    private static String
            incorrectScriptForCorrectFileName;
    private static String
            incorrectScriptForCorrectFileString;
    private static ByteArrayInputStream
            incorrectScriptForCorrectFile;

    private static String
            correctScriptForIncorrectFileName;
    private static String
            correctScriptForIncorrectFileString;
    private static ByteArrayInputStream
            correctScriptForIncorrectFile;


    public static boolean doubleEqual(
            double a, double b) {
        return Math.abs(a - b) < EPS;
    }

    private static ByteArrayInputStream convertStringToStream(
            String data) {
        byte[] b = new byte[data.length()];
        for (int i = 0; i < data.length(); i++) {
            b[i] = (byte) data.charAt(i);
        }
        return new ByteArrayInputStream(b);
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        InputStream is =
                new ByteArrayInputStream(
                        TEST_DATA.getBytes());
        JsonParser jsonParser;
        jsonParser = new JsonParser();
        points = jsonParser.parse(is).toPointArray();
        call = new Renjin();

        correctFile = convertStringToStream(TEST_DATA);
        correctFileId =
                FileRepository.getRepo()
                        .persist(correctFile,
                                CORRECT_FILENAME);
        if (correctFileId == null) {
            throw new IllegalArgumentException(
                    "Repository doesn't work");
        }
        correctX = "x_from__repo__"
                + correctFileId + "__";
        correctY = "y_from__repo__"
                + correctFileId + "__";
        correct = new ArrayList<DataSet>();
        ISourceInfo correctInfo =
                new DataRepositoryInfo(
                        correctFileId, TypeOfFile.SIMPLE_JSON);
        DataSet setCorrect =
                new DataSet(CORRECT_FILENAME,
                        correctInfo);
        setCorrect.addField("x");
        setCorrect.addField("y");
        correct.add(setCorrect);

        incorrectFile = convertStringToStream(
                WRONG_TEST_DATA);
        incorrectFileId =
                FileRepository.getRepo()
                        .persist(incorrectFile,
                                INCORRECT_FILENAME);
        incorrectX = "x_from__repo__"
                + incorrectFileId + "__";
        incorrectY = "y_from__repo__"
                + incorrectFileId + "__";
        incorrect = new ArrayList<DataSet>();
        ISourceInfo incorrectInfo =
                new DataRepositoryInfo(
                        incorrectFileId, TypeOfFile.SIMPLE_JSON);
        DataSet setIncorrect = new DataSet(
                INCORRECT_FILENAME,
                incorrectInfo);
        setIncorrect.addField("x");
        setIncorrect.addField("y");
        incorrect.add(setIncorrect);
        correctScriptForCorrectFileName = "script.r";
        correctScriptForCorrectFileString =
                "matrix(c(" + correctX + "[1], " +
                        correctY + "[1], " + correctX + "[1], " +
                        correctY + "[1]), nrow = 2, " +
                        "ncol = 2, byrow=TRUE)";
        correctScriptForCorrectFile =
                convertStringToStream(
                        correctScriptForCorrectFileString);

        incorrectScriptForCorrectFileName =
                "incorrectScript.r";
        incorrectScriptForCorrectFileString =
                "matrix(c" + correctX + "[1], "
                        + correctY + "[1, " + correctX + "[1], " +
                        correctY + "[1]), nrow = 2, ncol = 2, byrow=TRUE)";
        incorrectScriptForCorrectFile =
                convertStringToStream(
                        incorrectScriptForCorrectFileString);

        correctScriptForIncorrectFileName =
                "scriptForIncorrect.r";
        correctScriptForIncorrectFileString =
                "matrix(c(" + incorrectX + "[1], " +
                        incorrectY + "[1], " + incorrectX + "[1], " +
                        incorrectY + "[1]), nrow = 2, ncol = 2, byrow=TRUE)";
        correctScriptForIncorrectFile =
                convertStringToStream(
                        correctScriptForIncorrectFileString);

    }

    @AfterClass
    public static void after() throws Exception {
        FileRepository.getRepo()
                .deleteFileById(correctFileId);
        FileRepository.getRepo()
                .deleteFileById(incorrectFileId);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testDefaultForNullScriptName()
            throws Exception {
        GetFromR<String> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.STRING);
        link.runScript(
                null, correctScriptForCorrectFile,
                1, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDefaultForEmptyScriptName()
            throws Exception {
        GetFromR<String> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.STRING);
        link.runScript(
                "", correctScriptForCorrectFile,
                1, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDefaultForNullScript()
            throws Exception {
        GetFromR<String> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.STRING);
        link.runScript(
                correctScriptForCorrectFileName,
                null, 1, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDefaultForIncorrectUserId()
            throws Exception {
        GetFromR<String> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.STRING);
        link.runScript(
                correctScriptForCorrectFileName,
                correctScriptForCorrectFile, 0, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDefaultForNullProjectId()
            throws Exception {
        GetFromR<String> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.STRING);
        link.runScript(
                correctScriptForCorrectFileName,
                correctScriptForCorrectFile, 1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDefaultForEmptyProjectId()
            throws Exception {
        GetFromR<String> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.STRING);
        link.runScript(
                correctScriptForCorrectFileName,
                correctScriptForCorrectFile, 1, "");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNumberForNullScriptName()
            throws Exception {
        GetFromR<Double> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
        link.runScript(
                null, correctScriptForCorrectFile,
                1, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberForEmptyScriptName()
            throws Exception {
        GetFromR<Double> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
        link.runScript(
                "", correctScriptForCorrectFile,
                1, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberForNullScript()
            throws Exception {
        GetFromR<Double> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
        link.runScript(
                correctScriptForCorrectFileName,
                null, 1, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberForIncorrectUserId()
            throws Exception {
        GetFromR<Double> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
        link.runScript(
                correctScriptForCorrectFileName,
                correctScriptForCorrectFile, 0, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberForNullProjectId()
            throws Exception {
        GetFromR<Double> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
        link.runScript(
                correctScriptForCorrectFileName,
                correctScriptForCorrectFile, 1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberForEmptyProjectId()
            throws Exception {
        GetFromR<Double> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
        link.runScript(
                correctScriptForCorrectFileName,
                correctScriptForCorrectFile, 1, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPointForNullScriptName()
            throws Exception {
        GetFromR<Point> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINT);
        link.runScript(
                null, correctScriptForCorrectFile,
                1, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPointForEmptyScriptName()
            throws Exception {
        GetFromR<Point> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINT);
        link.runScript(
                "", correctScriptForCorrectFile,
                1, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPointForNullScript()
            throws Exception {
        GetFromR<Point> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINT);
        link.runScript(
                correctScriptForCorrectFileName,
                null, 1, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPointForIncorrectUserId()
            throws Exception {
        GetFromR<Point> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINT);
        link.runScript(
                correctScriptForCorrectFileName,
                correctScriptForCorrectFile, 0, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPointForNullProjectId()
            throws Exception {
        GetFromR<Point> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINT);
        link.runScript(
                correctScriptForCorrectFileName,
                correctScriptForCorrectFile, 1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPointForEmptyProjectId()
            throws Exception {
        GetFromR<Point> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINT);
        link.runScript(
                correctScriptForCorrectFileName,
                correctScriptForCorrectFile, 1, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPointsForNullScriptName()
            throws Exception {
        GetFromR<List<Point>> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINTS);
        link.runScript(
                null, correctScriptForCorrectFile,
                1, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPointsForEmptyScriptName()
            throws Exception {
        GetFromR<List<Point>> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINTS);
        link.runScript(
                "", correctScriptForCorrectFile,
                1, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPointsForNullScript()
            throws Exception {
        GetFromR<List<Point>> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINTS);
        link.runScript(
                correctScriptForCorrectFileName,
                null, 1, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPointsForIncorrectUserId()
            throws Exception {
        GetFromR<List<Point>> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINTS);
        link.runScript(
                correctScriptForCorrectFileName,
                correctScriptForCorrectFile, 0, "project");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPointsForNullProjectId()
            throws Exception {
        GetFromR<List<Point>> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINTS);
        link.runScript(
                correctScriptForCorrectFileName,
                correctScriptForCorrectFile, 1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPointsForEmptyProjectId()
            throws Exception {
        GetFromR<List<Point>> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINTS);
        link.runScript(
                correctScriptForCorrectFileName,
                correctScriptForCorrectFile, 1, "");
    }


    @Test(expected = org.renjin.parser.ParseException.class)
    public void testDefaultRunCommandForIncorrectCommand()
            throws Exception {
        GetFromR<String> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.STRING);
        link.runCommand(
                incorrectScriptForCorrectFileString,
                TEST_DATA);
    }

    @Test(expected = com.analyzeme.parsers.JsonParserException.class)
    public void testDefaultRunCommandForIncorrectData()
            throws Exception {
        GetFromR<String> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.STRING);
        link.runCommand(
                correctScriptForCorrectFileString,
                WRONG_TEST_DATA);
    }

    @Test(expected = org.renjin.parser.ParseException.class)
    public void testNumberRunCommandForIncorrectCommand()
            throws Exception {
        GetFromR<Double> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
        link.runCommand(
                incorrectScriptForCorrectFileString,
                TEST_DATA);
    }

    @Test(expected = com.analyzeme.parsers.JsonParserException.class)
    public void testNumberRunCommandForIncorrectData() throws Exception {
        GetFromR<Double> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
        link.runCommand(
                correctScriptForCorrectFileString,
                WRONG_TEST_DATA);
    }

    @Test(expected = org.renjin.parser.ParseException.class)
    public void testPointRunCommandForIncorrectCommand() throws Exception {
        GetFromR<Point> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINT);
        link.runCommand(
                incorrectScriptForCorrectFileString,
                TEST_DATA);
    }

    @Test(expected = com.analyzeme.parsers.JsonParserException.class)
    public void testPointRunCommandForIncorrectData()
            throws Exception {
        GetFromR<Point> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINT);
        link.runCommand(
                correctScriptForCorrectFileString,
                WRONG_TEST_DATA);
    }

    @Test(expected = org.renjin.parser.ParseException.class)
    public void testPointsRunCommandForIncorrectCommand()
            throws Exception {
        GetFromR<List<Point>> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINTS);
        link.runCommand(
                incorrectScriptForCorrectFileString,
                TEST_DATA);
    }

    @Test(expected =
            com.analyzeme.parsers.JsonParserException.class)
    public void testPointsRunCommandForIncorrectData()
            throws Exception {
        GetFromR<List<Point>> link =
                GetFromRFactory.getLinkToR(TypeOfReturnValue.POINTS);
        link.runCommand(
                correctScriptForCorrectFileString,
                WRONG_TEST_DATA);
    }
}
