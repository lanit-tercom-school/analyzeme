package com.analyzeme.filestorageconfiguration;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * factory of FileStorageConfiguration
 */
public class FileStorageConfFactory {

    /**
     * private constructor to prevent initialization other factory
     */
    private FileStorageConfFactory() {

    }

    /**
     * Example of json string
     * {"rConfType":"TestConf","name":"Example1","activeFlag":true}
     * {"rConfType":"FirestorageConf","name":"Example2","activeFlag":true,
     *                              "serviceAccount":"testserviceAccount","databaseUrl":"testUrl"}
     *
     * @param data json string what will be used get param for FileStoragConfigurations
     * @return FConf
     * @throws IllegalArgumentException
     */

    public static IFileStorageConf getFileStorageConf(final String data) throws IllegalArgumentException {
        //Create new parser
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(data);
            String fConfType = (String) jsonObject.get("fConfType");
            if (fConfType.equals("FirebaseConf")) {
                //get param name from Json String
                String name = (String) jsonObject.get("name");
                //get param activeFlag from Json String
                Boolean activeFlag = (Boolean) jsonObject.get("activeFlag");
                String serviceAccount = (String) jsonObject.get("serviceAccount");
                String databaseUrl = (String) jsonObject.get("databaseUrl");

                return new FirebaseConf(name, activeFlag, serviceAccount, databaseUrl);

            } else if (fConfType.equals("TestFConf")) {
                //get param name from Json String
                String name = (String) jsonObject.get("name");
                //get param activeFlag from Json String
                Boolean activeFlag = (Boolean) jsonObject.get("activeFlag");

                return new TestFConf(name,activeFlag);

            } else {
                throw new IllegalArgumentException(
                        "param ''fConfType'' has illegal value. ''" +
                                fConfType + "'' isn't name of real FileStorageConfiguration");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
