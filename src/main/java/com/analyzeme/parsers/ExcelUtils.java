package com.analyzeme.parsers;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.stream.IntStream;


/**
 * Created by ilya on 7/17/16.
 */
public class ExcelUtils {
    public static boolean isCellTimeFormatted(Cell cell) {
        String[] styleCodes = new String[]{
                "HH:MM",
                "HH:MM:SS",
                "HH:MM\\ AM/PM",
                "HH:MM:SS\\ AM/PM"
        };
        return Arrays.asList(styleCodes).contains(cell.getCellStyle().getDataFormatString());
    }

    public static boolean isCellDateFormated(Cell cell) {
        String[] styleCodes = new String[]{
                "D/M/YY",
                "DD/MM/YYYY",
                "D\\ MMM\", \"YY",
                "D\\ MMMM\", \"YYYY",
                "D/\\ MMMM\\ YYYY"
        };
        return Arrays.asList(styleCodes).contains(cell.getCellStyle().getDataFormatString());
    }

    public static boolean isCellDateTimeFormatted(Cell cell) {
        String[] styleCodes = new String[]{
                "DD/MM/YY\\ HH:MM",
                "DD/MM/YYYY\\ HH:MM:SS",
                "MM/DD/YY\\ HH:MM\\ AM/PM"
        };
        return Arrays.asList(styleCodes).contains(cell.getCellStyle().getDataFormatString());
    }
    public static LocalDate getDateFormCell(Cell cell) {
        return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    public static LocalDateTime getDateTimeFormCell(Cell cell) {
        return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalTime getTimeFromExcelCell(Cell cell) {
        if (cell.getNumericCellValue() < 0 || cell.getNumericCellValue() > 1) {
            throw new IllegalArgumentException("Cell's numeric value should be from 0 to 1");
        }
        int allSeconds = (int) Math.round(cell.getNumericCellValue() * 24 * 60 * 60);
        int hours = allSeconds / (60 * 60);
        int minutes = (allSeconds - (hours * 60 * 60)) / 60;
        int seconds = allSeconds % 60;
        return LocalTime.of(hours, minutes, seconds);
    }



}
