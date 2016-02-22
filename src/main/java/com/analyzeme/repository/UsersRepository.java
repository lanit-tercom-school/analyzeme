package com.analyzeme.repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by lagroffe on 22.02.2016 23:03
 */
public class UsersRepository implements IRepository {
	public IRepository repo = new UsersRepository();
	public ArrayList<UserInfo> users;
	//unique id of a new user - users.size()+1

	/**
	 * ctor should be private - call only from checkInitializationAndCreate()
	 */
	private UsersRepository() {
		users = new ArrayList<UserInfo>();
	}

	/**
	 * checks if the object of class exists
	 * if yes, return it
	 * if no, creates and return it
	 *
	 * @return unique object of repository
	 */
	public IRepository checkInitializationAndCreate() {
		if (users == null) {
			new UsersRepository();
		}
		return repo;
	}

	/**
	 * checks if the object of class exists
	 * if yes, return it
	 * if no, return null
	 *
	 * @return existing unique object of repository or null
	 */
	public IRepository checkInitialization() {
		if (users == null) {
			return null;
		}
		return repo;
	}


	/**
	 * add new item into repository
	 *
	 * @param data contains login, email, password  (IN THIS ORDER)
	 * @return identificator in repository or throws Exception
	 * @throws Exception
	 */
	public String newItem(final String[] data) throws Exception {
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
	private UserInfo findUser(final int id) throws Exception {
		return users.get(id - 1);
	}


	/**
	 * return UserInfo if the login is given
	 *
	 * @param login
	 * @throws Exception
	 */
	private UserInfo findUser(final String login) throws Exception {
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
	 * @return true if project created
	 * @throws Exception
	 */
	public boolean newProject(final String login, final String projectName) throws Exception {
		return (findUser(login).projects.createProject(projectName) != null);
	}

	/**
	 * add new file, that is connected to this repository
	 * should use all necessary information about file for future usage, then
	 * give it to other class that guarantees that file data will be saved correctly
	 *
	 * @param part - part from http request, contains all the information about the file
	 * @param data - filename, projectName, username (IN THIS ORDER)
	 * @return unique filename in repository or throws Exception
	 * @throws Exception
	 */
	public String persist(final Part part, final String[] data) throws Exception {
		return findUser(data[2]).projects.persist(part, data[0], data[1]);
	}


	/**
	 * return all names of items in repository
	 * (item from repository name, e.g. for UsersRepository use this function to add new User)
	 *
	 * @return array of names or null if repository is empty
	 */
	public ArrayList<String> getAllNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (UserInfo info : users) {
			names.add(info.login);
		}
		return names;
	}


	/**
	 * return json with info about all items in repository
	 * (item from repository name, e.g. for UsersRepository use this function to add new User)
	 *
	 * @return json string with array of objects
	 */
	public String getAllItems() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder items = new StringBuilder();
		items.append("[ ");
		for (UserInfo info : users) {
			items.append(mapper.writeValueAsString(info));
		}
		items.append(" ]");
		return items.toString();
	}

	/**
	 * return json with info about an item if id or unique name is qiven
	 * (item from repository name, e.g. for UsersRepository use this function to add new User)
	 *
	 * @param id - unique name or id of an object
	 * @return json string with an object
	 */
	public String getItem(final String id) throws Exception {
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
	 * @param params     - projectName, username (IN THIS ORDER)
	 * @return
	 */
	public ByteArrayInputStream getFile(final String uniqueName, final String[] params) throws Exception {
		/**
		 * some checking logic
		 * if requirements are met ->
		 */
		return FileRepository.repo.getFileByID(uniqueName);
	}
}
