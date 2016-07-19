package com.analyzeme.filestorageconfiguration;

import org.json.simple.JSONObject;

/**
 * Created by Кирилл Зубов on 7/14/2016.
 */
public class TestFConf implements IFileStorageConf {
    private String name;
    private boolean activeFlag;

    public TestFConf() {
        name = "testName";
        activeFlag = true;
    }

    public TestFConf(String name, boolean activeFlag) {
        this.name = name;
        this.activeFlag = activeFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActiveFlag(final boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public boolean isActive() {
        return activeFlag;
    }


    public void assignment(IFileStorageConf FirebaseConfiguration) {
        if (!(FirebaseConfiguration instanceof FirebaseConf)) throw new IllegalArgumentException();
        FirebaseConf FileS = (FirebaseConf) FirebaseConfiguration;
        setName(FileS.getName());
        setActiveFlag(FileS.isActive());
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("fConfType", "FirebaseConf");
        obj.put("name", name);
        obj.put("activeFlag", activeFlag);
        return obj;
    }
}
