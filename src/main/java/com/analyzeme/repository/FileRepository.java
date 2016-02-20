package com.analyzeme.repository;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileRepository implements IFileRepository {
	public static IFileRepository repo = new FileRepository();
	private static ArrayList<FileInfo> files;

	/**
	 * creates empty repository
	 */
	public FileRepository() {
		files = new ArrayList<FileInfo>();
	}

	/**
	 * adding new file in repository
	 * if you don't know user, just give defaultUser ("guest") as login
	 *
	 * @param part     - file information
	 * @param filename - filename given by user
	 * @param login    - user id
	 * @return nameToWrite - if succeed, exception if not
	 */
	public synchronized String addNewFile(final Part part, final String filename, final String login) throws IOException {
		String nameToWrite = filename;
		if (!isNameCorrect(nameToWrite)) {
			nameToWrite = createCorrectName(filename);
		}
		FileInfo newFile = new FileInfo(filename, nameToWrite, login, part.getInputStream());
		files.add(newFile);
		return nameToWrite;
	}

	/**
	 * just the same with addNewFile except for Part object (cannot be create as Part is an abstract class)
	 * adding new file in repository
	 * if you don't know user, just give defaultUser ("guest") as login
	 *
	 * @param part     - file data (stream)
	 * @param filename - filename given by user
	 * @param login    - user id
	 * @return nameToWrite - if succeed, exception if not
	 */
	public synchronized String addNewFileForTests(ByteArrayInputStream part, final String filename, final String login) throws IOException {
		String nameToWrite = filename;
		if (!isNameCorrect(nameToWrite)) {
			nameToWrite = createCorrectName(filename);
		}
		FileInfo newFile = new FileInfo(filename, nameToWrite, login, part);
		files.add(newFile);
		return nameToWrite;
	}

	/**
	 * creation of name to store in repository
	 *
	 * @param fileName - name given by user
	 * @return created name
	 */
	private synchronized String createCorrectName(String fileName) throws IOException {
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
	private synchronized boolean isNameCorrect(String fileName) throws IOException {
		for (String file : getAllWrittenNames()) {
			if (file.equals(fileName)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return all names of files in repository
	 */
	public synchronized ArrayList<String> getAllWrittenNames() throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		if (files != null) {
			for (FileInfo info : files) {
				list.add(info.nameToWrite);
			}
		}
		return list;
	}

	/**
	 * Return file if nameToWrite is given
	 *
	 * @param nameToWrite - name in repository
	 * @return stream (or null if not found)
	 */
	public synchronized ByteArrayInputStream getFileByID(final String nameToWrite) throws IOException {
		for (FileInfo info : files) {
			if (info.nameToWrite.equals(nameToWrite)) {
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

	/**
	 * Return files for user if name and login are given
	 *
	 * @param name     - name given by user
	 * @param username - user name
	 * @return streams array (or null if not found)
	 */
	public synchronized ArrayList<ByteArrayInputStream> getFiles(final String name, final String username) throws IOException {
		ArrayList<ByteArrayInputStream> found = new ArrayList<ByteArrayInputStream>();
		for (FileInfo info : files) {
			if (info.nameToWrite.equals(name) && info.username.equals(username)) {
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

	/**
	 * Return all files with given name
	 *
	 * @param name - name given by user
	 * @return streams array (or null if not found)
	 */
	public synchronized ArrayList<ByteArrayInputStream> getFiles(String name) throws IOException {
		ArrayList<ByteArrayInputStream> found = new ArrayList<ByteArrayInputStream>();
		for (FileInfo info : files) {
			if (info.nameToWrite.equals(name)) {
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
