package com.analyzeme.rConfiguration;


import org.junit.Test;
import static junit.framework.Assert.assertTrue;

/**
 *
 */
public class TestFakeR {
    @Test
    /**
     * test for FakeR
     */
    public void TestFakeR() throws Exception{
        FakeRConfiguration C1= new FakeRConfiguration();
        FakeRConfiguration C2=new FakeRConfiguration(false);
        //is field flag=true?
        assertTrue(C1.getFlag());
        //is field flag=false?
        assertTrue(!C2.getFlag());
    }
}
