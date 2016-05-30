package com.analyzeme.rconfiguration;

import org.json.simple.JSONObject;

/**
 * class saving configuration of RServe
 */
public class RserveConf implements IRConf {
    /**
     * field for saving name of RConfigurations
     */
    private String name;
    /**
     * field what show is processor active
     */
    private boolean activeFlag;
    /**
     * field for saving host
     */
    private String host;
    /**
     * field for saving port
     */
    private String port;

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
     * field host and port become null
     */
    public RserveConf() {
        name = "newRServe";
        activeFlag = true;
        host = null;
        port = null;
    }

    /**
     * constructor by param flag,host,port
     *
     * @param activeFlag for setting field activeFlag
     * @param host       for setting field host
     * @param port       for setting field port
     */
    public RserveConf(final boolean activeFlag, final String host,
                      final String port, final String name) {
        this.name = name;
        this.activeFlag = activeFlag;
        this.host = host;
        this.port = port;
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
     * set field flag
     *
     * @param port what will set as field port
     */
    public void setPort(final String port) {
        this.port = port;
    }

    /**
     * get field flag
     *
     * @return value of field port
     */
    public String getPort() {
        return port;
    }

    /**
     * set field flag
     *
     * @param host what will set as field host
     */
    public void setHost(final String host) {
        this.host = host;
    }

    /**
     * get field flag
     *
     * @return value of field host
     */
    public String getHost() {
        return host;
    }


    /**
     * assignment value of param to this object
     * this object will become equal to RConf
     *
     * @param RConf will be assignmented to this RserveConf
     */
    public void assignment(final IRConf RConf) {
        if (!(RConf instanceof RserveConf)) throw new IllegalArgumentException();
        RserveConf RConf1 = (RserveConf) RConf;
        setName(RConf1.getName());
        setActiveFlag(RConf1.isActive());
        setPort(RConf1.getPort());
        setHost(RConf1.getHost());
    }

    /**
     * @return this this object as JSONObject
     */
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("rConfType", "RserveConf");
        obj.put("name", name);
        obj.put("activeFlag", activeFlag);
        obj.put("host", host);
        obj.put("port", port);
        return obj;
    }
}
