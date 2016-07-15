package com.analyzeme.controllers;

import com.analyzeme.rconfiguration.IRConf;
import com.analyzeme.rconfiguration.RConfFactory;
import com.analyzeme.rconfiguration.RConfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by asus on 10.04.2016.
 */
@RestController
public class RConfController {
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.controllers.RConfController");
    }

    /**
     * @param rConfName null if file doesn't exist
     * @return IRConf as Json String
     * @throws IOException
     */
    @RequestMapping(value = "/rConf/GetRConf/{rConf_name}", method = RequestMethod.GET)
    public String getRConfiguration(@PathVariable("rConf_name") final String rConfName)
            throws IOException {
        LOGGER.debug("getRConfiguration(): method started");
        if (rConfName == null || rConfName.equals("")) {
            LOGGER.info("getRConfiguration(): empty argument");
            throw new IllegalArgumentException();
        }
        IRConf RConf = RConfRepository.getRepo().getRConfByName(rConfName);
        LOGGER.debug("getRConfiguration(): config is found");
        return RConf.toJSONObject().toString();
    }

    /**
     * @return List<IRConf> as Json String
     * @throws IOException
     */
    @RequestMapping(value = "/rConf/GetRConf", method = RequestMethod.GET)
    public String getRConfiguration()
            throws IOException {
        LOGGER.debug("getRConfiguration(): method started");
        return RConfRepository.getRepo().allConfigurationsToJsonString();

    }


    /**
     * put new configurations to repository
     *
     * @throws IOException
     */
    @RequestMapping(value = "/rConf/{data}", method = RequestMethod.PUT)
    public void addRConfiguration(@PathVariable("data") final String data)
            throws IOException {
        LOGGER.debug("addRConfiguration(): method started");
        if (data == null || data.equals("")) {
            LOGGER.info("addRConfiguration(): empty argument");
            throw new IllegalArgumentException();
        }
        RConfRepository.getRepo().addRConf(RConfFactory.getRConf(data));

    }

    /**
     * @param data to update file
     * @param name of RConf to update
     * @throws IOException
     */
    @RequestMapping(value = "/rConf/{RConf_Name}/{data}", method = RequestMethod.POST)
    public void updateRConf(@PathVariable("data") final String data, @PathVariable("RConf_Name") final String name)
            throws IOException {
        LOGGER.debug("updateRConf(): method started");
        if (data == null || data.equals("") || name == null || name.equals("")) {
            LOGGER.info("updateRConf(): empty argument");
            throw new IllegalArgumentException();
        }
        RConfRepository.getRepo().updateRConfByName(name, data);

    }

    /**
     * @param name of file to delete
     * @throws IOException
     */
    @RequestMapping(value = "/rConf/{RConf_Name}", method = RequestMethod.DELETE)
    public void updateRConf(@PathVariable("RConf_Name") final String name)
            throws IOException {
        LOGGER.debug("updateRConf(): method started");
        if (name == null || name.equals("")) {
            LOGGER.info("updateRConf(): empty argument");
            throw new IllegalArgumentException();
        }
        RConfRepository.getRepo().deleteRConfByName(name);

    }
}
