package com.analyzeme.rConfiguration;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * factory of RConfiguration
 * Created by asus on 14.04.2016.
 */
public class RConfFactory {
    /**
     * private constructor to prevent initialization other factory
     */
    private RConfFactory() {

    }
    // TODO: 14.04.2016 Проверка строки на валидность

    /**
     * Example of json string
     * {"rConfType":"FakeRConf","name":"Example1","activeFlag":true}
     * {"rConfType":"RenjinConf","name":"Example2","activeFlag":true}
     * {"rConfType":"RserveConf","name":"Example3","activeFlag":true,"host":"localhost","port":"1099"}
     *
     * @param data json string what will be used get param for RConfigurations
     * @return RConf
     * @throws IllegalArgumentException
     */
    public static IRConf getRConf(final String data) throws IllegalArgumentException {
        //Create new parser
        JSONParser parser = new JSONParser();
        try {

            JSONObject jsonObject = (JSONObject) parser.parse(data);
            String rConfType = (String) jsonObject.get("rConfType");
            if (rConfType.equals("RenjinConf")) {
                //get param name from Json String
                String name = (String) jsonObject.get("name");
                //get param activeFlag from Json String
                Boolean activeFlag = (Boolean) jsonObject.get("activeFlag");
                return new RenjinConf(activeFlag, name);

            } else if (rConfType.equals("RserveConf")) {
                //get param name from Json String
                String name = (String) jsonObject.get("name");
                //get param activeFlag from Json String
                Boolean activeFlag = (Boolean) jsonObject.get("activeFlag");
                //get param host from Json String
                String host = (String) jsonObject.get("host");
                //get param port from Json String
                String port = (String) jsonObject.get("port");

                return new RserveConf(activeFlag, host, port, name);

            } else if (rConfType.equals("FakeRConf")) {
                //get param name from Json String
                String name = (String) jsonObject.get("name");
                //get param activeFlag from Json String
                Boolean activeFlag = (Boolean) jsonObject.get("activeFlag");

                return new FakeRConf(activeFlag, name);

            } else {
                throw new IllegalArgumentException("param ''rConfType'' has illegal value. ''" + rConfType + "'' isn't name of real RConfiguration");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param st string what will become boolean
     * @return boolean getting from st
     * @throws IllegalArgumentException
     */
    private static boolean StringToBoll(String st) throws IllegalArgumentException {
        if (st.equals("true")) return true;
        else if (st.equals("false")) return false;
        else
            throw new IllegalArgumentException("param ''st'' has illegal value. ''" + st + "'' isn't 'true' or 'false'");
    }
}
