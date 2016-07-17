package com.analyzeme.data.dataWithType;

import com.analyzeme.parsers.DateAndTimeUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
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
        if (value.getClass().isAssignableFrom(DataEntryType.DOUBLE.getJavaClass())) {
            this.type = DataEntryType.DOUBLE;
        } else if ((value.getClass().isAssignableFrom(DataEntryType.TIME.getJavaClass()))) {
            this.type = DataEntryType.TIME;
        } else if (value.getClass().isAssignableFrom(DataEntryType.STRING.getJavaClass())) {
            this.type = DataEntryType.STRING;
        } else {
            throw new IllegalArgumentException("Object's type is not supported");
        }
        this.value = value;
    }

    public static DataEntry fromString(String string) {
        try {
            Double value = Double.parseDouble(string);
            return new DataEntry(DataEntryType.DOUBLE, value);
        } catch (NumberFormatException e1) {
            try {
                LocalTime value = DateAndTimeUtils.parse(string);
                return new DataEntry(DataEntryType.TIME, value);
            } catch (Exception e2) {
                return new DataEntry(DataEntryType.STRING, string);
            }
        }
    }

    public String getStringVaue() {
        return (String) value;
    }


    public Double getDoubleValue() {
        return (Double) value;
    }

    public Instant getDateValue() {
        return (Instant) value;
    }

    public DataEntryType getType() {
        return type;
    }

    @Override
    public String toString() {
        final String timeFormat = "HH:mm:ss";
        switch (type) {
            case DOUBLE:
            case STRING:
                return value.toString();
            case TIME:
                return ((LocalTime) value).format(DateTimeFormatter.ofPattern(timeFormat));
            default:
                return null;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DataEntry)) {
            return false;
        }
        if (((DataEntry)other).getType() != this.getType()) {
            return false;
        }
        switch (((DataEntry) other).type) {
            case DOUBLE:
                return Math.abs((Double) this.value - (Double) ((DataEntry) other).value) < EPS_FOR_DOUBLE;
            case STRING:
            case TIME:
                return this.value.equals(((DataEntry) other).value);
            default:
                throw new IllegalArgumentException();
        }
    }
}
