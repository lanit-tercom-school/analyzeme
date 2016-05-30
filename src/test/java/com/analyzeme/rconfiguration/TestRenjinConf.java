package com.analyzeme.rconfiguration;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * test for  RenjinConf
 */
public class TestRenjinConf {

    @Test
    /**
     * test for Renjin
     */
    public void testRenjin() throws Exception {
        RenjinConf C1 = new RenjinConf();
        RenjinConf C2 = new RenjinConf(false, "Example");
        //is field flag=true?
        assertTrue(C1.isActive());
        //is field flag=false?
        assertTrue(!C2.isActive());
        //is field name="Example"?
        assertTrue(C2.getName().equals("Example"));
        //test ToJsonString
        // assertTrue(C1.toJSONObject().toString().equals("{\"rConfType\":\"RenjinConf\",\"name\":\"newRenjin\",\"activeFlag\":true}"));
        //  assertTrue(C2.toJSONObject().toString().equals("{\"rConfType\":\"RenjinConf\",\"name\":\"Example\",\"activeFlag\":false}"));
        // test of method assignment;
        C1.assignment(C2);
        assertTrue(!C1.isActive());
        assertTrue(C1.getName().equals("Example"));

    }
}
