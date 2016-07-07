package com.analyzeme.parsers;

import com.analyzeme.data.Data;
import com.analyzeme.data.DataArray;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static java.lang.System.in;

/**
 * Created by ilya on 7/5/16.
 */
public class ExcelParserTest {
    @Test
    public void testParsing() throws IOException, InvalidFormatException, InvalidFileException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet");

        List<Double[]> data = new ArrayList<Double[]>();
        for (int i = 0; i < 10; i++) {
            data.add(new Double[]{(double) i, (double) (i * i)});
        }
        String[] columnTitles = new String[]{"x", "y"};
        Row titleRow = sheet.createRow(0);
        for (int c = 0; c < columnTitles.length; c++) {
            titleRow.createCell(c).setCellValue(columnTitles[c]);
        }
        for (int r = 0; r < data.size(); r++) {
            Row row = sheet.createRow(r + 1);
            for (int c = 0; c < columnTitles.length; c++) {
                row.createCell(c).setCellValue(data.get(r)[c]);
            }
        }

        ExcelParser parser = new ExcelParser();
        DataArray<Double> dataArray = parser.parse(createInputStreamFromWorkbook(workbook));


        Assert.assertTrue(new HashSet<String>(Arrays.asList(columnTitles)).equals(dataArray.getData().get(0).getKeys()));

        for (int r = 0; r < data.size(); r++) {
            Data<Double> doubleData = dataArray.getData().get(r);
            for (int c = 0; c < columnTitles.length; c++) {
                Assert.assertEquals(data.get(r)[c], doubleData.getData().get(columnTitles[c]));
            }
        }
    }

    @Test(expected = InvalidFileException.class)
    public void testInvalidFile() throws IOException, InvalidFileException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet");

        List<Double[]> data = new ArrayList<Double[]>();
        for (int i = 0; i < 10; i++) {
            data.add(new Double[]{(double) i, (double) (i * i)});
        }

        for (int r = 0; r < data.size(); r++) {
            Row row = sheet.createRow(r);
            for (int c = 0; c < 2; c++) {
                row.createCell(c).setCellValue(data.get(r)[c]);
            }
        }

        ExcelParser parser = new ExcelParser();
        parser.parse(createInputStreamFromWorkbook(workbook));
    }

    private InputStream createInputStreamFromWorkbook(Workbook workbook) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        return in;
    }
}
