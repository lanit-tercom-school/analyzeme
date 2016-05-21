package com.analyzeme.controllers;

import com.analyzeme.parsers.InfoToJson;
import com.analyzeme.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ольга on 16.03.2016.
 */
@RestController
public class ProjectsController {

	/**
	 * gets files from project by project id
	 *
	 * @param projectId
	 * @return json like [{"uniqueName": ..., "nameForUser": ...}, {"uniqueName": ..., "nameForUser": ...}, {"uniqueName": ...}]
	 * @throws IOException
	 */
	@RequestMapping(value = "{user_id}/project/{project_id}/filesForList", method = RequestMethod.GET)
	public String getFilesForList(@PathVariable("user_id") int userId,
								  @PathVariable("project_id") String projectId)
			throws IOException {
		if (userId == 0 || projectId == null || projectId.equals("")) {
			throw new IllegalArgumentException("Incorrect userId or/and projectId");
		}
		try {
			UsersRepository.checkInitialization();
			return "{ \"Files\" : " + UsersRepository.findUser(userId).getProjects().findProjectById(projectId).returnActiveFilesForList() + "}";
			//to get only active files use:
			//ArrayList<String> filenames = UsersRepository.repo.findUser("guest").projects.findProject(projectName).returnAllNamesOfActiveFiles();
		} catch (Exception e) {
			e.printStackTrace();
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
	public String createProject(@PathVariable("user_id") int userId,
								@RequestHeader("project_name") String projectName) throws IOException {
		try {
			//when other users created, CheckInitializationAndCreate() should be called from user creator only
			//now it's possible to create a default user here
			UsersRepository.checkInitializationAndCreate();
			try {
				UsersRepository.findUser("guest");
			} catch (IllegalArgumentException e) {
				//login, email, password
				String[] param = {"guest", "guest@mail.sth", "1234"};
				UsersRepository.newItem(param);
			}
			//now username is used here
			//to use userId just change "guest" to int with it
			String project = UsersRepository.newProject(userId, projectName);
			if (project == null) return null;
			else return project;
		} catch (Exception e) {
			e.printStackTrace();
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
	public HttpStatus deleteProjectById(@PathVariable("user_id") int userId, @PathVariable("unique_name") String uniqueName)
			throws IOException {
		try {
			try {
				UsersRepository.checkInitialization();
			} catch (IllegalStateException e) {
				return HttpStatus.NOT_FOUND;
			}
			//deactivateProjectById deactivate project and all files in it
			//to remove them completely use deleteProjectCompletelyById
			return (UsersRepository.findUser(userId).getProjects().deactivateProjectById(uniqueName) ?
					HttpStatus.OK : HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpStatus.BAD_REQUEST;
		}
	}

	/**
	 * returns info about all projects
	 *
	 * @throws IOException
	 */
	@RequestMapping(value = "/projects/info", method = RequestMethod.GET)
	public String getProjectsInfo() throws IOException {
		try {
			UsersRepository.checkInitialization();
			return InfoToJson.convert(UsersRepository.findUser("guest").getProjects().getProjects());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
