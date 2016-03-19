package com.analyzeme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ольга on 08.03.2016.
 */

@Controller
public class ProjectViewController {

    @RequestMapping("/project/demo")
    public ModelAndView doGetDemoProjectPage() {
        return new ModelAndView("action");
    }

    @RequestMapping("/project/{project_id}")
    public ModelAndView doGetDemoProjectPage(@PathVariable("project_id") String projectId) {
        return new ModelAndView("action", "projectId", projectId);
    }
}
