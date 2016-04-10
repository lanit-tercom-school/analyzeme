package com.analyzeme.rConfiguration;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 *
 */
public class TestRserveConfiguration {
    /**
     * test for RserveConfiguration
     * @throws Exception
     */
    @Test
    public void TestRServeConfiguration() throws Exception{
        RserveConfiguration C1= new RserveConfiguration();
        RserveConfiguration C2=new RserveConfiguration(false,"localhost","8080","example");
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
        //is field name="example"?
        assertTrue(C2.getName().equals("example"));
    }
}
