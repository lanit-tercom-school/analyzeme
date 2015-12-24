package Repository;

import java.util.*;
import java.io.*;
import javax.servlet.http.Part;

/**
 * FileRepository is a singleton class that allow to add new files and get them for usage
 * To get access to these functions call FileRepository.repo.method();
 * <p>
 * Created by Laetitia_Lagroffe on 27.11.2015.
 */
public class FileRepository implements IFileRepository {
	public static final IFileRepository repo = new FileRepository();

	private static ArrayList<FileInfo> files;
	private static File rootFolder;
	private static String defaultUser = "guest";

	/**
	 * creates empty repository
	 */
	private FileRepository() {
		files = new ArrayList<FileInfo>();
	}

	/**
	 * creates root directory
	 *
	 * @param path - path for the repository that is to be created
	 */
	public void createRootFolder(String path) throws IOException {
		rootFolder = new File(path);
		if(!rootFolder.mkdir()) throw new IOException();
	}

	/**
	 * checks if rootFolder is attached to the root directory
	 */
	public boolean isRootFolderInitialized() {

		return (rootFolder != null);
	}

	/**
	 * adding new file in repository
	 * if you don't know user, just give defaultUser as login
	 *
	 * @param part     - file information
	 * @param filename - filename given by user
	 * @param login    - user id
	 * @return startfilename + uploadingDate + name in repository if succeed, exception if not
	 */
	//! after fixing problems change comments or return value
	public String addNewFile(Part part, String filename, String login) throws IOException {
		String response = "";
		String fileNameToWrite = filename;
		try {
			if (!isNameCorrect(filename)) {
				fileNameToWrite = createCorrectName(filename);
				if (fileNameToWrite == null || fileNameToWrite.equals("")) {
					response += ", cannot create a name to write";
				}
			}
		}
		catch (Exception e) {
			response += ", cannot create a name to write";
		}
		try {
			part.write(rootFolder.getCanonicalPath() + File.separator + fileNameToWrite);
		}
		catch (Exception e) {
			response += ", cannot write to repository";
		}
		FileInfo info = null;
		try {
			info = new FileInfo(filename, fileNameToWrite, login);
		}
		catch (Exception e){
			response += ", cannot create fileInfo structure";
		}
		files.add(info);
		return response;
	}

	/**
	 * creation of name to store in repository
	 *
	 * @param fileName - name given by user
	 * @return created name
	 */
	private String createCorrectName(String fileName) throws IOException {
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
	private boolean isNameCorrect(String fileName) throws IOException {
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
	public ArrayList<String> getAllWrittenNames() throws IOException {
		ArrayList<String> files = new ArrayList<String>();
		if (rootFolder != null) {
			for (File file : rootFolder.listFiles()) {
				files.add(file.getName());
			}
		}
		return files;
	}

	/**
	 * Return file if nameToWrite is given
	 *
	 * @param nameToWrite - name in repository
	 * @return file handler (or null if not found)
	 */
	public File getFileByID(String nameToWrite) {
		if (rootFolder != null) {
			for (File file : rootFolder.listFiles()) {
				if (file.getName().equals(nameToWrite)) {
					return file;
				}
			}
		}
		return null;
	}

	/**
	 * Return files for user if name and login are given
	 *
	 * @param name  - name given by user
	 * @param login - user name
	 * @return file handlers array (or null if not found)
	 */
	public ArrayList<File> getFiles(String name, String login) {
		ArrayList<File> filesToGet = null;
		for (FileInfo i : files) {
			if (i.startName.equals(name) && i.login.equals(login)) {
				if (files == null) {
					filesToGet = new ArrayList<File>();
				}
				filesToGet.add(getFileByID(i.nameToWrite));
			}
		}

		return filesToGet;
	}

	/**
	 * Return all files with given name
	 *
	 * @param name - name given by user
	 * @return file handlers array (or null if not found)
	 */
	public ArrayList<File> getFiles(String name) {
		ArrayList<File> filesToGet = null;
		for (FileInfo i : files) {
			if (i.startName.equals(name)) {
				if (files == null) {
					filesToGet = new ArrayList<File>();
				}
				filesToGet.add(getFileByID(i.nameToWrite));
			}
		}

		return filesToGet;
	}
}