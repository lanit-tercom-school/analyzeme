package com.analyzeme.repository;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

public interface IFileRepository {

	/**
	 * adding new file in repository
	 *
	 * @param part     - file information
	 * @param filename - filename given by user
	 * @return name in repository if succeed, exception if not
	 */
	String addNewFile(final Part part, final String filename) throws IOException;

	//TODO: rewrite tests to work with addNewFile

	/**
	 * same with addNewFile except for Part object
	 * adding new file in repository
	 *
	 * @param part     - file information
	 * @param filename - filename given by user
	 * @return name in repository if succeed, exception if not
	 */
	String addNewFileForTests(final ByteArrayInputStream part, final String filename) throws IOException;


	/**
	 * deletes file with given unique name
	 *
	 * @param uniqueName - name of file in repository
	 * @return true if  succeded
	 */
	boolean deleteFileByIdCompletely(final String uniqueName);

	/**
	 * deactivates file with given unique name
	 *
	 * @param uniqueName - name of file in repository
	 * @return true if  succeded
	 */
	boolean deleteFileById(final String uniqueName);

	/**
	 * @return all names of files in repository
	 */
	ArrayList<String> getAllWrittenNames() throws IOException;

	/**
	 * Return file if nameToWrite is given
	 *
	 * @param nameToWrite - name in repository
	 * @return file handler (or null if not found)
	 */
	ByteArrayInputStream getFileByID(final String nameToWrite) throws IOException;

	// TODO: decide whether to keep this or not

	/**
	 * Return all files with given name
	 *
	 * @param name - name given by user
	 * @return file handlers array (or null if not found)
	 */
	ArrayList<ByteArrayInputStream> getFiles(final String name) throws IOException;

	FileInfo findFileById(final String uniqueName);
}
