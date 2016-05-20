package com.analyzeme.rConfiguration;

import org.json.simple.JSONObject;

/**
 * Class for saving setting of Renjin processor
 */
public class RenjinConf implements IRConf {
    /**
     * field what save name of RConfiguration
     */
    private String name;
    /**
     * field what show is processor active
     */
    private boolean activeFlag;

    /**
     * default constructor
     */
    RenjinConf() {
        name = "newRenjin";
        activeFlag = true;
    }

    /**
     * constructor by param flag
     *
     * @param activeFlag for setting field activeFlag
     * @param name       for setting field name
     */
    public RenjinConf(boolean activeFlag, String name) {
        this.name = name;
        this.activeFlag = activeFlag;
    }

    /**
     * @param activeFlag what will set as field activeFlag
     */
    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * @param name what will set as field name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get field name
     *
     * @return field name
     */
    public String getName() {
        return name;
    }

    /**
     * get is RProcessor active?
     *
     * @return field activeFlag
     */
    public boolean isActive() {
        return activeFlag;
    }

    /**
     * assignment value of param to this object
     * this object will become equal to RConf
     *
     * @param RConf will be assignmented to this RenjinConf
     */
    public void assignment(IRConf RConf) {
        if (!(RConf instanceof RenjinConf)) throw new IllegalArgumentException();
        setName(RConf.getName());
        setActiveFlag(RConf.isActive());
    }

    /**
     * @return this this object as JSONObject
     */
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("rConfType", "RenjinConf");
        obj.put("name", name);
        obj.put("activeFlag", activeFlag);
        return obj;
    }

}
