package com.analyzeme.repository;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

public interface IFileRepository {

	/**
	 * adding new file in repository
	 * if you don't know user, just give defaultUser ("guest") as login
	 *
	 * @param part     - file information
	 * @param filename - filename given by user
	 * @return name in repository if succeed, exception if not
	 */
	String addNewFile(final Part part, final String filename) throws IOException;

	String addNewFileForTests(final ByteArrayInputStream part, final String filename) throws IOException;

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

	/**
	 * Return all files with given name
	 *
	 * @param name - name given by user
	 * @return file handlers array (or null if not found)
	 */
	ArrayList<ByteArrayInputStream> getFiles(final String name) throws IOException;
}
