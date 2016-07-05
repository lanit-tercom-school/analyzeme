package com.analyzeme.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.analyzeme.data.DoubleData;
import com.analyzeme.data.DoubleDataArray;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

/**
 * Created by ilya on 7/5/16.
 */
public class ExcelParser {
    public DoubleDataArray parse(InputStream file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);

        int numColumns = sheet.getRow(0).getLastCellNum();
        DoubleDataArray result = new DoubleDataArray();
        for (Row row : sheet) {
            Map<String, Double> data = new HashMap<String, Double>();
            for (int i = 0; i < numColumns; i++) {
                String columnName = sheet.getRow(0).getCell(i).getStringCellValue();
                Cell cell = row.getCell(i);
                if (cell != null && (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
                        || (cell.getCellType() == Cell.CELL_TYPE_FORMULA && cell.getCachedFormulaResultType() == Cell.CELL_TYPE_NUMERIC))) {
                    data.put(columnName, cell.getNumericCellValue());
                }
            }
            if (!data.isEmpty()) {
                result.addData(new DoubleData(data));
            }
        }
        return result;
    }
}
