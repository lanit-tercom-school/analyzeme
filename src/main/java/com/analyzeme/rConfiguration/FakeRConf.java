package com.analyzeme.rConfiguration;

/**
 *
 */
public class FakeRConf implements IRConf {
    private String name;
    /**
     * field what show is processor active
     */
    private boolean activeFlag;


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    FakeRConf(){
        name="newFake";
        activeFlag=true;
    }
    /**
     * constructor by param flag
     * @param activeFlag for setting field activeFlag
     */
    public FakeRConf(boolean activeFlag, String name){
        this.name=name;
        this.activeFlag=activeFlag;
    }
    /**
     *set field flag
     * @param activeFlag what will set as field activeFlag
     */
    @Override
    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag=activeFlag;

    }
    /**
     * get field activeFlag
     * @return value of field flag
     */
    @Override
    public boolean isActive() {
        return activeFlag;
    }

}
