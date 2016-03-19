package com.analyzeme.repository;

//TODO: synchronized - ?


import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileRepository implements IFileRepository {
	private static IFileRepository repo = new FileRepository();
	private static List<FileInfo> files;

	/**
	 * creates empty repository
	 */
	FileRepository() {
		files = new ArrayList<FileInfo>();
	}

	public static IFileRepository getRepo() {
		return repo;
	}

	public static List<FileInfo> getFiles() {
		return files;
	}

	/**
	 * adding new file in repository
	 *
	 * @param file     - file information
	 * @param filename - filename given by user
	 * @return nameToWrite - if succeed, exception if not
	 */
	public synchronized String addNewFile(final MultipartFile file, final String filename) throws IOException {
		if (file == null) throw new IOException();
		if (filename == null || filename.equals("")) throw new IOException();
		String uniqueName = filename;
		if (!isNameCorrect(uniqueName)) {
			uniqueName = createCorrectName(filename);
		}
		InputStream g = file.getInputStream();
		FileInfo newFile = new FileInfo(filename, uniqueName, file.getInputStream());
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
		if (part == null) throw new IOException();
		if (filename == null || filename.equals("")) throw new IOException();
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
		if (fileName == null || fileName.equals("")) throw new IOException();
		final char point = '.';
		String extension = fileName.substring(fileName.indexOf(point) + 1);
		String name = fileName.substring(0, fileName.indexOf(point)) + "_";
		int index = 1;
		while (true) {
			StringBuilder check = new StringBuilder();
			check.append(name);
			check.append(Integer.toString(index));
			check.append(point);
			check.append(extension);
			if (isNameCorrect(check.toString())) {
				return check.toString();
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
		if (fileName == null || fileName.equals("")) throw new IOException();
		for (String file : getAllWrittenNames()) {
			if (file.equals(fileName)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * deletes file with given unique name
	 *
	 * @param uniqueName - name of file in repository
	 * @return true if  succeded
	 */
	public synchronized boolean deleteFileByIdCompletely(final String uniqueName) throws IOException {
		if (uniqueName == null || uniqueName.equals("")) throw new IOException();
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i).getUniqueName().equals(uniqueName)) {
				files.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * deactivates file with given unique name
	 *
	 * @param uniqueName - name of file in repository
	 * @return true if  succeded
	 */
	public synchronized boolean deleteFileById(final String uniqueName) throws IOException {
		if (uniqueName == null || uniqueName.equals("")) throw new IOException();
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i).getUniqueName().equals(uniqueName)) {
				files.get(i).setIsActive(false);
				return true;
			}
		}
		return false;
	}

	/**
	 * cleans up the repository
	 */
	public boolean deleteAllDeactivatedFiles() throws IOException {
		for (FileInfo info : files) {
			if (!info.isActive()) {
				files.remove(info);
			}
		}
		return true;
	}

	/**
	 * @return all names (unique) of files in repository
	 */
	public synchronized List<String> getAllWrittenNames() throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		if (files != null) {
			for (FileInfo info : files) {
				list.add(info.getUniqueName());
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
		if (uniqueName == null || uniqueName.equals("")) throw new IOException();
		for (FileInfo info : files) {
			if (info.getUniqueName().equals(uniqueName) && info.isActive()) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				byte[] buffer = new byte[1024];
				int len;
				while ((len = info.getData().read(buffer)) > -1) {
					baos.write(buffer, 0, len);
				}
				baos.flush();

				info.setData(new ByteArrayInputStream(baos.toByteArray()));
				return new ByteArrayInputStream(baos.toByteArray());
			}
		}
		return null;
	}

	/**
	 * @return size of repository
	 */
	public synchronized int countFiles() {
		return files.size();
	}

	/**
	 * @param uniqueName - name of the file in repository
	 * @return FileInfo with all information about the file
	 */
	public synchronized FileInfo findFileById(final String uniqueName) throws IOException {
		if (uniqueName == null || uniqueName.equals("")) throw new IOException();
		for (FileInfo info : files) {
			if (info.getUniqueName().equals(uniqueName)) {
				return info;
			}
		}
		return null;
	}
}
