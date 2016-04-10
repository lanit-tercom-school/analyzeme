package com.analyzeme.rConfiguration;

/**
 *
 */
public class FakeRConfiguration implements IRConfiguration {
    private String name;
    /**
     * field what show is processor active
     */
    private boolean flag;


    public String getName(){
        return name;
    };

    public void setName(String name){
        this.name=name;
    }

    FakeRConfiguration(){
        name="newFake";
        flag=true;
    }
    /**
     * constructor by param flag
     * @param flag for setting field flag
     */
    public FakeRConfiguration(boolean flag, String name){
        this.name=name;
        this.flag=flag;
    }
    /**
     *set field flag
     * @param flag what will set as field flag
     */
    @Override
    public void setFlag(boolean flag) {
        this.flag=flag;

    }
    /**
     * get field flag
     * @return value of field flag
     */
    @Override
    public boolean getFlag() {
        return flag;
    }

}
