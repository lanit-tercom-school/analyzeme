package com.analyzeme.parsers;

import com.analyzeme.data.dataset.Data;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataArray;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class ExcelParserTest {

    @SuppressWarnings("Duplicates")
    @Test
    public void testFileWithDifferentTypes()
            throws FileNotFoundException, InvalidFileException {
        final String filepath = "/test_data/with_types.xlsx";
        FileInputStream file = new FileInputStream(
                new File(this.getClass().getResource(filepath).getFile()));
        ExcelParser parser = new ExcelParser();
        DataArray result = parser.parse(
                file);

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
            put("some_time", new DataEntry(
                    LocalTime.of(7, 40)));
            put("some_date", new DataEntry(
                    LocalDate.of(2010, Month.NOVEMBER, 1)));
            put("some_datetime", new DataEntry(
                    LocalDateTime.of(
                            2010, Month.NOVEMBER, 1, 7, 40)));
        }}));

        Assert.assertEquals(expected, result);
    }
}
