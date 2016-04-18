package com.analyzeme.rConfiguration;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * test for RConfFactory
 * Created by asus on 14.04.2016.
 */
// TODO: 17.04.2016 Comments 
public class TestRConfFactory {
    @Test
    public void testRConfFactory() {
        final String st1 = "{\"rConfType\":\"FakeRConf\",\"name\":\"Example1\",\"activeFlag\":true}";
        final String st2 = "{\"rConfType\":\"RenjinConf\",\"name\":\"Example2\",\"activeFlag\":true}";
        final String st3 = "{\"rConfType\":\"RserveConf\",\"name\":\"Example3\",\"activeFlag\":true,\"host\":\"localhost\",\"port\":\"1099\"}";
        IRConf RConf1 = RConfFactory.getRConf(st1);
        //test for FakeRConf object
        assertTrue(RConf1 instanceof FakeRConf);
        assertTrue(RConf1.getName().equals("Example1"));
        assertTrue(RConf1.isActive());
        IRConf RConf2 = RConfFactory.getRConf(st2);
        //test for RenjinConf object
        assertTrue(RConf2 instanceof RenjinConf);
        assertTrue(RConf2.getName().equals("Example2"));
        assertTrue(RConf2.isActive());
        IRConf RConf3 = RConfFactory.getRConf(st3);
        //test for RserveConf object
        assertTrue(RConf3 instanceof RserveConf);
        assertTrue(RConf3.getName().equals("Example3"));
        assertTrue(RConf3.isActive());
        RserveConf rserveConf1 = (RserveConf) RConf3;
        assertTrue(rserveConf1.getHost().equals("localhost"));
        assertTrue(rserveConf1.getPort().equals("1099"));

    }
}
