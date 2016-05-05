package com.analyzeme.repository;

//TODO: synchronized - ?

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagroffe on 22.02.2016 23:03
 */

public class UsersRepository implements IRepository {
	private static UsersRepository repo = new UsersRepository();
	private static List<UserInfo> users;
	//unique id of a new user - users.size()+1 (complete user deletion is not planned)

	/**
	 * ctor should be private - call only from checkInitializationAndCreate()
	 */
	private UsersRepository() {
		users = new ArrayList<UserInfo>();
	}

	public static UsersRepository getRepo() {
		return repo;
	}

	public static List<UserInfo> getUsers() {
		return users;
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
		for (String str : data) {
			if (str == null || str.equals(""))
				throw new IllegalArgumentException("Incorrect username, email or password");
		}
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
		if (id <= 0 || id > users.size()) throw new ArrayIndexOutOfBoundsException("Incorrect userId");
		return users.get(id - 1);
	}


	/**
	 * return UserInfo if the login is given
	 *
	 * @param username
	 * @throws Exception
	 */
	public synchronized UserInfo findUser(final String username) throws Exception {
		if (username == null || username.equals("")) throw new IllegalArgumentException("Incorrect username");
		for (UserInfo info : users) {
			if (info.getLogin().equals(username)) {
				return info;
			}
		}
		throw new IllegalArgumentException("User does not exist");
	}

	/**
	 * creates new project for a user
	 *
	 * @param username
	 * @param projectName
	 * @return project id
	 * @throws Exception
	 */
	public synchronized String newProject(final String username, final String projectName) throws Exception {
		if (username == null || username.equals("") || projectName == null || projectName.equals(""))
			throw new IllegalArgumentException("Incorrect username or projectName");
		return findUser(username).getProjects().createProject(projectName);
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
		if (userId <= 0 || projectName == null || projectName.equals(""))
			throw new IllegalArgumentException("Incorrect userId or projectName");
		return findUser(userId).getProjects().createProject(projectName);
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
		if (file == null) throw new IllegalArgumentException("Incorrect file");
		for (String str : data) {
			if (str == null || str.equals(""))
				throw new IllegalArgumentException("Incorrect filename, or username, or projectName");
		}
		return findUser(data[2]).getProjects().persist(file, data[0], data[1]);
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
		if (file == null) throw new IllegalArgumentException("Incorrect file");
		for (String str : data) {
			if (str == null || str.equals(""))
				throw new IllegalArgumentException("Incorrect filename, or username, or projectId");
		}
		return findUser(data[2]).getProjects().persistById(file, data[0], data[1]);
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
		if (file == null) throw new IllegalArgumentException("Incorrect file");
		for (String str : data) {
			if (str == null || str.equals(""))
				throw new IllegalArgumentException("Incorrect filename, or userId, or projectId");
		}
		return findUser(Integer.parseInt(data[2])).getProjects().persistById(file, data[0], data[1]);
	}

	/**
	 * add new file, that is connected to this repository
	 * should use all necessary information about file for future usage, then
	 * give it to other class that guarantees that file data will be saved correctly
	 *
	 * @param file - contains all the information about the file
	 * @param data - filename, projectId, userId
	 * @return unique filename in repository or throws Exception
	 * @throws Exception
	 */
	public synchronized String persistByIds(final ByteArrayInputStream file, final String[] data) throws Exception {
		if (file == null) throw new IllegalArgumentException("Incorrect file");
		for (String str : data) {
			if (str == null || str.equals(""))
				throw new IllegalArgumentException("Incorrect filename, or userId, or projectId");
		}
		return findUser(Integer.parseInt(data[2])).getProjects().persist(file, data[0], data[1]);
	}

	/**
	 * add new file, that is connected to this repository
	 * should use all necessary information about file for future usage, then
	 * give it to other class that guarantees that file data will be saved correctly
	 *
	 * @param file - contains all the information about the file
	 * @param data - filename, projectId, userId
	 * @return unique filename in repository or throws Exception
	 * @throws Exception
	 */
	public synchronized String persistByIds(final String file, final String[] data) throws Exception {
		if (file == null) throw new IllegalArgumentException("Incorrect file");
		for (String str : data) {
			if (str == null || str.equals(""))
				throw new IllegalArgumentException("Incorrect filename, or userId, or projectId");
		}
		return findUser(Integer.parseInt(data[2])).getProjects().persist(new ByteArrayInputStream(file.getBytes()), data[0], data[1]);
	}

	/**
	 * return all names of users in repository
	 *
	 * @return list of names or null if repository is empty
	 */
	public synchronized List<String> getAllNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (UserInfo info : users) {
			names.add(info.getLogin());
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
		return mapper.writeValueAsString(users);
	}

	//not tested

