package com.analyzeme.controllers;


import com.analyzeme.portalConnection.GovSpb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by Helen on 16.04.16.
 */
@RestController
public class ConnectionController {
    @RequestMapping(value = "/preview/file/data", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8" )
    public String getData() throws IOException {
        try {
            return GovSpb.main();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
