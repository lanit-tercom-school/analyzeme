package com.about.java.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ActionController {
    @RequestMapping(value = "/index")
    public String moveToIndexPage() {
        return "index";
    }
}