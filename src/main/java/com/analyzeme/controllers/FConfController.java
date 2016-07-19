package com.analyzeme.controllers;

import com.analyzeme.filestorageconfiguration.FileStorageConfFactory;
import com.analyzeme.filestorageconfiguration.FileStorageConfRepository;
import com.analyzeme.filestorageconfiguration.IFileStorageConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by Kirill Zubov on 7/18/2016.
 */
@RestController
public class FConfController {
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.controllers.FConfController");
    }

    /**
     * @param fConfName null if file doesn't exist
     * @return IFileStorageConf as Json String
     * @throws IOException
     */
    @RequestMapping(value = "/fConf/GetFConf/{fConf_name}", method = RequestMethod.GET)
    public String getFConfiguration(@PathVariable("fConf_name") final String fConfName)
            throws IOException {
        LOGGER.debug("getFConfiguration(): method started");
        if (fConfName == null || fConfName.equals("")) {
            LOGGER.info("getFConfiguration(): empty argument");
            throw new IllegalArgumentException();
        }
        IFileStorageConf FConf = FileStorageConfRepository.getRepo().getFileStorageConfByName(fConfName);
        LOGGER.debug("getFConfiguration(): config is found");
        return FConf.toJSONObject().toString();
    }

    /**
     * @return List<IFileStorageConf> as Json String
     * @throws IOException
     */
    @RequestMapping(value = "/fConf/GetFConf", method = RequestMethod.GET)
    public String getFConfiguration()
            throws IOException {
        LOGGER.debug("getFConfiguration(): method started");
        return FileStorageConfRepository.getRepo().allConfigurationsToJsonString();

    }


    /**
     * put new configurations to repository
     *
     * @throws IOException
     */
    @RequestMapping(value = "/fConf/{data}", method = RequestMethod.PUT)
    public void addFConfiguration(@PathVariable("data") final String data)
            throws IOException {
        LOGGER.debug("addFConfiguration(): method started");
        if (data == null || data.equals("")) {
            LOGGER.info("addFConfiguration(): empty argument");
            throw new IllegalArgumentException();
        }
        FileStorageConfRepository.getRepo().addFConf(FileStorageConfFactory.getFileStorageConf(data));

    }

    /**
     * @param data to update file
     * @param name of FConf to update
     * @throws IOException
     */
    @RequestMapping(value = "/fConf/{FConf_Name}/{data}", method = RequestMethod.POST)
    public void updateFConf(@PathVariable("data") final String data, @PathVariable("FConf_Name") final String name)
            throws IOException {
        LOGGER.debug("updateFConf(): method started");
        if (data == null || data.equals("") || name == null || name.equals("")) {
            LOGGER.info("updateFConf(): empty argument");
            throw new IllegalArgumentException();
        }
        FileStorageConfRepository.getRepo().updateFileStorageConfByName(name, data);

    }

    /**
     * @param name of file to delete
     * @throws IOException
     */
    @RequestMapping(value = "/fConf/{FConf_Name}", method = RequestMethod.DELETE)
    public void updateFConf(@PathVariable("FConf_Name") final String name)
            throws IOException {
        LOGGER.debug("updateFConf(): method started");
        if (name == null || name.equals("")) {
            LOGGER.info("updateFConf(): empty argument");
            throw new IllegalArgumentException();
        }
        FileStorageConfRepository.getRepo().deleteFConfByName(name);

    }
}
