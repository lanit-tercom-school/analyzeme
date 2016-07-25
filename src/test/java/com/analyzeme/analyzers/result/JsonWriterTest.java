package com.analyzeme.analyzers.result;

import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataEntryType;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JsonWriterTest {
    private static final String DOUBLE =
            "{\"type\": \"DOUBLE\", " +
                    "\"Data\": {\"value\" : \"5.0\"}}";
    private static final List<DataEntry> LIST =
            Arrays.asList(new DataEntry[]{new DataEntry(DataEntryType.DOUBLE, 5.0),
                    new DataEntry(DataEntryType.DOUBLE, 2.0)});
    private static final String VECTOR =
            "{\"type\": \"VECTOR_DOUBLE\", " +
                    "\"Data\": [{\"value\": \"5.0\"}, " +
                    "{\"value\": \"2.0\"}]}";
    private static final Map<String, List<DataEntry>> MAP =
            new HashMap<>();
    private static final String FILE =
            "{\"type\": \"VECTORS\", \"Metadata\": " +
                    "{\"columnsDescription\" : [{\"type\":\"DOUBLE\"," +
                    "\"first\":\"col_0\"},{\"type\":\"DOUBLE\"," +
                    "\"second\":\"col_1\"}]}, \"Data\": " +
                    "[{\"col_1\":\"3.0\",\"col_0\":\"1.0\"}, " +
                    "{\"col_1\":\"4.0\",\"col_0\":\"2.0\"}]}";

    static {
        List<DataEntry> first =
                Arrays.asList(new DataEntry[]{new DataEntry(DataEntryType.DOUBLE, 1.0),
                        new DataEntry(DataEntryType.DOUBLE, 2.0)});
        List<DataEntry> second =
                Arrays.asList(new DataEntry[]{new DataEntry(DataEntryType.DOUBLE, 3.0),
                        new DataEntry(DataEntryType.DOUBLE, 4.0)});
        MAP.put("first", first);
        MAP.put("second", second);
    }

    @Test
    public void testDoubleWriter() {
        JsonWriter writer = new JsonWriter();
        String res = writer.toJson(new DataEntry(DataEntryType.DOUBLE, 5.0));
        assertEquals(DOUBLE, res);
    }

    @Test
    public void testScalarResult() throws Exception {
        IResult result = new ScalarResult(
                new DataEntry(DataEntryType.DOUBLE, 5.0));
        String res = result.toJson();
        assertEquals(DOUBLE, res);
    }

    @Test
    public void testVectorWriter() {
        JsonWriter writer = new JsonWriter();
        String res = writer.toJson(LIST);
        assertEquals(VECTOR, res);
    }

    @Test
    public void testColumnResult() throws Exception {
        IResult result = new ColumnResult(LIST);
        String res = result.toJson();
        assertEquals(VECTOR, res);
    }

    @Test
    public void testVectorsWriter() {
        JsonWriter writer = new JsonWriter();
        String res = writer.toJson(MAP);
        assertEquals(FILE, res);
    }

    @Test
    public void testFileResult() throws Exception {
        IResult result = new FileResult(MAP);
        String res = result.toJson();
        assertEquals(FILE, res);
    }
}
