package com.analyzeme.controllers;


import com.analyzeme.portalconnection.GovSpb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by Helen on 16.04.16.
 */
@RestController
public class ConnectionController {
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.controllers.ConnectionController");
    }

    @RequestMapping(value = "/preview/file/data", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getData() throws IOException {
        LOGGER.debug("getData(): method started");
        try {
            return GovSpb.main();
        } catch (Exception e) {
            LOGGER.info("getData(): " + e.toString());
            return null;
        }
    }
}
