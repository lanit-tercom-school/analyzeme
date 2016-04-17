package com.analyzeme.rConfiguration;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * repository of RConfiguration
 * Created by asus on 03.04.2016.
 */

// TODO: 14.04.2016  comments
public class RConfRepository {
    // TODO: 14.04.2016  comments
    // TODO: 17.04.2016  test 
    private static RConfRepository repo = new RConfRepository();
    // TODO: 14.04.2016  comments
    // TODO: 17.04.2016  test 
    private static List<IRConf> RConfigurations;

    // TODO: 14.04.2016  comments
    // TODO: 17.04.2016  complete default initialization
    private RConfRepository() {
        RConfigurations = new ArrayList<IRConf>();
//        RenjinConf RConf2 = new RenjinConf(true, "Renjin");
        //    RserveConf RConf3 = new RserveConf();
        //    RConf3.setName("RServe1");
//       RConfRepository.getRepo().addRConf(RConf3);
        // RConfRepository.getRepo().addRConf(RConf2);
        // RConfRepository.getRepo().addRConf(RConf1);

    }

    // TODO: 14.04.2016  comments
    public static RConfRepository getRepo() {
        return repo;
    }

    // TODO: 14.04.2016  comments
    public static List<IRConf> getAllRConfigurations() {
        return RConfigurations;
    }

    // TODO: 14.04.2016  comments
    public synchronized void addRConf(IRConf newConfiguration) {
        RConfigurations.add(newConfiguration);
    }

    // TODO: 14.04.2016  comments
    public IRConf getRConfByName(String name) throws IOException {
        if (name == null || name.equals("")) throw new IOException();
        for (IRConf rConf : RConfigurations) {
            if (rConf.getName().equals(name)) {

                return rConf;
            }
        }
        return null;
    }

    // TODO: 14.04.2016  comments
    public void deleteRConfByName(String name) throws IOException {
        if (name == null || name.equals("")) throw new IOException();
        for (int i = 0; i < RConfigurations.size(); i++) {
            if (RConfigurations.get(i).getName().equals(name)) {
                RConfigurations.remove(i);
            }
        }

    }

    // TODO: 14.04.2016  comments
    public void updateRConfByName(String name, String data) throws IOException, IllegalArgumentException {

        IRConf RConf = getRConfByName(name);
        RConf.assignment(RConfFactory.getRConf(data));

    }
    // TODO: 14.04.2016  comments
    public String allConfigurationsToJsonString(){
        JSONArray ar = new JSONArray();
        for (IRConf rConf : RConfigurations) {
            JSONObject obj = rConf.toJSONObject();
            ar.add(obj);
        }

        return ar.toString();
    }


}
