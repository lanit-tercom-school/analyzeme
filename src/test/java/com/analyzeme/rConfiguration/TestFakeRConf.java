package com.analyzeme.rConfiguration;


import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Test class for FakeRConf
 */
public class TestFakeRConf {
    @Test
    /**
     * test for FakeR
     */
    public void TestFakeR() throws Exception {
        FakeRConf C1 = new FakeRConf();
        FakeRConf C2 = new FakeRConf(false, "Example");
        //is field flag=true?
        assertTrue(C1.isActive());
        //is field flag=false?
        assertTrue(!C2.isActive());
        //is field name="Example"?
        assertTrue(C2.getName().equals("Example"));

        //test ToJsonString
        assertTrue(C2.toJSONObject().toString().equals("{\"rConfType\":\"FakeRConf\",\"name\":\"Example\",\"activeFlag\":false}"));
        assertTrue(C1.toJSONObject().toString().equals("{\"rConfType\":\"FakeRConf\",\"name\":\"newFake\",\"activeFlag\":true}"));

        // test of method assignment;
        C1.assignment(C2);
        assertTrue(!C1.isActive());
        assertTrue(C1.getName().equals("Example"));


    }
}
