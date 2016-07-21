package com.analyzeme.filestorageconfiguration;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class TestFileStorageConfRepository {
    /**
     * test default initialization
     *
     * @throws IOException
     */
    @Test
    public void testDefaultInitialization() throws IOException {
        //get number of RConfigiration saved in repository
        int size = FileStorageConfRepository.getAllFileStorageConfigurations().size();
        //get all three RConf
        IFileStorageConf FConf1 = FileStorageConfRepository.getRepo().getFileStorageConfByName("TestFConf");
        FirebaseConf FConf2 = (FirebaseConf)
                FileStorageConfRepository.getRepo().getFileStorageConfByName("FireConf1");
        //check size
        assertTrue(size == 2);
        //check first RConf
        assertTrue(FConf1.getName().equals("TestFConf"));
        assertTrue(FConf1.isActive());

        //check second RConf
        assertTrue(FConf2.getName().equals("FireConf1"));
        assertTrue(FConf2.isActive());
        assertNull(FConf2.getServiceAccount());
        assertNull(FConf2.getDataBaseUrl());
    }

    /**
     * test methods addFConf() and getFileStorageConfByName()
     *
     * @throws IOException
     */
    @Test
    public void testAddAndGet() throws IOException {
//      init new FConf
        TestFConf FConf1 = new TestFConf("testTestF",true);

        FirebaseConf FConf2 = new FirebaseConf();
        FConf2.setName("testFirebase1");
//      add new FConf
        FileStorageConfRepository.getRepo().addFConf(FConf2);
        FileStorageConfRepository.getRepo().addFConf(FConf1);
//      get added FConf
        IFileStorageConf FConf11 = FileStorageConfRepository.getRepo().getFileStorageConfByName("testTestF");
        IFileStorageConf FConf22 = FileStorageConfRepository.getRepo().getFileStorageConfByName("testFirebase1");
        FirebaseConf FConf34 = (FirebaseConf) FConf22;
//      test FConf1
        assertTrue(FConf11.getName().equals("testTestF"));
        assertTrue(FConf11.isActive());

//      test FConf2
        assertTrue(FConf34.getName().equals("testFirebase1"));
        assertTrue(FConf34.isActive());
        assertNull(FConf34.getServiceAccount());
        assertNull(FConf34.getDataBaseUrl());
    }

    /**
     * * test method deleteFileStorageConfByName()
     *
     * @throws IOException
     */
    @Test
    public void testDelete() throws IOException {
// init new FConf
        TestFConf FConf1 = new TestFConf("testTestF",true);
        FirebaseConf FConf2 = new FirebaseConf();
        FConf2.setName("testFirebase1");
// add new FConf
        FileStorageConfRepository.getRepo().addFConf(FConf2);
        FileStorageConfRepository.getRepo().addFConf(FConf1);
// delete and try get RConf
        FileStorageConfRepository.getRepo().deleteFConfByName("testTestF");
        IFileStorageConf FConf3 = FileStorageConfRepository.getRepo().getFileStorageConfByName("testTestF");
        assertNull(FConf3);
    }

    /**
     * test method updateFileStorageConfByName()
     *
     * @throws IOException
     * @throws IllegalArgumentException
     */

    @Test
    public void testUpdate() throws IOException, IllegalArgumentException {
        // init json string for update
        final String data = "{\"fConfType\":\"FirebaseConf\"," +
                "\"name\":\"Example\",\"activeFlag\":true," +
                "\"serviceAccount\":\"testServiceAccount\",\"databaseUrl\":\"testUrl\"}";

        // update FConf
        FileStorageConfRepository.getRepo().updateFileStorageConfByName("FireConf1", data);
        // get updated FConf
        FirebaseConf FConf1 = (FirebaseConf)FileStorageConfRepository.getRepo().getFileStorageConfByName("Example");
        // try get old FConf
        IFileStorageConf FConf2 = FileStorageConfRepository.getRepo().getFileStorageConfByName("FireConf1");
        //test  FConf1
        assertTrue(FConf1.getName().equals("Example"));
        assertTrue(FConf1.isActive());
        assertTrue(FConf1.getServiceAccount().equals("testServiceAccount"));
        assertTrue(FConf1.getDataBaseUrl().equals("testUrl"));
        //check FConf2
        assertNull(FConf2);

    }


    /**
     * test method AllConfigurationsToJsonString()
     */
    // TODO: 17.04.2016  doesn't work
    @Ignore
    @Test
    public void testAllConfigurationsToJsonString() {
        assertTrue(FileStorageConfRepository.getRepo().allConfigurationsToJsonString().
                equals("[{\"fConfType\":\"TestFConf\",\"name\":\"TestF\"," +
                        "\"activeFlag\":true},{\"serviceAccount\":null," +
                        "\"fConfType\":\"FirebaseConf\",\"name\":\"Firebase11\"," +
                        "\"databaseUrl\":null,\"activeFlag\":true}]"));
    }

    @Test
    public void testGetDefaultTestConfiguration() throws IOException {
        //add inactive TestFConf
        FileStorageConfRepository.getRepo().addFConf(new TestFConf("TestF",true));
        //delete active TestFConf to move add it after inactive TestFConf
        FileStorageConfRepository.getRepo().deleteFConfByName("TestFConf1");
        FileStorageConfRepository.getRepo().addFConf(new TestFConf("TestFConf2",true));
        //get first active TestF
        IFileStorageConf FConf = FileStorageConfRepository.getRepo().getDefaultTestConfiguration();
        //test first active TestF
        assertTrue(FConf instanceof TestFConf);
        assertTrue(FConf.getName().equals("TestFConf2"));
        assertTrue(FConf.isActive());

    }
}
