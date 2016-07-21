package com.analyzeme.filestorageconfiguration;

import org.json.simple.JSONObject;

/**
 * class saving configuration of Firebase
 */
public class FirebaseConf implements IFileStorageConf {

    /**
     * field for saving name of FileStorageConfigurations
     */
    private String name;
    /**
     * field what show is processor active
     */
    private boolean activeFlag;
    /**
     * field for saving serviceAccount
     */
    private String serviceAccount;
    /**
     * field for saving databaseUrl
     */
    private String databaseUrl;

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
    public void setName(String name) {
        this.name = name;
    }

    /**
     * default constructor
     * field serviceAccount and databaseUrl become null
     */
    FirebaseConf() {
        name = "AnalyzeMeDatabase";
        activeFlag = true;
        serviceAccount = null;
        databaseUrl = null;
    }

    /**
     * constructor by param flag,host,port
     *
     * @param activeFlag for setting field activeFlag
     * @param serviceAccount    for setting field host
     * @param databaseUrl       for setting field port
     */
    public FirebaseConf(final String name,final boolean activeFlag,final String serviceAccount,final String databaseUrl) {
        this.name = name;
        this.activeFlag = activeFlag;
        this.serviceAccount = serviceAccount;
        this.databaseUrl = databaseUrl;
    }

    /**
     * set field flag
     *
     * @param activeFlag what will set as field flag
     */
    public void setActiveFlag(final boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * get field flag
     *
     * @return value of field flag
     */
    public boolean isActive() {
        return activeFlag;
    }

    /**
     * get field flag
     *
     * @return value of field serviceAccount
     */
    public String getServiceAccount() {
        return serviceAccount;
    }

    /**
     * set field flag
     *
     * @param serviceAccount what will set as field port
     */
    public void setServiceAccount(String serviceAccount) {
        this.serviceAccount = serviceAccount;
    }

    /**
     * get field flag
     *
     * @return value of field databaseUrl
     */
    public String getDataBaseUrl() {
        return databaseUrl;
    }

    /**
     * set field flag
     *
     * @param databaseUrl what will set as field host
     */
    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    /**
     * assignment value of param to this object
     * this object will become equal to RConf
     *
     * @param FConf will be assignmented to this FirebaseConf
     */
    @Override
    public void assignment(IFileStorageConf FConf) {
        if (!(FConf instanceof FirebaseConf)) throw new IllegalArgumentException();
        FirebaseConf FirebaseConf1 = (FirebaseConf) FConf;
        setName(FirebaseConf1.getName());
        setActiveFlag(FirebaseConf1.isActive());
        setServiceAccount(FirebaseConf1.getServiceAccount());
        setDatabaseUrl(FirebaseConf1.getDataBaseUrl());

    }

    /**
     * @return this this object as JSONObject
     */
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("fConfType", "FirebaseConf");
        obj.put("name", name);
        obj.put("activeFlag", activeFlag);
        obj.put("serviceAccount", serviceAccount);
        return obj;
    }
}
