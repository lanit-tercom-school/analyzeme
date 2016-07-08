package com.analyzeme.controllers;

import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.analyzers.r.RAnalyzer;
import com.analyzeme.analyzers.r.TypeOfCall;
import com.analyzeme.data.DataSet;
import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.filerepository.FileUploader;
import com.analyzeme.repository.filerepository.TypeOfFile;
import com.analyzeme.repository.projects.ProjectInfo;
import com.analyzeme.repository.UsersRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ольга on 12.04.2016.
 */
@RestController
public class RConsoleController {

	private final RAnalyzer rAnalyzer = new RAnalyzer();

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
	public ResponseEntity<String> runRForNumber(@PathVariable("user_id") final int userId,
												 @PathVariable("project_id") final String projectId,
												 @RequestHeader("type_of_call") final TypeOfCall typeOfCall,
												 @RequestHeader("type_of_result") final TypeOfReturnValue typeOfResult,
												 @RequestHeader("name") final String scriptName,
												 @RequestBody final String scriptText) throws Exception {
        try {
            return new ResponseEntity<String>(rAnalyzer.runScript(userId, projectId, scriptName, scriptText, typeOfResult, typeOfCall).toJson(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
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
		UsersRepository.checkInitialization();
		DataSet file = FileUploader.upload(scriptText, scriptName, scriptName, TypeOfFile.SCRIPT);
		ProjectInfo project = UsersRepository.findUser(userId).getProjects().findProjectById(projectId);
		if (project == null) {
			return null;
		}
		project.persist(file);
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
		UsersRepository.checkInitialization();
		FileInfo file = UsersRepository.findByReferenceName(scriptRefName, new String[]{String.valueOf(userId), projectId});
		if (file == null) {
			return null;
		}
		return IOUtils.toString(file.getData());
	}
}
