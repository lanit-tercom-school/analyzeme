package com.analyzeme.controllers;

import com.analyzeme.rConfiguration.RConfRepository;
import com.analyzeme.repository.UsersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @RequestMapping(value = "/")
    public ModelAndView index() throws Exception {
        ModelAndView mav = new ModelAndView("index");

        String msg = "Running IndexController.index() method";
        try {
            UsersRepository.getRepo().findUser("guest");
        } catch (IllegalArgumentException e) {
            //login, email, password  (IN THIS ORDER)
            String[] param = {"guest", "guest@mail.sth", "1234"};
            UsersRepository.getRepo().newItem(param);
        }
        mav.addObject("msg", msg);
        return mav;
    }

    @RequestMapping(value = "/index")
    public String moveToIndexPage() {
        return "index";
    }

    @RequestMapping(value = "/projects")
    public String moveToProjectPage() {
        return "projects";
    }

    @RequestMapping(value = "/data/spb")
    public String moveToPreviewPage() {
        return "preview";
    }

    @RequestMapping(value = "/fileInfo")
    public String moveToFileInfo() {
        return "fileInfo";
    }

    @RequestMapping(value = "/config")
    public ModelAndView moveToRConfPage() {

        String RConfList = RConfRepository.getRepo().allConfigurationsToJsonString();
        return new ModelAndView("config", "RConfList", RConfList);

    }


    @RequestMapping(value = "/help")
    public String moveToHelp() {
        return "help";
    }

}