	/**
	 * return json with info about a user if id or unique name is qiven
	 *
	 * @param id - unique name or id of the user
	 * @return json string with an object
	 */
	public synchronized String getItemById(final String id) throws Exception {
		if (id == null || id.equals("")) throw new IllegalArgumentException("Incorrect id");
		int num = Integer.parseInt(id);
		UserInfo info = findUser(num);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(info);
	}


	/**
	 * checks if the requirements given in params are met
	 * if so, returns file by id from FileRepository
	 *
	 * @param filename - filename (given by user)
	 * @param params   - userId, projectId
	 * @return
	 */
	public synchronized ByteArrayInputStream getFile(final String filename, final String[] params) throws Exception {
		if (filename == null || filename.equals("")) throw new IllegalArgumentException("Incorrect fileName");
		for (String str : params) {
			if (str == null || str.equals("")) throw new IllegalArgumentException("Incorrect projectId or userId");
		}
		int userId = Integer.parseInt(params[0]);
		UserInfo user = findUser(userId);
		if (userId == 0 || user == null) {
			throw new IllegalArgumentException("User does not exist");
		}
		String projectId = params[1];
		ProjectInfo project = user.getProjects().findProjectById(projectId);
		if (project == null) {
			throw new IllegalArgumentException("Project does not exist");
		}
		for (String file : project.getFilenames()) {
			if (file.equals(filename)) {
				return FileRepository.getRepo().findFileById(filename).getData();
			}
		}
		return null;
	}


	/**
	 * checks if the requirements given in params are met
	 * if so, returns fileinfo from FileRepository
	 *
	 * @param filename - filename given by user
	 * @param params   - userId, projectId
	 * @return
	 */
	public synchronized FileInfo findFile(final String filename, final String[] params) throws Exception {
		if (filename == null || filename.equals("")) throw new IllegalArgumentException("Incorrect fileName");
		for (String str : params) {
			if (str == null || str.equals("")) throw new IllegalArgumentException("Incorrect projectId or userId");
		}
		int userId = Integer.parseInt(params[0]);
		UserInfo user = findUser(userId);
		if (userId == 0 || user == null) {
			throw new IllegalArgumentException("User does not exist");
		}
		String projectId = params[1];
		ProjectInfo project = user.getProjects().findProjectById(projectId);
		if (project == null) {
			throw new IllegalArgumentException("Project does not exist");
		}
		for (String file : project.getFilenames()) {
			if (file.equals(filename)) {
				return FileRepository.getRepo().findFileById(filename);
			}
		}
		return null;
	}

	/**
	 * checks if the requirements given in params are met
	 * if so, returns file by id from FileRepository
	 *
	 * @param uniqueFilename - fileId in repository
	 * @param params         -userId, projectId
	 * @return
	 */
	public synchronized ByteArrayInputStream getFileById(final String uniqueFilename, final String[] params) throws Exception {
		if (uniqueFilename == null || uniqueFilename.equals(""))
			throw new IllegalArgumentException("Incorrect fileName");
		for (String str : params) {
			if (str == null || str.equals("")) throw new IllegalArgumentException("Incorrect projectId or userId");
		}
		int userId = Integer.parseInt(params[0]);
		UserInfo user = findUser(userId);
		if (userId == 0 || user == null) {
			throw new IllegalArgumentException("User does not exist");
		}
		String projectId = params[1];
		ProjectInfo project = user.getProjects().findProjectById(projectId);
		if (project == null) {
			throw new IllegalArgumentException("Project does not exist");
		}
		for (String file : project.getFilenames()) {
			if (file.equals(uniqueFilename)) {
				return FileRepository.getRepo().findFileById(uniqueFilename).getData();
			}
		}
		return null;
	}


	/**
	 * checks if the requirements given in params are met
	 * if so, returns fileinfo from FileRepository
	 *
	 * @param uniqueFilename - fileId in repository
	 * @param params         - userId, projectId
	 * @return
	 */
	public synchronized FileInfo findFileById(final String uniqueFilename, final String[] params) throws Exception {
		if (uniqueFilename == null || uniqueFilename.equals(""))
			throw new IllegalArgumentException("Incorrect fileName");
		for (String str : params) {
			if (str == null || str.equals("")) throw new IllegalArgumentException("Incorrect projectId or userId");
		}
		int userId = Integer.parseInt(params[0]);
		UserInfo user = findUser(userId);
		if (userId == 0 || user == null) {
			throw new IllegalArgumentException("User does not exist");
		}
		String projectId = params[1];
		ProjectInfo project = user.getProjects().findProjectById(projectId);
		if (project == null) {
			throw new IllegalArgumentException("Project does not exist");
		}
		for (String file : project.getFilenames()) {
			if (file.equals(uniqueFilename)) {
				return FileRepository.getRepo().findFileById(uniqueFilename);
			}
		}
		return null;
	}
}
