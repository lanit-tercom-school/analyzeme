package com.analyzeme.parsers;

import java.io.InputStream;
import java.util.*;

import com.analyzeme.data.Data;
import com.analyzeme.data.DataArray;
import org.apache.poi.ss.usermodel.*;

/**
 * Created by ilya on 7/5/16.
 */
public class ExcelParser implements IParser {
    public DataArray<Double> parse(InputStream input) throws InvalidFileException {// Now works only with doubles
        DataArray<Double> result = new DataArray<Double>();

        try {
            Workbook workbook = WorkbookFactory.create(input);
            Sheet sheet = workbook.getSheetAt(0);

            int numColumns = sheet.getRow(0).getLastCellNum();
            List<String> columnNames = new ArrayList<String>();
            for (Cell titleCell : sheet.getRow(0)) {
                columnNames.add(titleCell.getStringCellValue());
            }
            for (Row row : sheet) {
                Map<String, Double> data = new HashMap<String, Double>();
                for (int c = 0; c < numColumns; c++) {
                    Cell cell = row.getCell(c);
                    Boolean isNumeric = cell != null && (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
                            || (cell.getCellType() == Cell.CELL_TYPE_FORMULA && cell.getCachedFormulaResultType() == Cell.CELL_TYPE_NUMERIC));
                    if (isNumeric) {
                        data.put(columnNames.get(c), cell.getNumericCellValue());
                    }
                }
                if (!data.isEmpty()) {
                    result.addData(new Data<Double>(data));
                }
            }
        }catch (Exception e) {
            throw new InvalidFileException("Excel file is not valid!");
        }
        return result;
    }
}
