package com.analyzeme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ольга on 08.03.2016.
 */

@Controller
public class ProjectController {

    @RequestMapping("/demoProject")
    public ModelAndView doGetDemoProjectPage() {
        return new ModelAndView("action");
    }
}
