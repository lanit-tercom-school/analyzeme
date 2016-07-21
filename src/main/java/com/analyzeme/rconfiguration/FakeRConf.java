package com.analyzeme.rconfiguration;

import org.json.simple.JSONObject;

/**
 *
 */
public class FakeRConf implements IRConf {

    /**
     * field what save name of RConfigurations
     */
    private String name;

    /**
     * field what show is processor active
     */
    private boolean activeFlag;

    /**
     * get field name
     *
     * @return field name
     */
    public String getName() {
        return name;
    }

    /**
     * set field name
     *
     * @param name what will set as field name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * default constructor
     */
    FakeRConf() {
        name = "newFake";
        activeFlag = true;
    }

    /**
     * constructor by param flag
     *
     * @param activeFlag for setting field activeFlag
     */
    public FakeRConf(final boolean activeFlag, final String name) {
        this.name = name;
        this.activeFlag = activeFlag;
    }

    /**
     * set field flag
     *
     * @param activeFlag what will set as field activeFlag
     */
    public void setActiveFlag(final boolean activeFlag) {
        this.activeFlag = activeFlag;

    }

    /**
     * get field activeFlag
     *
     * @return value of field flag
     */
    public boolean isActive() {
        return activeFlag;
    }


    /**
     * assignment value of param to this object
     * this object will become equal to RConf
     *
     * @param RConf will be assignmented to this FakeRConf
     */
    public void assignment(final IRConf RConf) throws IllegalArgumentException {
        if (!(RConf instanceof FakeRConf)) throw new IllegalArgumentException();
        setName(RConf.getName());
        setActiveFlag(RConf.isActive());
    }

    /**
     * @return this this object as JSONObject
     */
    public JSONObject toJSONObject() {

        JSONObject obj = new JSONObject();
        obj.put("rConfType", "FakeRConf");
        obj.put("name", name);
        obj.put("activeFlag", activeFlag);
        return obj;
    }

}
