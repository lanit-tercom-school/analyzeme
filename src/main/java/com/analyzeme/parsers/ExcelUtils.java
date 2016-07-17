package com.analyzeme.parsers;

import org.apache.poi.ss.usermodel.Cell;

import java.time.LocalTime;
import java.util.stream.IntStream;


/**
 * Created by ilya on 7/17/16.
 */
public class ExcelUtils {
    public static boolean isCellTimeFormatted(Cell cell) {
        int[] styleCodes = new int[]{165, 167, 168, 169};
        return IntStream.of(styleCodes).anyMatch(x -> x == cell.getCellStyle().getDataFormat());
    }

    public static LocalTime getTimeFromExcelCell(Cell cell) {
        int allSeconds = (int) Math.round (cell.getNumericCellValue() * 24 * 60 * 60);
        int hours = allSeconds / (60 * 60);
        int minutes = (allSeconds - (hours * 60 * 60)) / 60;
        int seconds = allSeconds % 60;
        return LocalTime.of(hours, minutes, seconds);
    }
}
