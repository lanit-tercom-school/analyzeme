package com.analyzeme.rconfiguration;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * test of RConfRepository
 * Created by asus on 17.04.2016.
 */
public class TestRConfRepository {
    /**
     * test default initialization
     *
     * @throws IOException
     */
    @Test
    public void testDefaultInitialization() throws IOException {
        //get number of RConfigiration saved in repository
        int size = RConfRepository.getAllRConfigurations().size();
        //get all three RConf
        IRConf RConf1 = RConfRepository.getRepo().getRConfByName("FakeR");
        IRConf RConf2 = RConfRepository.getRepo().getRConfByName("Renjin");
        RserveConf RConf3 = (RserveConf)
                RConfRepository.getRepo().getRConfByName("Rserve1");
        //check size
        assertTrue(size == 3);
        //check first RConf
        assertTrue(RConf1.getName().equals("FakeR"));
        assertTrue(RConf1.isActive());
        //check second RConf
        assertTrue(RConf2.getName().equals("Renjin"));
        assertTrue(RConf2.isActive());
        //check third RConf
        assertTrue(RConf3.getName().equals("Rserve1"));
        assertTrue(RConf3.isActive());
        assertNull(RConf3.getHost());
        assertNull(RConf3.getPort());
    }

    /**
     * test methods addRConf() and getRConfByName()
     *
     * @throws IOException
     */
    @Test
    public void testAddAndGet() throws IOException {
//      init new RCof
        FakeRConf RConf1 = new FakeRConf(true, "testFakeR");
        RenjinConf RConf2 = new RenjinConf(true, "testRenjin");
        RserveConf RConf3 = new RserveConf();
        RConf3.setName("testRServe1");
//      add new RConf
        RConfRepository.getRepo().addRConf(RConf3);
        RConfRepository.getRepo().addRConf(RConf2);
        RConfRepository.getRepo().addRConf(RConf1);
//      get added RConf
        IRConf RConf11 = RConfRepository.getRepo().getRConfByName("testFakeR");
        IRConf RConf22 = RConfRepository.getRepo().getRConfByName("testRenjin");
        IRConf RConf33 = RConfRepository.getRepo().getRConfByName("testRServe1");
        RserveConf RConf34 = (RserveConf) RConf33;
//      test RConf1
        assertTrue(RConf11.getName().equals("testFakeR"));
        assertTrue(RConf11.isActive());
//      test RConf2
        assertTrue(RConf22.getName().equals("testRenjin"));
        assertTrue(RConf22.isActive());
//      test RConf3
        assertTrue(RConf34.getName().equals("testRServe1"));
        assertTrue(RConf34.isActive());
        assertNull(RConf34.getHost());
        assertNull(RConf34.getPort());


    }

    /**
     * * test method deleteRConfByName()
     *
     * @throws IOException
     */
    @Test
    public void testDelete() throws IOException {
// init new RConf
        FakeRConf RConf1 = new FakeRConf(true, "testFakeR");
        RenjinConf RConf2 = new RenjinConf(true, "testRenjin");
        RserveConf RConf3 = new RserveConf();
        RConf3.setName("testRServe1");
// add new RConf
        RConfRepository.getRepo().addRConf(RConf3);
        RConfRepository.getRepo().addRConf(RConf2);
        RConfRepository.getRepo().addRConf(RConf1);
// delete and try get RConf
        RConfRepository.getRepo().deleteRConfByName("testFakeR");
        IRConf RConf4 = RConfRepository.getRepo().getRConfByName("testFakeR");
        assertNull(RConf4);

    }

    /**
     * test method updateRConfByName()
     *
     * @throws IOException
     * @throws IllegalArgumentException
     */

    @Test
    public void testUpdate() throws IOException, IllegalArgumentException {
        // init json string for update
        final String data = "{\"rConfType\":\"RserveConf\"," +
                "\"name\":\"Example\",\"activeFlag\":true," +
                "\"host\":\"localhost\",\"port\":\"1099\"}";
        // update RConf
        RConfRepository.getRepo().updateRConfByName("Rserve1", data);
        // get updated RConf
        RserveConf RConf1 = (RserveConf)
                RConfRepository.getRepo().getRConfByName("Example");
        // try get old RConf
        IRConf RConf2 = RConfRepository.getRepo().getRConfByName("Rserve1");
        //test  RConf1
        assertTrue(RConf1.getName().equals("Example"));
        assertTrue(RConf1.isActive());
        assertTrue(RConf1.getHost().equals("localhost"));
        assertTrue(RConf1.getPort().equals("1099"));
        //check RConf2
        assertNull(RConf2);

    }

    /**
     * test method AllConfigurationsToJsonString()
     */
    // TODO: 17.04.2016  doesn't work
    @Ignore
    @Test
    public void testAllConfigurationsToJsonString() {

        assertTrue(RConfRepository.getRepo().allConfigurationsToJsonString().
                equals("[{\"rConfType\":\"FakeRConf\",\"name\":\"FakeR\"," +
                        "\"activeFlag\":true},{\"rConfType\":\"RenjinConf\"," +
                        "\"name\":\"Renjin\",\"activeFlag\":true},{\"port\":null," +
                        "\"rConfType\":\"RserveConf\",\"name\":\"Rserve1\"," +
                        "\"host\":null,\"activeFlag\":true}]"));
    }

    /**
     * test method getDefaultConfiguration()
     */
    @Test
    public void testGetDefaultConfiguration() {
        IRConf RConf = RConfRepository.getRepo().getDefaultConfiguration();
        assertTrue(RConf instanceof RenjinConf);
        assertTrue(RConf.getName().equals("Renjin"));
        assertTrue(RConf.isActive());

    }

    /**
     * test method getDefaultTestConfiguration
     */
    @Test
    public void testGetDefaultTestConfiguration() throws IOException {
        //add inactive FakeRConf
        RConfRepository.getRepo().addRConf(new FakeRConf(false, "testRFake"));
        //delete active FakeRConf to move add it after inactive FakeRConf
        RConfRepository.getRepo().deleteRConfByName("FakeR");
        RConfRepository.getRepo().addRConf(new FakeRConf(true, "FakeR1"));
        //get first active FakeR
        IRConf RConf = RConfRepository.getRepo().getDefaultTestConfiguration();
        //test first active FakeR
        assertTrue(RConf instanceof FakeRConf);
        assertTrue(RConf.getName().equals("FakeR1"));
        assertTrue(RConf.isActive());

    }
}
