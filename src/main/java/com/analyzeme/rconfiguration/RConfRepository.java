package com.analyzeme.rconfiguration;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * repository of RConfiguration
 * Created by asus on 03.04.2016.
 */
public class RConfRepository {

    private static RConfRepository repo = new RConfRepository();
    private static List<IRConf> RConfigurations;

    /**
     * private default constructor
     * initialize repository with active FakeRConf,RenjinConf and RserveConf configuration
     * name of  RserveConf if RServe1
     * Rserve1 port and host is null
     * name of FakeRConf is FakeR
     * name of RenjinConf is Renjin
     */
    private RConfRepository() {
        RConfigurations = new ArrayList<IRConf>();
        //init new RConf
        FakeRConf RConf1 = new FakeRConf(true, "FakeR");
        RenjinConf RConf2 = new RenjinConf(true, "Renjin");
        RserveConf RConf3 = new RserveConf();
        RConf3.setName("Rserve1");
        //add new RConf
        RConfigurations.add(RConf1);
        RConfigurations.add(RConf2);
        RConfigurations.add(RConf3);
    }

    /**
     * get repository
     * @return field repo
     */
    public static RConfRepository getRepo() {
        return repo;
    }

    /**
     * get list of all RConf
     * @return field RConfigurations
     */
    public static List<IRConf> getAllRConfigurations() {
        return RConfigurations;
    }

    /**
     * add new IRConf
     * @param newConfiguration will be added to repository
     */
    public synchronized void addRConf(final IRConf newConfiguration) {
        RConfigurations.add(newConfiguration);
    }

    /**
     * get RConf by name
     * @param name of RConf
     * @return IRConf with name @param name
     * @throws IOException
     */
    public IRConf getRConfByName(final String name) throws IOException {
        if (name == null || name.equals("")) throw new IOException();
        for (IRConf rConf : RConfigurations) {
            if (rConf.getName().equals(name)) {

                return rConf;
            }
        }
        return null;
    }

    /**
     * delete RConf by name
     * @param name of RConf to delete
     * @throws IOException
     */
    public void deleteRConfByName(final String name) throws IOException {
        if (name == null || name.equals("")) throw new IOException();
        for (int i = 0; i < RConfigurations.size(); i++) {
            if (RConfigurations.get(i).getName().equals(name)) {
                RConfigurations.remove(i);
            }
        }

    }

    /**
     * update RConf by name
     * @param name of updating RConf
     * @param data JsonString with data to update
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public void updateRConfByName(final String name, final String data) throws IOException, IllegalArgumentException {

        IRConf RConf = repo.getRConfByName(name);
        RConf.assignment(RConfFactory.getRConf(data));

    }

    /**
     * get all RConf as json String
     * @return all RConf as jsonString
     */
    public String allConfigurationsToJsonString() {
        JSONArray ar = new JSONArray();
        for (IRConf rConf : RConfigurations) {
            JSONObject obj = rConf.toJSONObject();
            ar.add(obj);
        }

        return ar.toString();
    }

    /**
     * get first active non Fake RConf
     * @return active Rseve or Renjin RConf
     */
    public IRConf getDefaultConfiguration() {
        for (IRConf rConf : RConfigurations) {
            if (!(rConf instanceof FakeRConf) && rConf.isActive()) {
                return rConf;
            }

        }
        return null;
    }

    /**
     * get first active FakeRConf
     * @return first active FakeRConf
     */
    public IRConf getDefaultTestConfiguration() {
        for (IRConf rConf : RConfigurations) {
            if ((rConf instanceof FakeRConf) && rConf.isActive()) {
                return rConf;
            }

        }
        return null;
    }
}
