package com.analyzeme.rConfiguration;

/**
 * interface for configuration of R-processor
 */
public interface IRConf {
    // TODO: 14.04.2016 more comments
    String getName();
    void setName(String name);

    /**
     *set field flag
     * @param flag what will set as field flag
     */
    void setActiveFlag(boolean flag);

    /**
     * get field flag
     * @return value of field flag
     */
    boolean isActive();

}
