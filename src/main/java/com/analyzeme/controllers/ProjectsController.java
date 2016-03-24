package com.analyzeme.controllers;

import com.analyzeme.repository.ProjectInfo;
import com.analyzeme.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ольга on 16.03.2016.
 */
@RestController
public class ProjectsController {

	/**
	 * gets files from project by project id (for "guest" user)
	 *
	 * @param projectName
	 * @return list of fileNames
	 * null if userRepository doesn't exist or project doesn't exist
	 * @throws IOException
	 */
	@RequestMapping("/project/{project_name}/files")
	public List<String> getFiles(@PathVariable("project_name") String projectName)
			throws IOException {
		try {
			if (UsersRepository.getRepo().checkInitialization() == null) {
				return null;
			}
			//this line will return all filenames in project, including temporary deleted files
			return UsersRepository.getRepo().findUser("guest").getProjects().findProject(projectName).getFilenames();
			//to get only active files use:
			//ArrayList<String> filenames = UsersRepository.repo.findUser("guest").projects.findProject(projectName).returnAllNames();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * creates new project (for "guest" user)
	 *
	 * @param projectName
	 * @return project unique name
	 * null if project wasn't created
	 * @throws IOException
	 */
	@RequestMapping(value = "/project/{project_name}/create", method = RequestMethod.PUT)
	public String createProject(@PathVariable("project_name") String projectName) throws IOException {
		try {
			//when other users created, CheckInitializationAndCreate() should be called from user creator only
			//now it's possible to create a default user here
			UsersRepository.getRepo().checkInitializationAndCreate();
			if (UsersRepository.getRepo().findUser("guest") == null) {
				//login, email, password
				String[] param = {"guest", "guest@mail.sth", "1234"};
				UsersRepository.getRepo().newItem(param);
			}
			//now username is used here
			//to use userId just change "guest" to int with it
			String project = UsersRepository.getRepo().newProject("guest", projectName);
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
	 * @param projectName
	 * @return HttpStatus.NOT_FOUND if userRepository doesn't exist
	 * HttpStatus.OK if project deleted successfully
	 * HttpStatus.BAD_REQUEST if sth went wrong
	 */
	@RequestMapping(value = "/project/{project_name}/delete", method = RequestMethod.DELETE)
	public HttpStatus deleteProject(@PathVariable("project_name") String projectName)
			throws IOException {
		try {
			if (UsersRepository.getRepo().checkInitialization() == null) {
				//response.setHeader("Success", "project doesn't exist");
				return HttpStatus.NOT_FOUND;
			}
			//to change to deleting by id use ...projects.deleteProjectById(projectId)
			//deleteProject or deleteProjectById deactivate project and all files in it
			//to remove them completely use deleteProjectCompletely or deleteProjectCompletelyById
			return (UsersRepository.getRepo().findUser("guest").getProjects().deleteProject(projectName) ?
					HttpStatus.OK : HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpStatus.BAD_REQUEST;
		}
	}

	/**
	 * gets all projects for "guest" user
	 */
	@RequestMapping("/user/projects")
	public List<String> getProjectNames() throws IOException {
		try {
			if (UsersRepository.getRepo().checkInitialization() == null) {
				return null;
			}
			//this returns all projects (temporary deleted included)
			//to get only active projects use ...returnAllActiveProjectsNames()
			return UsersRepository.getRepo().findUser("guest").getProjects().returnAllProjectsNames();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * returns info about all projects
	 *
	 * @throws IOException
	 */
	@RequestMapping("/projects/info")
	public List<ProjectInfo> getProjectsInfo() throws IOException {
		try {
			if (UsersRepository.getRepo().checkInitialization() == null) {
				return null;
			}
			return UsersRepository.getRepo().findUser("guest").getProjects().getProjects();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}



