package com.analyzeme.filestorageconfiguration;

import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 *
 */
public class TestFirebaseConf {
    /**
     * test for FirebaseConf
     */
    @Test
    public void testFirebaseConfiguration() throws Exception {
        FirebaseConf C1 = new FirebaseConf();
        FirebaseConf C2 = new FirebaseConf("TestAnalizeMe",false, "testServiceAccount", "testUrl");
        //is field flag=true?
        assertTrue(C1.isActive());
        //is field host= null?
        assertNull(C1.getServiceAccount());
        //is field port= null?
        assertNull(C1.getDataBaseUrl());
        //is field flag=false?
        assertTrue(!C2.isActive());
        //is field host="localhost"?
        assertTrue(C2.getServiceAccount().equals("testServiceAccount"));
        //is field port="8080"?
        assertTrue(C2.getDataBaseUrl().equals("testUrl"));
        //is field name="example"?
        assertTrue(C2.getName().equals("TestAnalizeMe"));
        //test ToJsonString

        // test of method assignment;
        C1.assignment(C2);
        assertTrue(!C1.isActive());
        assertTrue(C1.getName().equals("TestAnalizeMe"));
        assertTrue(C1.getServiceAccount().equals("testServiceAccount"));
        assertTrue(C1.getDataBaseUrl().equals("testUrl"));

    }
}
