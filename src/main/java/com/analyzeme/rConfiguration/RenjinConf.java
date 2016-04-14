package com.analyzeme.rConfiguration;

/**
 * Created by asus on 11.04.2016.
 */
public class RenjinConf implements IRConf {
    // TODO: 14.04.2016  comments
    private String name;
    /**
     * field what show is processor active
     */
    private boolean activeFlag;

    RenjinConf(){
        name="newRenjin";
        activeFlag=true;
    }
    /**
     * constructor by param flag
     * @param activeFlag for setting field activeFlag
     */
    public RenjinConf(boolean activeFlag, String name){
        this.name=name;
        this.activeFlag=activeFlag;
    }
    @Override
    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag=activeFlag;
    }

    @Override
    public void setName(String name) {
this.name=name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isActive() {
        return activeFlag;
    }
}
