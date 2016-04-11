package com.analyzeme.controllers;

import com.analyzeme.rConfiguration.FakeRConf;
import com.analyzeme.rConfiguration.IRConf;
import com.analyzeme.rConfiguration.RConfRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by asus on 10.04.2016.
 */
@RestController
public class RConfController {

    /**
     * @param rConfName null if file doesn't exist
     * @throws IOException
     */
    @RequestMapping(value = "/RConf/{rConf_name}", method = RequestMethod.GET)
    public IRConf getRConfiguration(@PathVariable("rConf_name") String rConfName, HttpServletResponse response)
            throws IOException {
        try {
            IRConf RConf = RConfRepository.getRepo().getRConfByName(rConfName);
            return RConf;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * @param rConfType null if file doesn't exist
     * @throws IOException
     */
    @RequestMapping(value = "/RConf/{rConf_type}", method = RequestMethod.POST)
    public void addRConfiguration(@PathVariable("rConf_type") String rConfType, HttpServletResponse response)
            throws IOException {
        try {

            IRConf RConf = new FakeRConf(true,"example");
            RConfRepository.getRepo().addRConf(RConf);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
