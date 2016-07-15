package com.analyzeme.controllers;

import com.analyzeme.parsers.InfoToJson;
import com.analyzeme.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by Ольга on 16.03.2016.
 */
@RestController
public class ProjectsController {
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.controllers.ProjectsController");
    }

    /**
     * gets files from project by project id
     *
     * @param projectId
     * @return json like [{"uniqueName": ..., "nameForUser": ...}, {"uniqueName": ..., "nameForUser": ...}, {"uniqueName": ...}]
     * @throws IOException
     */
    @RequestMapping(value = "{user_id}/project/{project_id}/filesForList", method = RequestMethod.GET)
    public String getFilesForList(@PathVariable("user_id") final int userId,
                                  @PathVariable("project_id") final String projectId)
            throws IOException {
        LOGGER.debug("getFilesForList(): method started");
        if (userId == 0 || projectId == null || projectId.equals("")) {
            LOGGER.info("getFilesForList(): argument is empty");
            throw new IllegalArgumentException("Incorrect userId or/and projectId");
        }
        try {
            UsersRepository.checkInitialization();
            return "{ \"Files\" : " + UsersRepository.findUser(userId).getProjects().findProjectById(projectId).returnActiveFilesForList() + "}";
        } catch (Exception e) {
            LOGGER.info("getFilesForList(): ", e.toString());
            return null;
        }
    }

    /**
     * creates new project
     *
     * @param projectName - should be passed as header
     * @return project unique name
     * null if project wasn't created
     * @throws IOException
     */
    @RequestMapping(value = "{user_id}/project/new/create", method = RequestMethod.PUT)
    public String createProject(@PathVariable("user_id") final int userId,
                                @RequestHeader("project_name") final String projectName) throws IOException {
        LOGGER.debug("createProject(): method started");
        if (userId == 0 || projectName == null || projectName.equals("")) {
            LOGGER.info("createProject(): argument is empty");
            throw new IllegalArgumentException("Incorrect userId or/and projectName");
        }
        try {
            //when other users created, CheckInitializationAndCreate() should be called from user creator only
            //now it's possible to create a default user here
            UsersRepository.checkInitializationAndCreate();
            try {
                UsersRepository.findUser("guest");
                LOGGER.debug("createProject(): guest user is found");
            } catch (IllegalArgumentException e) {
                //login, email, password
                String[] param = {"guest", "guest@mail.sth", "1234"};
                UsersRepository.newItem(param);
                LOGGER.debug("createProject(): guest user is created");
            }
            String project = UsersRepository.newProject(userId, projectName);
            if (project == null) {
                LOGGER.info("createProject(): project was not created");
                return null;
            } else {
                LOGGER.debug("createProject(): project is created");
                return project;
            }
        } catch (Exception e) {
            LOGGER.info("createProject(): ", e.toString());
            return null;
        }
    }

    /**
     * deletes project by project name
     *
     * @param uniqueName
     * @return HttpStatus.NOT_FOUND if userRepository doesn't exist
     * HttpStatus.OK if project deleted successfully
     * HttpStatus.BAD_REQUEST if sth went wrong
     */
    @RequestMapping(value = "{user_id}/project/{unique_name}/delete", method = RequestMethod.DELETE)
    public HttpStatus deleteProjectById(@PathVariable("user_id") final int userId, @PathVariable("unique_name") final String uniqueName)
            throws IOException {
        LOGGER.debug("deleteProjectById(): method started");
        if (userId == 0 || uniqueName == null || uniqueName.equals("")) {
            LOGGER.info("deleteProjectById(): argument is empty");
            throw new IllegalArgumentException("Incorrect userId or/and uniqueName");
        }
        try {
            try {
                UsersRepository.checkInitialization();
            } catch (IllegalStateException e) {
                LOGGER.info("deleteProjectById(): no users found");
                return HttpStatus.NOT_FOUND;
            }
            LOGGER.debug("deleteProjectById(): UsersRepository exists");
            //deactivateProjectById deactivate project and all files in it
            //to remove them completely use deleteProjectCompletelyById
            return (UsersRepository.findUser(userId).getProjects().deactivateProjectById(uniqueName) ?
                    HttpStatus.OK : HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.warn("deleteProjectById(): ", e.toString());
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * returns info about all projects (for guest)
     *
     * @throws IOException
     */
    @RequestMapping(value = "/projects/info", method = RequestMethod.GET)
    public String getProjectsInfo() throws IOException {
        LOGGER.debug("getProjectsInfo(): method started");
        try {
            UsersRepository.checkInitialization();
            LOGGER.debug("getProjectsInfo(): UsersRepository exists");
            return InfoToJson.convert(UsersRepository.findUser("guest").getProjects().getProjects());
        } catch (Exception e) {
            LOGGER.info("getProjectsInfo(): ", e.toString());
            return null;
        }
    }
}
