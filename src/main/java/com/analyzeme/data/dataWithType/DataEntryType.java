package com.analyzeme.data.dataWithType;

/**
 * Types of {@link DataEntry}
 */
public enum DataEntryType {
    DOUBLE(Double.class, "DOUBLE"),
    STRING(String.class, "STRING"),
    TIME(java.time.LocalTime.class, "TIME"),
    DATE(java.time.LocalDate.class, "DATE"),
    DATE_TIME(java.time.LocalDateTime.class, "DATE_TIME"),
    BOOLEAN(Boolean.class, "BOOLEAN");

    private final Class<?> javaClass;
    private final String type;

    /**
     * Creates {@link DataEntryType} which will store object of {@code javaClass} class
     *
     * @param javaClass - class of object to be stored
     */
    DataEntryType(final Class<?> javaClass, final String type) {
        this.javaClass = javaClass;
        this.type = type;
    }

    public Class<?> getJavaClass() {
        return javaClass;
    }

    public String getType() {
        return type;
    }
}
