package com.analyzeme.parsers;

import com.analyzeme.data.Data;
import com.analyzeme.data.DataArray;
import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataWithType;
import com.analyzeme.data.dataWithType.DataWithTypeArray;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * Created by ilya on 7/5/16.
 */
public class ExcelParserTest {

    @Test
    public void testParsing1() throws IOException, InvalidFormatException, InvalidFileException {
        List<Double[]> data = new ArrayList<Double[]>();
        for (int i = 1; i <= 10; i++) {
            data.add(new Double[]{(double) i, (double) (i * i)});
        }
        String[] columnTitles = new String[]{"x", "y"};
        testExcelFile("/test_data/1.xlsx", data, columnTitles, 2);
    }

    @Test
    public void testParsing2() throws IOException, InvalidFormatException, InvalidFileException {
        List<Double[]> data = new ArrayList<Double[]>();
        for (int i = 1; i <= 10; i++) {
            data.add(new Double[]{(double) i, (double) (i * i)});
        }
        String[] columnTitles = new String[]{"x", "y"};
        testExcelFile("/test_data/2.xls", data, columnTitles, 2);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void testFileWithDifferentTypes() throws FileNotFoundException, InvalidFileException {
        final String filepath = "/test_data/with_types.xlsx";
        FileInputStream file = new FileInputStream(new File(this.getClass().getResource(filepath).getFile()));
        ExcelParser parser = new ExcelParser();
        DataWithTypeArray result = parser.parseWithType(file);

        DataWithTypeArray expected = new DataWithTypeArray();
        expected.addData(new DataWithType(new HashMap<String, DataEntry>() {{
            put("some_double", new DataEntry(1d));
            put("some_string", new DataEntry("hello"));
            put("some_time", new DataEntry(LocalTime.of(10, 30, 10)));
            put("some_date", new DataEntry(LocalDate.of(2010, Month.OCTOBER, 10)));
            put("some_datetime", new DataEntry(LocalDateTime.of(2010, Month.OCTOBER, 10, 10, 30, 10)));
        }}));
        expected.addData(new DataWithType(new HashMap<String, DataEntry>() {{
            put("some_double", new DataEntry(2d));
            put("some_string", new DataEntry("bye"));
            put("some_time", new DataEntry(LocalTime.of(7, 40)));
            put("some_date", new DataEntry(LocalDate.of(2010, Month.NOVEMBER, 1)));
            put("some_datetime", new DataEntry(LocalDateTime.of(2010, Month.NOVEMBER, 1, 7, 40)));
        }}));

        Assert.assertEquals(expected, result);
    }

    @Deprecated
    private void testExcelFile(String filepath, List<Double[]> data, String[] columnTitles, int rowSize)
            throws FileNotFoundException, InvalidFileException {
        FileInputStream file = new FileInputStream(new File(this.getClass().getResource(filepath).getFile()));
        ExcelParser parser = new ExcelParser();
        DataArray<Double> dataArray = parser.parse(file);
        Assert.assertTrue(new HashSet<String>(Arrays.asList(columnTitles)).equals(dataArray.getData().get(0).getKeys()));

        for (int r = 0; r < data.size(); r++) {
            Data<Double> doubleData = dataArray.getData().get(r);
            for (int c = 0; c < rowSize; c++) {
                Assert.assertEquals(data.get(r)[c], doubleData.getData().get(columnTitles[c]));
            }
        }
    }
}
