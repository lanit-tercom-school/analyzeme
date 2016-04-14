package com.analyzeme.rConfiguration;

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
    public void setName(String name) {
        this.name = name;
    }

    /**
     * default constructor
     * <p>
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
    public RserveConf(boolean activeFlag, String host, String port, String name) {
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
    @Override
    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * get field flag
     *
     * @return value of field flag
     */
    @Override
    public boolean isActive() {
        return activeFlag;
    }

    /**
     * set field flag
     *
     * @param port what will set as field port
     */
    public void setPort(String port) {
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
    public void setHost(String host) {
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
}
