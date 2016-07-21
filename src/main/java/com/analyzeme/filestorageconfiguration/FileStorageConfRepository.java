package com.analyzeme.filestorageconfiguration;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * repository of FileStorageConfiguration
 * Created by Kirill Zubov on 7/13/2016.
 */
public class FileStorageConfRepository {
    private static FileStorageConfRepository repo = new FileStorageConfRepository();
    private static List<IFileStorageConf> FileStorageConfigurations;

    /**
     * private default constructor
     * initialize repository with active TestFConf, FirebaseConf configuration
     * name of  FirebaseConf if FireConf1
     * FireConf1 serviceAccount and databaseUrl is null
     * name of TestFConf is TestFConf
     */
    private FileStorageConfRepository() {
        FileStorageConfigurations = new ArrayList<IFileStorageConf>();
        //init new FConf
        FirebaseConf FireConf = new FirebaseConf();
        FireConf.setName("FireConf1");
        TestFConf TestFConf = new TestFConf("TestFConf", true);
        //add new FConf
        FileStorageConfigurations.add(FireConf);
        FileStorageConfigurations.add(TestFConf);
    }


    /**
     * get repository
     * @return field repo
     */
    public static FileStorageConfRepository getRepo() {
        return repo;
    }

    /**
     * get list of all FConf
     * @return field FileStorageConfigurations
     */
    public static List<IFileStorageConf> getAllFileStorageConfigurations() {
        return FileStorageConfigurations;
    }

    /**
     * add new IFConf
     * @param newConfiguration will be added to repository
     */
    public synchronized void addFConf(final IFileStorageConf newConfiguration) {

        FileStorageConfigurations.add(newConfiguration);
    }

    /**
     * get RConf by name
     * @param name of FConf
     * @return IFileStorageConf with name @param name
     * @throws IOException
     */
    public IFileStorageConf getFileStorageConfByName(final String name) throws IOException {
        if (name == null || name.equals("")) throw new IOException();
        for (IFileStorageConf fConf : FileStorageConfigurations) {
            if (fConf.getName().equals(name)) {

                return fConf;
            }
        }
        return null;
    }

    /**
     * delete FConf by name
     * @param name of FConf to delete
     * @throws IOException
     */
    public void deleteFConfByName(final String name) throws IOException {
        if (name == null || name.equals("")) throw new IOException();
        for (int i = 0; i < FileStorageConfigurations.size(); i++) {
            if (FileStorageConfigurations.get(i).getName().equals(name)) {
                FileStorageConfigurations.remove(i);
            }
        }

    }

    /**
     * update FConf by name
     * @param name of updating FConf
     * @param data JsonString with data to update
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public void updateFileStorageConfByName(final String name, final String data)
            throws IOException, IllegalArgumentException {

        IFileStorageConf FConf = repo.getFileStorageConfByName(name);
        FConf.assignment(FileStorageConfFactory.getFileStorageConf(data));
    }

    /**
     * get all Fonf as json String
     * @return all FConf as jsonString
     */
    public String allConfigurationsToJsonString() {
        JSONArray ar = new JSONArray();
        for (IFileStorageConf fConf : FileStorageConfigurations) {
            JSONObject obj = fConf.toJSONObject();
            ar.add(obj);
        }

        return ar.toString();
    }

    /**
     * get first active non TestF FConf
     * @return active FirebaseConf  FConf
     */
    public IFileStorageConf getDefaultConfiguration() {
        for (IFileStorageConf fConf : FileStorageConfigurations) {
            if (!(fConf instanceof TestFConf) && fConf.isActive()) {
                return fConf;
            }

        }
        return null;
    }

    /**
     * get first active TestFConf
     * @return first active TestFConf
     */
    public IFileStorageConf getDefaultTestConfiguration() {
        for (IFileStorageConf fConf : FileStorageConfigurations) {
            if ((fConf instanceof TestFConf) && fConf.isActive()) {
                return fConf;
            }
        }
        return null;
    }


}
