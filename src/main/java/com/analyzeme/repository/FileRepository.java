package com.analyzeme.repository;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileRepository implements IFileRepository {
	public static IFileRepository repo = new FileRepository();
	public static ArrayList<FileInfo> files;

	//TODO : rewrite tests to make ctor private
	/**
	 * creates empty repository
	 */
	public FileRepository() {
		files = new ArrayList<FileInfo>();
	}

	/**
	 * adding new file in repository
	 *
	 * @param part     - file information
	 * @param filename - filename given by user
	 * @return nameToWrite - if succeed, exception if not
	 */
	public synchronized String addNewFile(final Part part, final String filename) throws IOException {
		String uniqueName = filename;
		if (!isNameCorrect(uniqueName)) {
			uniqueName = createCorrectName(filename);
		}
		FileInfo newFile = new FileInfo(filename, uniqueName, part.getInputStream());
		files.add(newFile);
		return uniqueName;
	}

	/**
	 * just the same with addNewFile except for Part object (cannot be create as Part is an abstract class)
	 * adding new file in repository
	 *
	 * @param part     - file data (stream)
	 * @param filename - filename given by user
	 * @return uniqueName - if succeed, exception if not
	 */
	public synchronized String addNewFileForTests(ByteArrayInputStream part, final String filename) throws IOException {
		String uniqueName = filename;
		if (!isNameCorrect(uniqueName)) {
			uniqueName = createCorrectName(filename);
		}
		FileInfo newFile = new FileInfo(filename, uniqueName, part);
		files.add(newFile);
		return uniqueName;
	}

	/**
	 * creation of name to store in repository
	 *
	 * @param fileName - name given by user
	 * @return created name
	 */
	private synchronized String createCorrectName(final String fileName) throws IOException {
		final char point = '.';
		String extension = fileName.substring(fileName.indexOf(point) + 1);
		String name = fileName.substring(0, fileName.indexOf(point)) + "_";
		int index = 1;
		while (true) {
			if (isNameCorrect(name + Integer.toString(index) + point + extension)) {
				return name + Integer.toString(index) + point + extension;
			}
			index++;
		}
	}

	/**
	 * checks if name is used
	 *
	 * @param fileName - name given by user
	 * @return true if free
	 */
	private synchronized boolean isNameCorrect(final String fileName) throws IOException {
		for (String file : getAllWrittenNames()) {
			if (file.equals(fileName)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return all names (unique) of files in repository
	 */
	public synchronized ArrayList<String> getAllWrittenNames() throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		if (files != null) {
			for (FileInfo info : files) {
				list.add(info.uniqueName);
			}
		}
		return list;
	}

	/**
	 * Return file if uniqueName is given
	 *
	 * @param uniqueName - name in repository
	 * @return stream (or null if not found)
	 */
	public synchronized ByteArrayInputStream getFileByID(final String uniqueName) throws IOException {
		for (FileInfo info : files) {
			if (info.uniqueName.equals(uniqueName)) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				byte[] buffer = new byte[1024];
				int len;
				while ((len = info.data.read(buffer)) > -1) {
					baos.write(buffer, 0, len);
				}
				baos.flush();

				info.data = new ByteArrayInputStream(baos.toByteArray());
				return new ByteArrayInputStream(baos.toByteArray());
			}
		}
		return null;
	}

	//TODO: decide whether to keep this or not
	/**
	 * Return all files with given name
	 *
	 * @param name - name given by user
	 * @return streams array (or null if not found)
	 */
	public synchronized ArrayList<ByteArrayInputStream> getFiles(String name) throws IOException {
		ArrayList<ByteArrayInputStream> found = new ArrayList<ByteArrayInputStream>();
		for (FileInfo info : files) {
			if (info.nameForUser.equals(name)) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				byte[] buffer = new byte[1024];
				int len;
				while ((len = info.data.read(buffer)) > -1) {
					baos.write(buffer, 0, len);
				}
				baos.flush();

				info.data = new ByteArrayInputStream(baos.toByteArray());
				found.add(new ByteArrayInputStream(baos.toByteArray()));
			}
		}
		if (found.isEmpty()) return null;
		return found;
	}

	public synchronized int countFiles() {
		return files.size();
	}
}
