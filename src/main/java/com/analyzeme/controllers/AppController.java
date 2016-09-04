package com.analyzeme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.controllers.AppController");
    }

    @RequestMapping(value = "/app/**")
    public ModelAndView moveToAppPage() {
        LOGGER.debug("moveToAppPage(): method started");
        return new ModelAndView("app");
    }
}
