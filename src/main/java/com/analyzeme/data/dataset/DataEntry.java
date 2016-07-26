package com.analyzeme.data.dataset;

import com.analyzeme.parsers.DateAndTimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * Represents one cell in data matrix
 */
public class DataEntry {
    private static final double EPS_FOR_DOUBLE = .0001;
    private final DataEntryType type;
    private final Object value;

    /**
     * Creates {@link DataEntry} using given value and given type
     *
     * @param type  type of object
     * @param value object to be stored
     * @throws IllegalArgumentException if  {@code object} type is not equals to {@code type}
     */
    public DataEntry(DataEntryType type, Object value) {
        if (!value.getClass().isAssignableFrom(type.getJavaClass())) {
            throw new IllegalArgumentException("DataEntryType's class and object's class should be equal");
        }
        this.type = type;
        this.value = value;
    }

    /**
     * Creates {@link DataEntry} using given value and automatically determines its type
     *
     * @param value - object to be stored
     * @throws IllegalArgumentException if type of {@code object} is not supported
     */
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

    /**
     * Constructs new {@link DataEntry}. Automatically determines its type by given string
     *
     * @param value string representation of object
     * @return created {@link DataEntry}
     */
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

    public String getStringValue() throws IllegalStateException {
        if (type != DataEntryType.STRING) {
            throw new IllegalStateException("This value is not a string");
        }
        return (String) value;
    }

    public Double getDoubleValue() throws IllegalStateException {
        if (type != DataEntryType.DOUBLE) {
            throw new IllegalStateException("This value is not a double");
        }
        return (Double) value;
    }

    public LocalTime getTimeValue() throws IllegalStateException {
        if (type != DataEntryType.TIME) {
            throw new IllegalStateException("This value is not a time");
        }
        return (LocalTime) value;
    }

    public LocalDate getDateValue() throws IllegalStateException {
        if (type != DataEntryType.DATE) {
            throw new IllegalStateException("This value is not a date");
        }
        return (LocalDate) value;
    }

    public LocalDateTime getDateTimeValue() throws IllegalStateException {
        if (type != DataEntryType.DATE_TIME) {
            throw new IllegalStateException("This value is not a date/time");
        }
        return (LocalDateTime) value;
    }

    public Boolean getBooleanValue() throws IllegalStateException {
        if (type != DataEntryType.BOOLEAN) {
            throw new IllegalStateException("This value is not a boolean");
        }
        return (Boolean) value;
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
            case BOOLEAN:
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
