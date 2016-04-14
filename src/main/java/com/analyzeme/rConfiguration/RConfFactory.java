package com.analyzeme.rConfiguration;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by asus on 14.04.2016.
 */
public class RConfFactory {
    // TODO: 14.04.2016  comments
    private RConfFactory() {

    }

    public static IRConf getRConf(final String data) throws IllegalArgumentException {
        JSONParser parser = new JSONParser();
//// TODO: 14.04.2016 Тесты
        try {


            Object obj = parser.parse(data);
            JSONObject jsonObject = (JSONObject) obj;
            String rConfType = (String) jsonObject.get("rConfType");
            //TODO:удалить после проверки
            System.out.println(rConfType);

            if (rConfType.equals("RenjinConf")) {

                String name = (String) jsonObject.get("name");
                //TODO:удалить после проверки
                System.out.println(name);
                Boolean activeFlag = StringToBoll( (String) jsonObject.get("activeFlag"));
                //TODO:удалить после проверки
                System.out.println(activeFlag);

                return new RenjinConf(activeFlag,name);

            } else if (rConfType.equals("RserveConf")) {
                String name = (String) jsonObject.get("name");
                //TODO:удалить после проверки
                System.out.println(name);
                Boolean activeFlag = StringToBoll( (String) jsonObject.get("activeFlag"));
                //TODO:удалить после проверки
                System.out.println(activeFlag);

                String host = (String) jsonObject.get("host");
                //TODO:удалить после проверки
                System.out.println(host);
                String port = (String) jsonObject.get("port");
                //TODO:удалить после проверки
                System.out.println(port);

                return new RserveConf(activeFlag,host,port,name);

            } else if (rConfType.equals("FakeRConf")) {
                String name = (String) jsonObject.get("name");
                //TODO:удалить после проверки
                System.out.println(name);
                Boolean activeFlag = StringToBoll( (String) jsonObject.get("activeFlag"));
                //TODO:удалить после проверки
                System.out.println(activeFlag);

                return new FakeRConf(activeFlag,name);

            } else {
                throw new IllegalArgumentException("param ''rConfType'' has illegal value. ''" + rConfType + "'' isn't name of real RConfiguration");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    private static boolean StringToBoll(String st) throws IllegalArgumentException {
        if (st.equals("true")) return true;
        else if (st.equals("false")) return false;
        else
            throw new IllegalArgumentException("param ''st'' has illegal value. ''" + st + "'' isn't 'true' or 'false'");
    }
}
