package com.analyzeme.rConfiguration;

import org.json.simple.JSONObject;

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

// TODO: 17.04.2016 add to UML diagram

    /**
     * assignment value of param to this object
     * this object will become equal to RConf
     *
     * @param RConf will be assignmented to this IRConf
     */
    void assignment(IRConf RConf);

    // TODO: 17.04.2016 comment + UML 
    JSONObject toJSONObject();
    
}
