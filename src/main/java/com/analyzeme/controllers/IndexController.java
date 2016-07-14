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
    private static final Logger LOGGER;
    private static boolean isGuestActive = false;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.controllers.IndexController");
    }

    private String returnIndex() throws Exception {
        LOGGER.debug("Index page");

        if (!isGuestActive) {
            UsersRepository.checkInitializationAndCreate();
            try {
                LOGGER.trace(
                        "Attempt to find a guest user");
                UsersRepository.findUser("guest");
                LOGGER.trace("Guest user found");
            } catch (IllegalArgumentException e) {
                LOGGER.trace("Create a guest user");
                //login, email, password  (IN THIS ORDER)
                String[] param = {"guest",
                        "guest@mail.sth", "1234"};
                UsersRepository.newItem(param);
                LOGGER.debug("Guest user created");
            }
            isGuestActive = true;
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
        LOGGER.debug("Preview page");
        return "preview";
    }

    @RequestMapping(value = "/config")
    public ModelAndView moveToRConfPage() {
        LOGGER.debug("Configs page");
        String RConfList = RConfRepository.getRepo()
                .allConfigurationsToJsonString();
        LOGGER.trace("R configurations are found");
        return new ModelAndView("config",
                "RConfList", RConfList);

    }

    @RequestMapping(value = "/help")
    public String moveToHelp() {
        LOGGER.debug("Help page");
        return "help";
    }

}
