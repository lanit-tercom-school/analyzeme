package com.analyzeme.controllers;

import com.analyzeme.rConfiguration.RConfRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @RequestMapping(value = "/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");

        String msg = "Running IndexController.index() method";

        mav.addObject("msg", msg);
        return mav;
    }

    @RequestMapping(value = "/index")
    public String moveToIndexPage() {
        return "index";
    }

    @RequestMapping(value = "/action")
    public String moveToActionPage() {
        return "action";
    }

    @RequestMapping(value = "/projects")
    public String moveToProjectPage() {
        return "projects";
    }

    @RequestMapping(value = "/REditorPage")
    public String moveToRScriptPage() {
        return "REditorPage";
    }

    @RequestMapping(value = "/rConf")
    public ModelAndView moveToRConfPage() {

        String RConfList = RConfRepository.getRepo().allConfigurationsToJsonString();
        return new ModelAndView("rConf", "RConfList", RConfList);

    }

}

