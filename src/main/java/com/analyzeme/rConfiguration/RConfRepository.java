package com.analyzeme.rConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * repository of RConfiguration
 * Created by asus on 03.04.2016.
 */
// TODO: 15.04.2016  tests
// TODO: 14.04.2016  comments
public class RConfRepository {
    private static RConfRepository repo = new RConfRepository();
    private static List<IRConf> RConfigurations;

    private RConfRepository() {
        RConfigurations = new ArrayList<IRConf>();
        FakeRConf RConf1 = new FakeRConf(true, "FakeR");
        RenjinConf RConf2 = new RenjinConf(true, "Renjin");
        RserveConf RConf3 = new RserveConf();
        repo.addRConf(RConf3);
        repo.addRConf(RConf2);
        repo.addRConf(RConf1);

    }

    public static RConfRepository getRepo() {
        return repo;
    }

    public static List<IRConf> getAllRConfigurations() {
        return RConfigurations;
    }


    public synchronized void addRConf(IRConf newConfiguration) {
        RConfigurations.add(newConfiguration);
    }

    public IRConf getRConfByName(String name) throws IOException {
        if (name == null || name.equals("")) throw new IOException();
        for (IRConf rConf : RConfigurations) {
            if (rConf.getName().equals(name)) {
                return rConf;
            }
        }
        return null;
    }


    public void deleteRConfByName(String name) throws IOException {
        if (name == null || name.equals("")) throw new IOException();
        for (int i = 0; i < RConfigurations.size(); i++) {
            if (RConfigurations.get(i).getName().equals(name)) {
                RConfigurations.remove(i);
            }
        }

    }

    //TODO: закончить
    public void updateRConfByName(String name, String data) throws IOException {
        IRConf RConf = getRConfByName(name);
        RConf.setName("newName");
        RConf.setActiveFlag(true);


    }


}
