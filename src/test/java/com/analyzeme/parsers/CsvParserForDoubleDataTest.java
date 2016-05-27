package com.analyzeme.parsers;

import com.analyzeme.data.DoubleDataArray;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by lagroffe on 27.05.2016 19:07
 */
public class CsvParserForDoubleDataTest {

    @Test
    public void testSimple() {
        List<Double> x = new ArrayList<Double>();
        x.add(1.0); x.add(1.23);
        List<Double> y = new ArrayList<Double>();
        y.add(15.0); y.add(43.2);
        StringBuilder example = new StringBuilder();
        example.append("x, y \n");
        example.append(x.get(0)); example.append(',');
        example.append(y.get(0)); example.append('\n');
        example.append(x.get(1)); example.append(',');
        example.append(y.get(1)); example.append('\n');

        InputStream is = new ByteArrayInputStream(example.toString().getBytes());
        DoubleDataArray result = CsvParserForDoubleData.parse(is);
        assertTrue(result.getByKey("x").equals(x));
        assertTrue(result.getByKey("y").equals(y));
    }

    @Test
    public void testLonger() {
        List<Double> x = new ArrayList<Double>();
        x.add(1.0); x.add(1.23); x.add(4.21444444444415);
        List<Double> y = new ArrayList<Double>();
        y.add(15.0); y.add(43.2); y.add(0.0);
        List<Double> miles = new ArrayList<Double>();
        miles.add(42.6); miles.add(4.0); miles.add(0.32);
        StringBuilder example = new StringBuilder();
        example.append("x, y, miles \n");
        example.append(x.get(0)); example.append(',');
        example.append(y.get(0)); example.append(',');
        example.append(miles.get(0)); example.append('\n');
        example.append(x.get(1)); example.append(',');
        example.append(y.get(1)); example.append(',');
        example.append(miles.get(1));  example.append('\n');
        example.append(x.get(2)); example.append(',');
        example.append(y.get(2)); example.append(',');
        example.append(miles.get(2));  example.append('\n');

        InputStream is = new ByteArrayInputStream(example.toString().getBytes());
        DoubleDataArray result = CsvParserForDoubleData.parse(is);
        assertTrue(result.getByKey("x").equals(x));
        assertTrue(result.getByKey("y").equals(y));
        assertTrue(result.getByKey("miles").equals(miles));
    }

    @Test
    public void testNotFull() {
        List<Double> x = new ArrayList<Double>();
        x.add(1.0); x.add(1.0);
        StringBuilder s = new StringBuilder();
        s.append("x, y \n");
        s.append(x.get(0)); s.append("\n"); s.append(x.get(1));
        InputStream is = new ByteArrayInputStream(s.toString().getBytes());
        DoubleDataArray result = CsvParserForDoubleData.parse(is);
        assertTrue(result.getByKey("x").equals(x));
    }


    @Test(expected = NullPointerException.class)
    public void testIncorrect() {
        String example3 = "x, y \n 1 \n ,3";
        InputStream is = new ByteArrayInputStream(example3.getBytes());
        CsvParserForDoubleData.parse(is);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        CsvParserForDoubleData.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() {
        String example3 = "";
        InputStream is = new ByteArrayInputStream(example3.getBytes());
        CsvParserForDoubleData.parse(is);
    }
}
