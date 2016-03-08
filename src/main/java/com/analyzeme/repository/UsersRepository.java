package com.analyzeme.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by lagroffe on 22.02.2016 23:03
 */

public class UsersRepository implements IRepository {
	public static UsersRepository repo = new UsersRepository();
	public static ArrayList<UserInfo> users;
	//unique id of a new user - users.size()+1 (user deletion isn't planned)

	/**
	 * ctor should be private - call only from checkInitializationAndCreate()
	 */
	private UsersRepository() {
		users = new ArrayList<UserInfo>();
	}


	//TODO: when users are added, checkInitializationAndCreate() should be private and called from creation of a new user only (newItem)

	/**
	 * checks if the object of class exists
	 * if yes, return this
	 * if no, creates and return it
	 *
	 * @return unique object of repository
	 */
	public synchronized IRepository checkInitializationAndCreate() {
		if (users == null) {
			new UsersRepository();
		}
		return repo;
	}

	/**
	 * checks if the object of class exists
	 * if yes, return this
	 * if no, return null
	 *
	 * @return existing unique object of repository or null
	 */
	public synchronized IRepository checkInitialization() {
		if (users == null) {
			return null;
		}
		return repo;
	}


	/**
	 * add new User into repository
	 *
	 * @param data contains login, email, password  (IN THIS ORDER)
	 * @return id in repository or throws Exception
	 * @throws Exception
	 */
	public synchronized String newItem(final String[] data) throws Exception {
		int id = users.size() + 1;
		UserInfo newUser = new UserInfo(data[0], id, data[1], data[2]);
		users.add(newUser);
		return Integer.toString(id);
	}

	/**
	 * returns UserInfo if the unique id is given
	 *
	 * @param id - user id in repository
	 * @throws Exception
	 */
	public synchronized UserInfo findUser(final int id) throws Exception {
		if (id <= 0 || id > users.size()) throw new ArrayIndexOutOfBoundsException();
		return users.get(id - 1);
	}


	/**
	 * return UserInfo if the login is given
	 *
	 * @param login
	 * @throws Exception
	 */
	public synchronized UserInfo findUser(final String login) throws Exception {
		for (UserInfo info : users) {
			if (info.login.equals(login)) {
				return info;
			}
		}
		return null;
	}

	/**
	 * creates new project for a user
	 *
	 * @param login
	 * @param projectName
	 * @return project id
	 * @throws Exception
	 */
	public synchronized String newProject(final String login, final String projectName) throws Exception {
		return findUser(login).projects.createProject(projectName);
	}

	/**
	 * creates new project for a user
	 *
	 * @param userId
	 * @param projectName
	 * @return project id
	 * @throws Exception
	 */
	public synchronized String newProject(final int userId, final String projectName) throws Exception {
		return findUser(userId).projects.createProject(projectName);
	}

	/**
	 * add new file, that is connected to this repository
	 * should use all necessary information about file for future usage, then
	 * give it to other class that guarantees that file data will be saved correctly
	 *
	 * @param file - contains all the information about the file
	 * @param data - filename, projectName, username
	 * @return unique filename in repository or throws Exception
	 * @throws Exception
	 */
	public synchronized String persist(final MultipartFile file, final String[] data) throws Exception {
		return findUser(data[2]).projects.persist(file, data[0], data[1]);
	}

	/**
	 * add new file, that is connected to this repository
	 * should use all necessary information about file for future usage, then
	 * give it to other class that guarantees that file data will be saved correctly
	 *
	 * @param file - contains all the information about the file
	 * @param data - filename, projectId, username
	 * @return unique filename in repository or throws Exception
	 * @throws Exception
	 */
	public synchronized String persistByProjectId(final MultipartFile file, final String[] data) throws Exception {
		return findUser(data[2]).projects.persistById(file, data[0], data[1]);
	}

	/**
	 * add new file, that is connected to this repository
	 * should use all necessary information about file for future usage, then
	 * give it to other class that guarantees that file data will be saved correctly
	 *
	 * @param file - part from http request, contains all the information about the file
	 * @param data - filename, projectId, userId
	 * @return unique filename in repository or throws Exception
	 * @throws Exception
	 */
	public synchronized String persistByIds(final MultipartFile file, final String[] data) throws Exception {
		return findUser(Integer.parseInt(data[2])).projects.persistById(file, data[0], data[1]);
	}

	/**
	 * return all names of users in repository
	 *
	 * @return array of names or null if repository is empty
	 */
	public synchronized ArrayList<String> getAllNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (UserInfo info : users) {
			names.add(info.login);
		}
		return names;
	}

	//not tested
	/**
	 * return json with info about all users in repository
	 *
	 * @return json string with array of objects
	 */
	public synchronized String getAllItems() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder items = new StringBuilder();
		items.append("[ ");
		for (UserInfo info : users) {
			items.append(mapper.writeValueAsString(info));
		}
		items.append(" ]");
		return items.toString();
	}

	//not tested
	/**
	 * return json with info about a user if id or unique name is qiven
	 *
	 * @param id - unique name or id of the user
	 * @return json string with an object
	 */
	public synchronized String getItem(final String id) throws Exception {
		int num = Integer.parseInt(id);
		UserInfo info = findUser(num);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(info);
	}


	/**
	 * checks if the requirements given in params are met
	 * if so, returns file by id from FileRepository
	 *
	 * @param uniqueName - filename in FileRepository
	 * @param params     - projectName, username)
	 * @return
	 */
	public synchronized ByteArrayInputStream getFile(final String uniqueName, final String[] params) throws Exception {
		/**
		 * some checking logic
		 * if requirements are met ->
		 */
		return FileRepository.repo.getFileByID(uniqueName);
	}
}
