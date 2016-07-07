package com.analyzeme.parsers;

import com.analyzeme.data.Data;
import com.analyzeme.data.DataArray;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
