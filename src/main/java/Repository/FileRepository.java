package Repository;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileRepository implements IFileRepository {
	public static final IFileRepository repo = new FileRepository();
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
	public String addNewFile(final Part part, final String filename, final String login) throws IOException {
		String nameToWrite = filename;
		if (!isNameCorrect(nameToWrite)) {
			nameToWrite = createCorrectName(filename);
		}
		FileInfo newFile = new FileInfo(filename, nameToWrite, login, part.getInputStream());
		files.add(newFile);
		return nameToWrite;
	}

	/**
	 * just the same with addNewFile except for Part object (cannot be create as far as Part is an abstract class)
	 * adding new file in repository
	 * if you don't know user, just give defaultUser ("guest") as login
	 *
	 * @param part     - file data (stream)
	 * @param filename - filename given by user
	 * @param login    - user id
	 * @return nameToWrite - if succeed, exception if not
	 */
	public String addNewFileForTests(final ByteArrayInputStream part, final String filename, final String login) throws IOException {
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
	public ByteArrayInputStream getFileByID(final String nameToWrite) {
		for (FileInfo info : files) {
			if (info.nameToWrite.equals(nameToWrite)) {
				return info.data;
			}
		}
		return null;
	}

	/**
	 * Return files for user if name and login are given
	 *
	 * @param name  - name given by user
	 * @param login - user name
	 * @return streams array (or null if not found)
	 */
	public ArrayList<ByteArrayInputStream> getFiles(final String name, final String login) {
		ArrayList<ByteArrayInputStream> found = new ArrayList<ByteArrayInputStream>();
		for (FileInfo info : files) {
			if (info.nameToWrite.equals(name) && info.login.equals(login)) {
				found.add(info.data);
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
	public ArrayList<ByteArrayInputStream> getFiles(String name) {
		ArrayList<ByteArrayInputStream> found = new ArrayList<ByteArrayInputStream>();
		for (FileInfo info : files) {
			if (info.nameToWrite.equals(name)) {
				found.add(info.data);
			}
		}
		if (found.isEmpty()) return null;
		return found;
	}
}