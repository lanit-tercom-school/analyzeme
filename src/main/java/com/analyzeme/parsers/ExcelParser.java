package com.analyzeme.parsers;

import com.analyzeme.data.dataset.DataArray;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataEntryType;
import com.analyzeme.data.dataset.Data;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parses Excel file in format:
 * <pre>
 * +---+---+---+
 * |   | A | B |
 * +---+---+---+
 * | 1 | x | y |
 * | 2 | 1 | 1 |
 * | 3 | 2 | 2 |
 * +---+---+---+
 * </pre>
 */
public class ExcelParser implements IParser {

    @Override
    public DataArray parse(InputStream input) throws InvalidFileException {
        DataArray result = new DataArray();

        try {
            Workbook workbook = WorkbookFactory.create(input);
            Sheet sheet = workbook.getSheetAt(0);

            int numColumns = sheet.getRow(0).getLastCellNum();
            List<String> columnNames = new ArrayList<>();
            for (Cell titleCell : sheet.getRow(0)) {
                columnNames.add(titleCell.getStringCellValue());
            }
            boolean isTitleRow = true;
            for (Row row : sheet) {
                //We need to skip title row
                if (isTitleRow) {
                    isTitleRow = false;
                    continue;
                }
                Map<String, DataEntry> data = new HashMap<>();
                for (int c = 0; c < numColumns; c++) {
                    Cell cell = row.getCell(c);
                    data.put(columnNames.get(c), getFromCell(cell));
                }
                if (!data.isEmpty()) {
                    result.addData(new Data(data));
                }
            }
        } catch (IOException | InvalidFormatException
                | EncryptedDocumentException | IllegalArgumentException e) {
            throw new InvalidFileException("Excel file is not valid!");
        }
        return result;
    }

    private DataEntry getFromCell(Cell cell) {
        if (cell == null) {
            throw new IllegalArgumentException();
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (ExcelUtils.isCellTimeFormatted(cell)) {
                return new DataEntry(
                        DataEntryType.TIME,
                        ExcelUtils.getTimeFromCell(cell));
            } else if (ExcelUtils.isCellDateFormatted(cell)) {
                return new DataEntry(
                        DataEntryType.DATE,
                        ExcelUtils.getDateFormCell(cell));
            } else if (ExcelUtils.isCellDateTimeFormatted(cell)) {
                return new DataEntry(
                        DataEntryType.DATE_TIME,
                        ExcelUtils.getDateTimeFormCell(cell));
            } else {
                return new DataEntry(
                        DataEntryType.DOUBLE,
                        cell.getNumericCellValue());
            }

        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA &&
                cell.getCachedFormulaResultType() == Cell.CELL_TYPE_NUMERIC) {
            return new DataEntry(
                    DataEntryType.DOUBLE,
                    cell.getNumericCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return new DataEntry(
                    DataEntryType.STRING,
                    cell.getStringCellValue());
        } else {
            throw new IllegalArgumentException(
                    "Cell type not supported!");
        }
    }
}
