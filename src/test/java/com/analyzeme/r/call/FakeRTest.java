package com.analyzeme.r.call;


import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.analyzers.result.VectorResult;
import com.analyzeme.analyzers.result.VectorsResult;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataEntryType;
import com.analyzeme.data.dataset.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.DataRepositoryInfo;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.repository.filerepository.FileRepository;
import com.analyzeme.repository.filerepository.TypeOfFile;
import com.analyzeme.scripts.InputType;
import com.analyzeme.scripts.Script;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FakeRTest {
    private static IRCaller call;

    private static final String TEST_DATA = "{\"Data\":[{ \"x\": \"0\",\"y\": \"0\" }," +
            "{ \"x\": \"1\",\"y\": \"1\" },{\"x\": \"2\",\"y\": \"2\"},{ \"x\": \"3\",\"y\": \"3\" }," +
            "{ \"x\": \"4\",\"y\": \"4\" },{ \"x\": \"5\",\"y\": \"5\" },{ \"x\": \"6\",\"y\": \"6\" }," +
            "{ \"x\": \"7\",\"y\": \"7\" },{ \"x\": \"8\",\"y\": \"8\" },{ \"x\": \"9\",\"y\": \"9\" }," +
            "{ \"x\": \"10\",\"y\": \"10\" }]}";
    private static ByteArrayInputStream correctFile;
    private static final String CORRECT_FILENAME = "file.json";
    private static String correctFileId;
    private static String correctX;
    private static String correctY;
    private static ArrayList<DataSet> correct;

    private static String correctScriptForCorrectFileString;

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

        correctScriptForCorrectFileString = "matrix(c(" + correctX +
                "[1], " + correctY + "[1], " + correctX + "[1], " +
                correctY + "[1]), nrow = 2, ncol = 2, byrow=TRUE)";
    }

    @AfterClass
    public static void after() throws Exception {
        FileRepository.getRepo().deleteFileById(correctFileId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgument4() throws Exception {
        call.runScriptToGetVector((Script) null,
                (ArrayList<DataSet>) null);
    }

    /*
    @Test
    public void testCorrectCommandToGetNumberCorrectFile() {
        try {
            Script script = new Script("", null, InputType.VECTORS, 1,
                    TypeOfReturnValue.SCALAR
            );
            ScalarResult resX = call.runScriptToGetScalar(
                    script,
                    correct);
            script = new Script("", null, InputType.VECTORS, 1, TypeOfReturnValue.SCALAR
            );
            ScalarResult resY = call.runScriptToGetScalar(
                    script,
                    correct);
            assertEquals("Scalar isn't returned correctly from FakeR",
                    new DataEntry(DataEntryType.DOUBLE, 0.),
                    resX.getValue());
            assertEquals("Scalar isn't returned correctly from FakeR",
                    new DataEntry(DataEntryType.DOUBLE, 0.),
                    resY.getValue());
        } catch (Exception e) {
            fail("Scalar isn't returned correctly from FakeR");
        }
    }

    @Test
    public void testCorrectCommandToGetPointCorrectFile() {
        try {
            Script script = new Script("", null,
                    InputType.VECTORS, 2, TypeOfReturnValue.VECTOR
            );
            VectorResult res = call.runScriptToGetVector(script, correct);
            assertTrue("Vector isn't returned correctly from FakeR",
                    res.getValue() instanceof ArrayList);
        } catch (Exception e) {
            fail("Vector isn't returned correctly from FakeR");
        }
    }

    @Test
    public void testCorrectCommandToGetPointsCorrectFile() {
        try {
            Script script = new Script("", null, InputType.VECTORS, 3,
                    TypeOfReturnValue.VECTORS
            );

            VectorsResult res = call.runScriptToGetVectors(script,
                    correct);
            assertTrue("Group of vectors aren't returned correctly from FakeR",
                    res.getValue() instanceof HashMap);
        } catch (Exception e) {
            fail("Group of vectors aren't returned correctly from FakeR");
        }
    }    */
}
