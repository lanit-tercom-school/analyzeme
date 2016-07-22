package com.analyzeme.analyzers.result;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JsonWriterTest {
    private static final String DOUBLE =
            "{\"type\": \"SCALAR_DOUBLE\", " +
                    "\"Data\": [{\"value\" : \"5.0\"}]}";
    private static final List<Double> LIST =
            Arrays.asList(new Double[]{5.0, 2.0});
    private static final String VECTOR =
            "{\"type\": \"VECTOR_DOUBLE\", " +
                    "\"Data\": [{\"value\": \"5.0\"}, " +
                    "{\"value\": \"2.0\"}]}";
    private static final Map<String, List<Double>> MAP =
            new HashMap<>();
    private static final String FILE =
            "{\"type\": \"VECTORS_DOUBLE\", " +
                    "\"Metadata\": {\"columnsDescription\" : " +
                    "[{\"first\":\"col_0\"},{\"second\":\"col_1\"}]}, " +
                    "\"Data\": [{\"col_1\":\"3.0\",\"col_0\":\"1.0\"}, " +
                    "{\"col_1\":\"4.0\",\"col_0\":\"2.0\"}]}";

    static {
        List<Double> first =
                Arrays.asList(new Double[]{1., 2.});
        List<Double> second =
                Arrays.asList(new Double[]{3., 4.});
        MAP.put("first", first);
        MAP.put("second", second);
    }

    @Test
    public void testDoubleWriter() {
        JsonWriter<Double> writer = new JsonWriter<>();
        assertEquals(DOUBLE, writer.toJson(5.0));
    }

    @Test
    public void testScalarResult() throws Exception {
        IResult result = new ScalarResult(5.);
        assertEquals(DOUBLE, result.toJson());
    }

    @Test
    public void testVectorWriter() {
        JsonWriter writer = new JsonWriter();
        assertEquals(VECTOR, writer.toJson(LIST));
    }

    @Test
    public void testColumnResult() throws Exception {
        IResult result = new ColumnResult(LIST);
        assertEquals(VECTOR, result.toJson());
    }

    @Test
    public void testVectorsWriter() {
        JsonWriter writer = new JsonWriter();
        assertEquals(FILE, writer.toJson(MAP));
    }

    @Test
    public void testFileResult() throws Exception {
        IResult result = new FileResult(MAP);
        assertEquals(FILE, result.toJson());
    }
}
