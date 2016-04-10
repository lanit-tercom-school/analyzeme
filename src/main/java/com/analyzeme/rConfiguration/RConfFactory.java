package com.analyzeme.rConfiguration;

import java.util.Objects;

/**
 * Created by asus on 10.04.2016.
 */
public class RConfFactory {
    /**
     * @param rConfType name of  AnalyzeFunction class
     * @return object of some AnalyzeFunction class
     */
    public static IRConfiguration getFunctionRConf(String rConfType) throws IllegalArgumentException{

        if (rConfType == null)
            throw new IllegalArgumentException("param ''functionType'' has illegal value.( Null is illegal value)");

        if (Objects.equals(rConfType, "FakeRConfiguration")) {
            return new FakeRConfiguration();
        } else if (Objects.equals(rConfType, "RserveConfiguration")) {
            return new RserveConfiguration();
        } else
            throw new IllegalArgumentException("param ''functionType'' has illegal value. ''" + rConfType + "'' isn't name of real class");

    }
}
