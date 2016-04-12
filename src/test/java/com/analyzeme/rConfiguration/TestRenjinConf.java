package com.analyzeme.rConfiguration;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by asus on 12.04.2016.
 */
public class TestRenjinConf{

        @Test
        /**
         * test for Renjin
         */
        public void TestRenjin() throws Exception{
            RenjinConf C1= new RenjinConf();
            RenjinConf C2=new RenjinConf(false,"Example");
            //is field flag=true?
            assertTrue(C1.isActive());
            //is field flag=false?
            assertTrue(!C2.isActive());
            //is field name="Example"?
            assertTrue(C2.getName().equals("Example"));
        }
}
