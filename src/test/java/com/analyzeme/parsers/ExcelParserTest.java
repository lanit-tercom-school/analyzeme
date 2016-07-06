package com.analyzeme.parsers;

import com.analyzeme.data.Data;
import com.analyzeme.data.DataArray;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    public void testParsing() throws IOException, InvalidFormatException {
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

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ExcelParser parser = new ExcelParser();
        DataArray<Double> dataArray = parser.parse(in);

        Assert.assertTrue(new HashSet<String>(Arrays.asList(columnTitles)).equals(dataArray.getData().get(0).getKeys()));

        for (int r = 0; r < data.size(); r++) {
            Data<Double> doubleData = dataArray.getData().get(r);
            for (int c = 0; c < columnTitles.length; c++) {
                Assert.assertEquals(data.get(r)[c], doubleData.getData().get(columnTitles[c]));
            }
        }
    }
}
