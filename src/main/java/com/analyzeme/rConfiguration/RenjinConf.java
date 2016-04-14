package com.analyzeme.rConfiguration;

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
    @Override
    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * @param name what will set as field name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get field name
     *
     * @return field name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * get is RProcessor active?
     *
     * @return field activeFlag
     */
    @Override
    public boolean isActive() {
        return activeFlag;
    }
}
