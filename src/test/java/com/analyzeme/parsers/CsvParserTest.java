package com.analyzeme.parsers;

import com.analyzeme.data.DataArray;
import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataWithType;
import com.analyzeme.data.dataWithType.DataWithTypeArray;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by lagroffe on 27.05.2016 19:07
 */
public class CsvParserTest {

    @Test
    public void testSimple() throws InvalidFileException {
        List<Double> x = new ArrayList<Double>();
        x.add(1.0);
        x.add(1.23);
        List<Double> y = new ArrayList<Double>();
        y.add(15.0);
        y.add(43.2);
        StringBuilder example = new StringBuilder();
        example.append("x, y \n");
        example.append(x.get(0));
        example.append(',');
        example.append(y.get(0));
        example.append('\n');
        example.append(x.get(1));
        example.append(',');
        example.append(y.get(1));
        example.append('\n');

        InputStream is = new ByteArrayInputStream(example.toString().getBytes());
        DataArray result = new CsvParser().parse(is);
        assertTrue(result.getByKey("x").equals(x));
        assertTrue(result.getByKey("y").equals(y));
    }

    @Test
    public void testLonger() throws InvalidFileException {
        List<Double> x = new ArrayList<Double>();
        x.add(1.0);
        x.add(1.23);
        x.add(4.21444444444415);
        List<Double> y = new ArrayList<Double>();
        y.add(15.0);
        y.add(43.2);
        y.add(0.0);
        List<Double> miles = new ArrayList<Double>();
        miles.add(42.6);
        miles.add(4.0);
        miles.add(0.32);
        StringBuilder example = new StringBuilder();
        example.append("x, y, miles \n");
        example.append(x.get(0));
        example.append(',');
        example.append(y.get(0));
        example.append(',');
        example.append(miles.get(0));
        example.append('\n');
        example.append(x.get(1));
        example.append(',');
        example.append(y.get(1));
        example.append(',');
        example.append(miles.get(1));
        example.append('\n');
        example.append(x.get(2));
        example.append(',');
        example.append(y.get(2));
        example.append(',');
        example.append(miles.get(2));
        example.append('\n');

        InputStream is = new ByteArrayInputStream(example.toString().getBytes());
        DataArray result = new CsvParser().parse(is);
        assertTrue(result.getByKey("x").equals(x));
        assertTrue(result.getByKey("y").equals(y));
        assertTrue(result.getByKey("miles").equals(miles));
    }

    @Test
    public void testNotFull() throws InvalidFileException {
        List<Double> x = new ArrayList<Double>();
        x.add(1.0);
        x.add(1.0);
        StringBuilder s = new StringBuilder();
        s.append("x, y \n");
        s.append(x.get(0));
        s.append("\n");
        s.append(x.get(1));
        InputStream is = new ByteArrayInputStream(s.toString().getBytes());
        DataArray result = new CsvParser().parse(is);
        assertTrue(result.getByKey("x").equals(x));
    }


    @Test(expected = NullPointerException.class)
    public void testIncorrect() throws InvalidFileException {
        String example3 = "x, y \n 1 \n ,3";
        InputStream is = new ByteArrayInputStream(example3.getBytes());
        new CsvParser().parse(is);
    }

    @Test(expected = InvalidFileException.class)
    public void testNull() throws InvalidFileException {
        new CsvParser().parse(null);
    }

    @Test(expected = InvalidFileException.class)
    public void testEmpty() throws InvalidFileException {
        String example3 = "";
        InputStream is = new ByteArrayInputStream(example3.getBytes());
        new CsvParser().parse(is);
    }

    @Test
    public void testFileWithDifferentTypes() throws FileNotFoundException, InvalidFileException {
        final String filepath = "/test_data/with_types.csv";
        FileInputStream file = new FileInputStream(new File(this.getClass().getResource(filepath).getFile()));
        CsvParser parser = new CsvParser();
        DataWithTypeArray result = parser.parseWithType(file);

        DataWithTypeArray expected = new DataWithTypeArray();
        expected.addData(new DataWithType(new HashMap<String, DataEntry>() {{
            put("some_double", new DataEntry(1d));
            put("some_string", new DataEntry("hello"));
            put("some_time", new DataEntry(LocalTime.of(10, 30, 10)));
        }}));
        expected.addData(new DataWithType(new HashMap<String, DataEntry>() {{
            put("some_double", new DataEntry(2d));
            put("some_string", new DataEntry("bye"));
            put("some_time", new DataEntry(LocalTime.of(7, 40)));
        }}));

        Assert.assertEquals(expected, result);
    }
}
