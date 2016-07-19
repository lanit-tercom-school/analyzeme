package com.analyzeme.parsers;

import org.apache.poi.ss.usermodel.Cell;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;


/**
 * Helper class for working with Apache POI cells
 */
public class ExcelUtils {
    /**
     * Checks {@code cell} stores time
     *
     * @param cell
     * @return true if stores, false otherwise
     */
    public static boolean isCellTimeFormatted(Cell cell) {
        String[] styleCodes = new String[]{
                "HH:MM",
                "HH:MM:SS",
                "HH:MM\\ AM/PM",
                "HH:MM:SS\\ AM/PM"
        };
        return Arrays.asList(styleCodes).contains(cell.getCellStyle().getDataFormatString());
    }

    /**
     * Checks {@code cell} stores date
     *
     * @param cell
     * @return true if stores, false otherwise
     */
    public static boolean isCellDateFormatted(Cell cell) {
        String[] styleCodes = new String[]{
                "D/M/YY",
                "DD/MM/YYYY",
                "D\\ MMM\", \"YY",
                "D\\ MMMM\", \"YYYY",
                "D/\\ MMMM\\ YYYY"
        };
        return Arrays.asList(styleCodes).contains(cell.getCellStyle().getDataFormatString());
    }

    /**
     * Checks {@code cell} stores dateTime
     *
     * @param cell
     * @return true if stores, false otherwise
     */
    public static boolean isCellDateTimeFormatted(Cell cell) {
        String[] styleCodes = new String[]{
                "DD/MM/YY\\ HH:MM",
                "DD/MM/YYYY\\ HH:MM:SS",
                "MM/DD/YY\\ HH:MM\\ AM/PM"
        };
        return Arrays.asList(styleCodes).contains(cell.getCellStyle().getDataFormatString());
    }

    /**
     * Gets date from cell if it is stores it
     *
     * @param cell
     * @return {@link LocalDate} time representation
     */
    public static LocalDate getDateFormCell(Cell cell) {
        return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Gets dateTime from cell if it is stores it
     *
     * @param cell
     * @return {@link LocalDateTime} time representation
     */
    public static LocalDateTime getDateTimeFormCell(Cell cell) {
        return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Gets time from cell if it is stores it
     *
     * @param cell
     * @return {@link LocalTime} time representation
     */
    public static LocalTime getTimeFromCell(Cell cell) {
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
