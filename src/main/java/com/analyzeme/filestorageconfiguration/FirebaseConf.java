package com.analyzeme.filestorageconfiguration;

import org.json.simple.JSONObject;

/**
 * Created by Kirill Zubov on 7/7/2016.
 */
public class FirebaseConf implements IFileStorageConf {
    private String name;
    private boolean activeFlag;
    private String serviceAccount;
    private String databaseUrl;

    public FirebaseConf() {
        name = "AnalyzeMeDatabase";
        activeFlag = true;
        serviceAccount = null;
        databaseUrl = null;
    }

    public FirebaseConf(final String name,final boolean activeFlag,final String serviceAccount,final String databaseUrl) {
        this.name = name;
        this.activeFlag = activeFlag;
        this.serviceAccount = serviceAccount;
        this.databaseUrl = databaseUrl;
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


    public String getServiceAccount() {
        return serviceAccount;
    }

    public void setServiceAccount(String serviceAccount) {
        this.serviceAccount = serviceAccount;
    }

    public String getDataBaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    @Override
    public void assignment(IFileStorageConf FirebaseConf) {
        if (!(FirebaseConf instanceof FirebaseConf)) throw new IllegalArgumentException();
        FirebaseConf FirebaseConf1 = (FirebaseConf) FirebaseConf;
        setName(FirebaseConf1.getName());
        setActiveFlag(FirebaseConf1.isActive());
        setServiceAccount(FirebaseConf1.getServiceAccount());
        setDatabaseUrl(FirebaseConf1.getDataBaseUrl());

    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("fConfType", "FirebaseConf");
        obj.put("name", name);
        obj.put("activeFlag", activeFlag);
        obj.put("serviceAccount", serviceAccount);
        return obj;
    }
}
