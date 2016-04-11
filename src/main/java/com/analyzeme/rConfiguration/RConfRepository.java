package com.analyzeme.rConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 03.04.2016.
 */
public class RConfRepository {
    private static RConfRepository repo = new RConfRepository();
    private static List<IRConf> RConfigurations;
    private RConfRepository() {
        RConfigurations = new ArrayList<IRConf>();
    }
    public static RConfRepository getRepo() {
        return repo;
    }
    public static List<IRConf> getRConfigurations() {
        return RConfigurations;
    }



    public synchronized void addRConf(IRConf newConfiguration)
    {
        RConfigurations.add(newConfiguration);
    }

    public IRConf getRConfByName(String name) throws IOException {
        if (name == null || name.equals("")) throw new IOException();

        for (IRConf rConf : RConfigurations) {
            if (rConf.getName().equals(name)) {
                return rConf;
            }
        }

        return  null;
    }



}
