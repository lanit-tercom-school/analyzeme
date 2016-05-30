package com.analyzeme.controllers;

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

	RAnalyzer rAnalyzer = new RAnalyzer();

	/**
	 * @param userId
	 * @param projectId
	 * @param typeOfCall
	 * @param typeOfResult
	 * @param scriptName
	 * @param scriptText
	 * @return ResponseEntity<> with
	 * HttpStatus.Conflict if there was an exception during running the script
	 * HttpStatus.Accepted and result if ran successfully
	 */

	@RequestMapping(value = "/{user_id}/{project_id}/run/script", method = RequestMethod.POST)
	public ResponseEntity<Object> runRForNumber(@PathVariable("user_id") int userId,
												@PathVariable("project_id") String projectId,
												@RequestHeader("type_of_call") TypeOfCall typeOfCall,
												@RequestHeader("type_of_result") TypeOfReturnValue typeOfResult,
												@RequestHeader("name") String scriptName,
												@RequestBody String scriptText) {
		Object result;
		try {
			result = rAnalyzer.runScript(userId, projectId, scriptName, scriptText, typeOfResult, typeOfCall);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Object>(result, HttpStatus.ACCEPTED);
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
	public String saveScript(@PathVariable("user_id") int userId,
							 @PathVariable("project_id") String projectId,
							 @RequestHeader("name") String scriptName,
							 @RequestBody String scriptText) throws Exception {
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
	public String getScript(@PathVariable("user_id") int userId,
							@PathVariable("project_id") String projectId,
							@RequestHeader("refName") String scriptRefName) throws Exception {
		UsersRepository.checkInitialization();
		FileInfo file = UsersRepository.findByReferenceName(scriptRefName, new String[]{String.valueOf(userId), projectId});
		if (file == null) {
			return null;
		}
		return IOUtils.toString(file.getData());
	}
}
