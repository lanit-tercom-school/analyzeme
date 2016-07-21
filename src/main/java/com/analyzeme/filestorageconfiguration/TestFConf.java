package com.analyzeme.filestorageconfiguration;

import org.json.simple.JSONObject;

/**
 *
 */
public class TestFConf implements IFileStorageConf {

    /**
     * field what save name of FileStorageConfigurations
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
    TestFConf() {
        name = "testName";
        activeFlag = true;
    }

    /**
     * constructor by param flag
     *
     * @param activeFlag for setting field activeFlag
     */
    public TestFConf(final String name,final boolean activeFlag) {
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
     * this object will become equal to FConf
     *
     * @param FConf will be assignmented to this TestFRConf
     */

    public void assignment(final IFileStorageConf FConf) throws IllegalArgumentException {
        if (!(FConf instanceof TestFConf)) throw new IllegalArgumentException();
        setName(FConf.getName());
        setActiveFlag(FConf.isActive());
    }

    /**
     * @return this this object as JSONObject
     */
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("fConfType", "TestFConf");
        obj.put("name", name);
        obj.put("activeFlag", activeFlag);
        return obj;
    }
}
