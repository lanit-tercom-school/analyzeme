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

    // TODO: 17.04.2016 add to UML diagram
    // TODO: 17.04.2016  проверять RConf на валидность
    /**
     *
     * assignment value of param to this object
     * this object will become equal to RConf
     * @param RConf will be assignmented to this RserveConf
     */
    @Override
    public void assignment(IRConf RConf){
        RserveConf RConf1=(RserveConf) RConf;
        setName(RConf1.getName());
        setActiveFlag(RConf1.isActive());
        setPort(RConf1.getPort());
        setHost(RConf1.getHost());
    }
}
