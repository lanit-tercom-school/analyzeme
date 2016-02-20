package com.analyzeme.repository;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lagroffe on 27.11.2015.
 */
public interface IFileRepository {

	/**
	 * creates root directory
	 *
	 * @param path - path for the repository that is to be created
	 */
	void createRootFolder(String path) throws IOException;

	/**
	 * checks if rootFolder is attached to the root directory
	 */
	boolean isRootFolderInitialized();

	/**
	 * adding new file in repository
	 * if you don't know user, just give defaultUser as login
	 *
	 * @param part     - file information
	 * @param filename - filename given by user
	 * @param login    - user id
	 * @return startfilename + uploadingDate + name in repository if succeed, exception if not
	 */
	FileInfo addNewFile(Part part, String filename, String login) throws IOException;

	/**
	 * @return all names of files in repository
	 */
	ArrayList<String> getAllWrittenNames();

	/**
	 * Return file if nameToWrite is given
	 *
	 * @param nameToWrite - name in repository
	 * @return file handler (or null if not found)
	 */
	File getFileByID(String nameToWrite);

	/**
	 * Return files for user if name and login are given
	 *
	 * @param name  - name given by user
	 * @param login - user name
	 * @return file handlers array (or null if not found)
	 */
	ArrayList<File> getFiles(String name, String login);

	/**
	 * Return all files with given name
	 *
	 * @param name - name given by user
	 * @return file handlers array (or null if not found)
	 */
	ArrayList<File> getFiles(String name);
}
