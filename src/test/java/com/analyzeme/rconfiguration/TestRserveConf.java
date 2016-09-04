package com.analyzeme.rconfiguration;

import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 *
 */
public class TestRserveConf {
    /**
     * test for RserveConf
     */
    @Test
    public void testRServeConfiguration() throws Exception {
        RserveConf C1 = new RserveConf();
        RserveConf C2 = new RserveConf(false, "localhost", "8080", "example");
        //is field flag=true?
        assertTrue(C1.isActive());
        //is field host= null?
        assertNull(C1.getHost());
        //is field port= null?
        assertNull(C1.getPort());
        //is field flag=false?
        assertTrue(!C2.isActive());
        //is field host="localhost"?
        assertTrue(C2.getHost().equals("localhost"));
        //is field port="8080"?
        assertTrue(C2.getPort().equals("8080"));
        //is field name="example"?
        assertTrue(C2.getName().equals("example"));
        //test ToJsonString
        //assertTrue(C1.toJSONObject().toString().equals("{\"port\":null,\"rConfType\":\"RserveConf\",\"name\":\"newRServe\",\"host\":null,\"activeFlag\":true}"));
        //assertTrue(C2.toJSONObject().toString().equals("{\"port\":\"8080\",\"rConfType\":\"RserveConf\",\"name\":\"example\",\"host\":\"localhost\",\"activeFlag\":false}"));
        // test of method assignment;
        C1.assignment(C2);
        assertTrue(!C1.isActive());
        assertTrue(C1.getName().equals("example"));
        assertTrue(C1.getHost().equals("localhost"));
        assertTrue(C1.getPort().equals("8080"));

    }
}
