package com.analyzeme.rConfiguration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by asus on 17.04.2016.
 */
// TODO: 17.04.2016 Comments 
public class TestRConfRepository {
    @Test
    public void testAddAndGet() throws IOException {
        FakeRConf RConf1 = new FakeRConf(true, "FakeR");
        RenjinConf RConf2 = new RenjinConf(true, "Renjin");
        RserveConf RConf3 = new RserveConf();
        RConf3.setName("RServe1");

        RConfRepository.getRepo().addRConf(RConf3);
        RConfRepository.getRepo().addRConf(RConf2);
        RConfRepository.getRepo().addRConf(RConf1);

        IRConf RConf11 = RConfRepository.getRepo().getRConfByName("FakeR");
        IRConf RConf22 = RConfRepository.getRepo().getRConfByName("Renjin");
        IRConf RConf33 = RConfRepository.getRepo().getRConfByName("RServe1");
        RserveConf RConf34 = (RserveConf) RConf33;

        assertTrue(RConf11.getName().equals("FakeR"));
        assertTrue(RConf11.isActive());

        assertTrue(RConf22.getName().equals("Renjin"));
        assertTrue(RConf22.isActive());

        assertTrue(RConf34.getName().equals("RServe1"));
        assertTrue(RConf34.isActive());
        assertNull(RConf34.getHost());
        assertNull(RConf34.getPort());


    }

    @Test
    public void testDelete() throws IOException {
        FakeRConf RConf1 = new FakeRConf(true, "FakeR");
        RenjinConf RConf2 = new RenjinConf(true, "Renjin");
        RserveConf RConf3 = new RserveConf();
        RConf3.setName("RServe1");

        RConfRepository.getRepo().addRConf(RConf3);
        RConfRepository.getRepo().addRConf(RConf2);
        RConfRepository.getRepo().addRConf(RConf1);

        RConfRepository.getRepo().deleteRConfByName("FakeR");
        IRConf RConf4 = RConfRepository.getRepo().getRConfByName("FakeR");
        assertNull(RConf4);

    }

    @Test
    public void testUpdate() throws IOException, IllegalArgumentException {
        FakeRConf RConf1 = new FakeRConf(true, "FakeR");
        RenjinConf RConf2 = new RenjinConf(true, "Renjin");
        RserveConf RConf3 = new RserveConf();
        RConf3.setName("RServe1");

        RConfRepository.getRepo().addRConf(RConf3);
        RConfRepository.getRepo().addRConf(RConf2);
        RConfRepository.getRepo().addRConf(RConf1);

        final String data = "{\"rConfType\":\"RserveConf\",\"name\":\"Example\",\"activeFlag\":\"true\",\"host\":\"localhost\",\"port\":\"1099\"}";
        RConfRepository.getRepo().updateRConfByName("RServe1", data);
        IRConf RConf4 = RConfRepository.getRepo().getRConfByName("Example");
        RserveConf RConf5 = (RserveConf) RConf4;
        assertTrue(RConf5.getName().equals("Example"));
        assertTrue(RConf5.isActive());
        assertTrue(RConf5.getHost().equals("localhost"));
        assertTrue(RConf5.getPort().equals("1099"));


    }
}
