package com.analyzeme.rConfiguration;

/**
 * interface for configuration of R-processor
 */
public interface IRConf {
    /**
     * get field name
     *
     * @return name of RConfiguration
     */
    String getName();

    /**
     * set field name
     *
     * @param name what will set as field name
     */
    void setName(String name);

    /**
     * set field activeFlag
     *
     * @param activeFlag what will set as field activeFlag
     */
    void setActiveFlag(boolean activeFlag);

    /**
     * get field flag
     *
     * @return value of field flag
     */
    boolean isActive();

}
