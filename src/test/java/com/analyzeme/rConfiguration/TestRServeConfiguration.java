package com.analyzeme.rConfiguration;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 *
 */
public class TestRServeConfiguration {
    /**
     * test for RServeConfiguration
     * @throws Exception
     */
    @Test
    public void TestRServeConfiguration() throws Exception{
        RServeConfiguration C1= new RServeConfiguration();
        RServeConfiguration C2=new RServeConfiguration(false,"localhost","8080");
        //is field flag=true?
        assertTrue(C1.getFlag());
        //is field host=""?
        assertTrue(C1.getHost().equals(""));
        //is field port=""?
        assertTrue(C1.getPort().equals(""));
        //is field flag=false?
        assertTrue(!C2.getFlag());
        //is field host="localhost"?
        assertTrue(C2.getHost().equals("localhost"));
        //is field port="8080"?
        assertTrue(C2.getPort().equals("8080"));
    }
}
