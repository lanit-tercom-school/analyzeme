package com.analyzeme.filestorageconfiguration;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class TestFileStorageConfFactory {
    @Test
    public void testRConfFactory() {
        final String st1 = "{\"fConfType\":\"TestFConf\",\"name\":\"Example1\",\"activeFlag\":true}";
        final String st2 = "{\"fConfType\":\"FirebaseConf\",\"name\":\"Example2\",\"activeFlag\":true," +
                "\"serviceAccount\":\"testServiceAccount\",\"databaseUrl\":\"testUrl\"}";
        IFileStorageConf FConf1 = FileStorageConfFactory.getFileStorageConf(st1);
        //test for TestFConf object
        assertTrue(FConf1 instanceof TestFConf);
        assertTrue(FConf1.getName().equals("Example1"));
        assertTrue(FConf1.isActive());

        IFileStorageConf FConf2 = FileStorageConfFactory.getFileStorageConf(st2);
        //test for RserveConf object
        assertTrue(FConf2 instanceof FirebaseConf);
        assertTrue(FConf2.getName().equals("Example2"));
        assertTrue(FConf2.isActive());
        FirebaseConf firebaseConf1 = (FirebaseConf) FConf2;
        assertTrue(firebaseConf1.getServiceAccount().equals("testServiceAccount"));
        assertTrue(firebaseConf1.getDataBaseUrl().equals("testUrl"));

    }
}
