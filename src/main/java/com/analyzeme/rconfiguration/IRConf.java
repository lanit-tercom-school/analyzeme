package com.analyzeme.rconfiguration;

import org.json.simple.JSONObject;

/**
 * interface for configuration of r-processor
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

    /**
     * assignment value of param to this object
     * this object will become equal to RConf
     *
     * @param RConf will be assignmented to this IRConf
     */
    void assignment(IRConf RConf);

    /**
     * @return this this object as JSONObject
     */
    JSONObject toJSONObject();

}
