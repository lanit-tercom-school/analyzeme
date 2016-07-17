package com.analyzeme.data.dataWithType;

/**
 * Created by ilya on 7/14/16.
 */
public enum DataEntryType {
    DOUBLE(Double.class),
    STRING(String.class),
    TIME(java.time.LocalTime.class);

    private Class<?> javaClass;

    DataEntryType(Class<?> javaClass) {
        this.javaClass = javaClass;
    }

    public Class<?> getJavaClass() {
        return javaClass;
    }
}
