package com.analyzeme.repository.filerepository;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IFileRepository {

	/**
	 * adding new file in repository
	 *
	 * @param file     - file information
	 * @param filename - filename given by user
	 * @return name in repository if succeed, exception if not
	 */
	String persist(final MultipartFile file, final String filename) throws IOException;

	/**
	 * same with persist except for Part object
	 * adding new file in repository
	 *
	 * @param part     - file information
	 * @param filename - filename given by user
	 * @return name in repository if succeed, exception if not
	 */
	String persist(final InputStream part, final String filename) throws IOException;

	/**
	 * same with persist except for Part object
	 * adding new file in repository
	 *
	 * @param part     - file information
	 * @param filename - filename given by user
	 * @return name in repository if succeed, exception if not
	 */
	String persist(String part, final String filename) throws IOException;

	/**
	 * deletes file with given unique name
	 *
	 * @param uniqueName - name of file in repository
	 * @return true if  succeded
	 */
	boolean deleteFileByIdCompletely(final String uniqueName) throws IOException;

	/**
	 * deactivates file with given unique name
	 *
	 * @param uniqueName - name of file in repository
	 * @return true if  succeded
	 */
	boolean deleteFileById(final String uniqueName) throws IOException;

	/**
	 * cleans up the repository
	 */
	boolean deleteAllDeactivatedFiles() throws IOException;

	/**
	 * @return all names of files in repository
	 */
	List<String> getAllWrittenNames() throws IOException;

	/**
	 * Return file if nameToWrite is given
	 *
	 * @param nameToWrite - name in repository
	 * @return file handler (or null if not found)
	 */
	ByteArrayInputStream getFileByID(final String nameToWrite) throws IOException;

	/**
	 * @param uniqueName - name of the file in repository
	 * @return FileInfo with all information about the file
	 */
	FileInfo findFileById(final String uniqueName) throws IOException;
}
