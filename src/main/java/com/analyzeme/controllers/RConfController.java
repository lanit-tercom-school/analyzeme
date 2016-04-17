package com.analyzeme.controllers;

import com.analyzeme.rConfiguration.FakeRConf;
import com.analyzeme.rConfiguration.IRConf;
import com.analyzeme.rConfiguration.RConfFactory;
import com.analyzeme.rConfiguration.RConfRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOError;
import java.io.IOException;
import java.util.List;

/**
 * Created by asus on 10.04.2016.
 */
@RestController
public class RConfController {

    /**
     * @param rConfName null if file doesn't exist
     * @throws IOException
     */
    @RequestMapping(value = "/RConf/GetRConf/{rConf_name}", method = RequestMethod.GET)
    public String getRConfiguration(@PathVariable("rConf_name") String rConfName)
            throws IOException {

            IRConf RConf = RConfRepository.getRepo().getRConfByName(rConfName);
            return RConf.toJSONObject().toString();
    }

    // TODO: 17.04.2016 comments 
    @RequestMapping(value = "/RConf/GetRConf", method = RequestMethod.GET)
    public String getRConfiguration()
            throws IOException {
        return  RConfRepository.getRepo().allConfigurationsToJsonString();

    }


    /**
      * @throws IOException
     */
    // TODO: 17.04.2016 comments
    @RequestMapping(value = "/RConf/{data}", method = RequestMethod.PUT)
    public void addRConfiguration(@PathVariable("data") String data)
            throws IOException {
        RConfRepository.getRepo().addRConf(RConfFactory.getRConf(data));

    }

    // TODO: 17.04.2016  comments
    @RequestMapping(value = "/RConf/{RConf_Name}/{data}", method = RequestMethod.POST)
    public void updateRConf(@PathVariable("data") String data, @PathVariable("RConf_Name") String name, HttpServletResponse response)
            throws IOException {
        RConfRepository.getRepo().updateRConfByName(name, data);

    }

    // TODO: 14.04.2016 comments
    @RequestMapping(value = "/RConf/{RConf_Name}/Delete", method = RequestMethod.DELETE)
    public void updateRConf(@PathVariable("RConf_Name") String name)
            throws IOException {
        RConfRepository.getRepo().deleteRConfByName(name);

    }
}