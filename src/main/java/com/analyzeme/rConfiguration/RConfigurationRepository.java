package com.analyzeme.rConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 03.04.2016.
 */
public class RConfigurationRepository {
    private static RConfigurationRepository repo = new RConfigurationRepository();
    private static List<IRConfiguration> RConfigurations;
    private RConfigurationRepository() {
        RConfigurations = new ArrayList<IRConfiguration>();
    }
    public static RConfigurationRepository getRepo() {
        return repo;
    }
    public static List<IRConfiguration> getRConfigurations() {
        return RConfigurations;
    }



    public void addRConf(IRConfiguration newConfiguration)
    {
        RConfigurations.add(newConfiguration);
    }

    public IRConfiguration getRConfByName(String name)
    {
        IRConfiguration RConf = new FakeRConfiguration(true,"example");
        return  RConf;
    }



}
