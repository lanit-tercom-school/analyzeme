package com.analyzeme.rConfiguration;

/**
 *
 */
public class FakeRConfiguration implements IRConfiguration {
    /**
     * field what show is processor active
     */
    private boolean flag;
    FakeRConfiguration(){
        flag=true;
    }
    /**
     * constructor by param flag
     * @param flag for setting field flag
     */
    FakeRConfiguration(boolean flag){
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
