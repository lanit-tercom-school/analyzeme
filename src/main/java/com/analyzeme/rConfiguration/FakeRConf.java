package com.analyzeme.rConfiguration;

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
    public void setName(String name) {
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
    public FakeRConf(boolean activeFlag, String name) {
        this.name = name;
        this.activeFlag = activeFlag;
    }

    /**
     * set field flag
     *
     * @param activeFlag what will set as field activeFlag
     */
    @Override
    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;

    }

    /**
     * get field activeFlag
     *
     * @return value of field flag
     */
    @Override
    public boolean isActive() {
        return activeFlag;
    }
// TODO: 17.04.2016 add to UML diagram
// TODO: 17.04.2016  проверять RConf на валидность

    /**
     * assignment value of param to this object
     * this object will become equal to RConf
     *
     * @param RConf will be assignmented to this FakeRConf
     */
    @Override
    public void assignment(IRConf RConf) {
        setName(RConf.getName());
        setActiveFlag(RConf.isActive());
    }

}
