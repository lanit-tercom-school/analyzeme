package com.analyzeme.data.dataWithType;

/**
 * Types of {@link DataEntry}
 */
public enum DataEntryType {
    DOUBLE(Double.class),
    STRING(String.class),
    TIME(java.time.LocalTime.class),
    DATE(java.time.LocalDate.class),
    DATE_TIME(java.time.LocalDateTime.class);

    private Class<?> javaClass;

    /**
     * Creates {@link DataEntryType} which will store object of {@code javaClass} class
     *
     * @param javaClass - class of object to be stored
     */
    DataEntryType(Class<?> javaClass) {
        this.javaClass = javaClass;
    }

    public Class<?> getJavaClass() {
        return javaClass;
    }
}
