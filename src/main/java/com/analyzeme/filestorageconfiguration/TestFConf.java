package com.analyzeme.filestorageconfiguration;

import org.json.simple.JSONObject;

/**
 * Created by Кирилл Зубов on 7/14/2016.
 */
public class TestFConf implements IFileStorageConf {
    private String name;
    private boolean activeFlag;

    TestFConf() {
        name = "testName";
        activeFlag = true;
    }

    public TestFConf(final String name,final boolean activeFlag) {
        this.name = name;
        this.activeFlag = activeFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setActiveFlag(final boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public boolean isActive() {
        return activeFlag;
    }


    public void assignment(final IFileStorageConf TestFConf) throws IllegalArgumentException {
        if (!(TestFConf instanceof TestFConf)) throw new IllegalArgumentException();
        setName(TestFConf.getName());
        setActiveFlag(TestFConf.isActive());
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("fConfType", "TestFConf");
        obj.put("name", name);
        obj.put("activeFlag", activeFlag);
        return obj;
    }
}
