package com.analyzeme.controllers;

import com.analyzeme.repository.ProjectInfo;
import com.analyzeme.repository.UsersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ольга on 08.03.2016.
 */

@Controller
public class ProjectViewController {

    @RequestMapping("/demo")
    public ModelAndView doGetDemoProjectPage() {
        return new ModelAndView("action");
    }

    @RequestMapping("/project/{project_id}")
    public ModelAndView doGetDemoProjectPage(@PathVariable("project_id") String projectId) {
        ProjectInfo project;
        try {
            if (UsersRepository.getRepo().checkInitialization() == null) {
                return new ModelAndView("error");
            }
            project = UsersRepository.getRepo().findUser("guest").getProjects().findProjectById(projectId);
            if (project == null) {
                return new ModelAndView("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("error");
        }
        return new ModelAndView("action", "project", project);
    }

}
