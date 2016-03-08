package com.analyzeme.repository;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by lagroffe on 22.02.2016 22:27
 */

public interface IRepository {

	/**
	 * ctor should be private - call only from checkInitializationAndCreate()
	 */

	/**
	 * checks if the object of class exists
	 * if yes, return it
	 * if no, creates and return it
	 *
	 * @return unique object of repository
	 */
	IRepository checkInitializationAndCreate();


	/**
	 * checks if the object of class exists
	 * if yes, return it
	 * if no, return null
	 *
	 * @return existing unique object of repository or null
	 */
	IRepository checkInitialization();


	/**
	 * add new item into repository
	 * (item from repository name, e.g. for UsersRepository use this function to add new User)
	 * specification for parameters in data should be specified in javadoc for specific repositories
	 *
	 * @param data contains information about an object to create, e.g. name, id, sth else
	 * @return identificator in repository or throws Exception
	 * @throws Exception
	 */
	String newItem(final String[] data) throws Exception;

	/**
	 * add new file, that is connected to this repository
	 * should use all necessary information about file for future usage, then
	 * give it to other class that guarantees that file data will be saved correctly
	 *
	 * @param file - part from http request, contains all the information about the file
	 * @param data - parameters from part that are useful for this repository
	 *             specification for parameters in data should be specified in javadoc for specific repositories
	 * @return unique filename in repository or throws Exception
	 * @throws Exception
	 */
	String persist(final MultipartFile file, final String[] data) throws Exception;


	/**
	 * return all names of items in repository
	 * (item from repository name, e.g. for UsersRepository use this function to get names of users)
	 *
	 * @return array of names or null if repository is empty
	 */
	ArrayList<String> getAllNames();


	/**
	 * return json with info about all items in repository
	 * (item from repository name, e.g. for UsersRepository use this function to get all users)
	 *
	 * @return json string with array of objects
	 */
	String getAllItems() throws Exception;

	/**
	 * return json with info about an item if id or unique name is qiven
	 * (item from repository name, e.g. for UsersRepository use this function to get the user)
	 *
	 * @param id - unique name or id of an object
	 * @return json string with an object
	 */
	String getItem(final String id) throws Exception;


	/**
	 * checks if the requirements given in params are met
	 * if so, returns file by id from FileRepository
	 *
	 * @param uniqueName - filename in FileRepository
	 * @param params     - some specific requirements (such as usernmame, password, etc.)
	 * @return
	 */
	ByteArrayInputStream getFile(final String uniqueName, final String[] params) throws Exception;
}
