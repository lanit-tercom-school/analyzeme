package com.analyzeme.data.dataWithType;

import com.analyzeme.parsers.DateAndTimeUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Created by ilya on 7/14/16.
 */
public class DataEntry {
    private static final double EPS_FOR_DOUBLE = .0001;
    private DataEntryType type;
    private Object value;

    public DataEntry(DataEntryType type, Object value) {
        if (!value.getClass().isAssignableFrom(type.getJavaClass())) {
            throw new IllegalArgumentException("DataEntryType's class and object's class should be equal");
        }
        this.type = type;
        this.value = value;
    }

    public DataEntry(Object value) {
        this.value = value;

        for (DataEntryType type : DataEntryType.values()) {
            if (value.getClass().isAssignableFrom(type.getJavaClass())) {
                this.type = type;
                return;
            }
        }
        throw new IllegalArgumentException("Object's type is not supported");
    }

    private static boolean isValidDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static DataEntry fromString(String value) {
        if (isValidDouble(value)) {
            return new DataEntry(Double.parseDouble(value));
        } else if (DateAndTimeUtils.isValidTime(value)) {
            return new DataEntry(DateAndTimeUtils.parseTime(value));
        } else if (DateAndTimeUtils.isValidDate(value)) {
            return new DataEntry(DateAndTimeUtils.parseDate(value));
        } else if (DateAndTimeUtils.isValidDateTime(value)) {
            return new DataEntry(DateAndTimeUtils.parseDateTime(value));
        } else {
            return new DataEntry(value);
        }
    }

    public String getStringVaue() {
        return (String) value;
    }

    public Double getDoubleValue() {
        return (Double) value;
    }

    public LocalTime getTimeValue() {
        return (LocalTime) value;
    }

    public LocalDate getDateValue() {
        return (LocalDate) value;
    }

    public LocalDateTime getDateTimeValue() {
        return (LocalDateTime) value;
    }

    public DataEntryType getType() {
        return type;
    }

    @Override
    public String toString() {
        final String timeFormat = "HH:mm:ss";
        final String dateFormat = "dd-MM-yyyy";
        final String dateTimeFormat = "dd-MM-yyyy HH:mm:ss";
        switch (type) {
            case DOUBLE:
            case STRING:
                return value.toString();
            case TIME:
                return ((LocalTime) value).format(DateTimeFormatter.ofPattern(timeFormat));
            case DATE:
                return ((LocalDate) value).format(DateTimeFormatter.ofPattern(dateFormat));
            case DATE_TIME:
                return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern(dateTimeFormat));
            default:
                return null;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DataEntry)) {
            return false;
        }
        if (((DataEntry) other).getType() != this.getType()) {
            return false;
        }
        switch (((DataEntry) other).type) {
            case DOUBLE:
                return Math.abs((Double) this.value - (Double) ((DataEntry) other).value) < EPS_FOR_DOUBLE;
            case STRING:
            case TIME:
            case DATE:
            case DATE_TIME:
                return this.value.equals(((DataEntry) other).value);
            default:
                throw new IllegalArgumentException();
        }
    }
}
