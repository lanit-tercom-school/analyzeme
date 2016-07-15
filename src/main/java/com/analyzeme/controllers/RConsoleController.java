package com.analyzeme.controllers;

import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.analyzers.r.UserScriptAnalyzer;
import com.analyzeme.analyzers.r.TypeOfCall;
import com.analyzeme.data.DataSet;
import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.filerepository.FileUploader;
import com.analyzeme.repository.filerepository.TypeOfFile;
import com.analyzeme.repository.projects.ProjectInfo;
import com.analyzeme.repository.UsersRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ольга on 12.04.2016.
 */
@RestController
public class RConsoleController {
    private static final Logger LOGGER;
    private final UserScriptAnalyzer userScriptAnalyzer = new UserScriptAnalyzer();

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.controllers.RConsoleController");
    }


    /**
     * @param userId
     * @param projectId
     * @param typeOfCall
     * @param typeOfResult
     * @param scriptName
     * @param scriptText
     * @return json version of result
     */

    @RequestMapping(value = "/{user_id}/{project_id}/run/script", method = RequestMethod.POST)
    public ResponseEntity<String> runR(@PathVariable("user_id") final int userId,
                                       @PathVariable("project_id") final String projectId,
                                       @RequestHeader("type_of_call") final TypeOfCall typeOfCall,
                                       @RequestHeader("type_of_result") final TypeOfReturnValue typeOfResult,
                                       @RequestHeader("name") final String scriptName,
                                       @RequestBody final String scriptText) throws Exception {
        try {
            LOGGER.debug("runR(): method started");
            return new ResponseEntity<String>(userScriptAnalyzer.runScript(userId, projectId, scriptName, scriptText, typeOfResult, typeOfCall).toJson(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            LOGGER.info("runR(): ", e.toString());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //not tested

    /**
     * saves script for user into the project
     *
     * @param userId     unique user id
     * @param projectId  unique project id
     * @param scriptName unique script name
     * @return unique name
     * @throws Exception
     */
    @RequestMapping(value = "/{user_id}/{project_id}/save/script", method = RequestMethod.POST)
    public String saveScript(@PathVariable("user_id") final int userId,
                             @PathVariable("project_id") final String projectId,
                             @RequestHeader("name") final String scriptName,
                             @RequestBody final String scriptText) throws Exception {
        LOGGER.debug("saveScript(): method started");
        UsersRepository.checkInitialization();
        DataSet file = FileUploader.upload(scriptText, scriptName, scriptName, TypeOfFile.SCRIPT);
        LOGGER.debug("saveScript(): file uploaded");
        ProjectInfo project = UsersRepository.findUser(userId).getProjects().findProjectById(projectId);
        if (project == null) {
            LOGGER.info("saveScript(): project does not exist");
            throw new IllegalArgumentException();
        }
        project.persist(file);
        LOGGER.debug("saveScript(): script in project");
        return file.getFile().getToken();
    }

    //not tested

    /**
     * gets file by its unique name
     *
     * @param scriptRefName - unique script name
     * @return file data in String
     * @throws Exception
     */
    @RequestMapping(value = "/{user_id}/{project_id}/get/script", method = RequestMethod.GET)
    public String getScript(@PathVariable("user_id") final int userId,
                            @PathVariable("project_id") final String projectId,
                            @RequestHeader("refName") final String scriptRefName) throws Exception {
        LOGGER.debug("getScript(): method started");
        UsersRepository.checkInitialization();
        FileInfo file = UsersRepository.findByReferenceName(scriptRefName, new String[]{String.valueOf(userId), projectId});
        if (file == null) {
            LOGGER.info("getScript(): file is not found");
            return null;
        }
        LOGGER.debug("getScript(): file is found");
        return IOUtils.toString(file.getData());
    }
}
