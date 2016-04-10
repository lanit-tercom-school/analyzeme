package com.analyzeme.rConfiguration;

/**
 * interface for configuration of R-processor
 */
public interface IRConfiguration {

    String getName();
    void setName(String name);

    /**
     *set field flag
     * @param flag what will set as field flag
     */
    void setFlag(boolean flag);

    /**
     * get field flag
     * @return value of field flag
     */
    boolean getFlag();

}
