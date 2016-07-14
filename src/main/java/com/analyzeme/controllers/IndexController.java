package com.analyzeme.controllers;

import com.analyzeme.rconfiguration.RConfRepository;
import com.analyzeme.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    private String returnIndex() throws Exception {
        Logger logger = LoggerFactory.getLogger(
                "com.analyzeme.controllers.IndexController");
        logger.debug("Index page");

        UsersRepository.checkInitializationAndCreate();
        try {
            UsersRepository.findUser("guest");
        } catch (IllegalArgumentException e) {
            //login, email, password  (IN THIS ORDER)
            String[] param = {"guest", "guest@mail.sth", "1234"};
            UsersRepository.newItem(param);
        }
        return "index";
    }

    @RequestMapping(value = "/")
    public String index() throws Exception {
        return returnIndex();
    }

    @RequestMapping(value = "/index")
    public String moveToIndexPage() throws Exception {
        return returnIndex();
    }

    @RequestMapping(value = "/data/spb")
    public String moveToPreviewPage() {
        Logger logger = LoggerFactory.getLogger(
                "com.analyzeme.controllers.IndexController");
        logger.debug("Prewiew page");
        return "preview";
    }

    @RequestMapping(value = "/config")
    public ModelAndView moveToRConfPage() {
        Logger logger = LoggerFactory.getLogger(
                "com.analyzeme.controllers.IndexController");
        logger.debug("Configs page");
        String RConfList = RConfRepository.getRepo()
                .allConfigurationsToJsonString();
        return new ModelAndView("config",
                "RConfList", RConfList);

    }

    @RequestMapping(value = "/help")
    public String moveToHelp() {
        Logger logger = LoggerFactory.getLogger(
                "com.analyzeme.controllers.IndexController");
        logger.debug("Help page");
        return "help";
    }

}
