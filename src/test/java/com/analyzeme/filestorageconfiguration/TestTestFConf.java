package com.analyzeme.filestorageconfiguration;


import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Test class for TestFConf
 */
public class TestTestFConf {
    @Test
    /**
     * test for TestFConf
     */
    public void testFakeR() throws Exception {
        TestFConf C1 = new TestFConf();
        TestFConf C2 = new TestFConf("Example",false);
        //is field flag=true?
        assertTrue(C1.isActive());
        //is field flag=false?
        assertTrue(!C2.isActive());
        //is field name="Example"?
        assertTrue(C2.getName().equals("Example"));
        // test of method assignment;
        C1.assignment(C2);
        assertTrue(!C1.isActive());
        assertTrue(C1.getName().equals("Example"));


    }
}
