package com.analyzeme.parsers;

import com.analyzeme.data.dataset.Data;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataArray;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashMap;

public class CsvParserTest {
    @Test(expected = NullPointerException.class)
    public void testIncorrect() throws InvalidFileException {
        String example3 = "x, y \n 1 \n ,3";
        InputStream is = new ByteArrayInputStream(
                example3.getBytes());
        new CsvParser().parse(is);
    }

    @Test(expected = InvalidFileException.class)
    public void testNull() throws InvalidFileException {
        new CsvParser().parse(null);
    }

    @Test(expected = InvalidFileException.class)
    public void testEmpty() throws InvalidFileException {
        String example3 = "";
        InputStream is = new ByteArrayInputStream(
                example3.getBytes());
        new CsvParser().parse(is);
    }

    @Test
    public void testFileWithDifferentTypes() throws
            FileNotFoundException, InvalidFileException {
        final String filepath = "/test_data/with_types.csv";
        FileInputStream file = new FileInputStream(
                new File(this.getClass()
                        .getResource(filepath).getFile()));
        CsvParser parser = new CsvParser();
        DataArray result = parser.parse(file);

        DataArray expected = new DataArray();
        expected.addData(new Data(
                new HashMap<String, DataEntry>() {{
                    put("some_double", new DataEntry(1d));
                    put("some_string", new DataEntry("hello"));
                    put("some_time", new DataEntry(
                            LocalTime.of(10, 30, 10)));
                    put("some_date", new DataEntry(
                            LocalDate.of(2010, Month.OCTOBER, 10)));
                    put("some_datetime", new DataEntry(
                            LocalDateTime.of(
                                    2010, Month.OCTOBER, 10, 10, 30, 10)));
                }}));
        expected.addData(new Data(
                new HashMap<String, DataEntry>() {{
                    put("some_double", new DataEntry(2d));
                    put("some_string", new DataEntry("bye"));
                    put("some_time", new DataEntry(LocalTime.of(7, 40)));
                    put("some_date", new DataEntry(
                            LocalDate.of(2010, Month.NOVEMBER, 1)));
                    put("some_datetime", new DataEntry(
                            LocalDateTime.of(
                                    2010, Month.NOVEMBER, 1, 7, 40)));
                }}));

        Assert.assertEquals(expected, result);
    }
}
