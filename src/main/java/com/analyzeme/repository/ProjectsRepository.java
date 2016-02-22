package com.analyzeme.repository;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lagroffe on 17.02.2016 18:40
 */
public class ProjectsRepository {
	private ArrayList<ProjectInfo> projects;

	/**
	 * creates empty repository
	 */
	public ProjectsRepository() {
		projects = new ArrayList<ProjectInfo>();
	}

	/**
	 * finds project for given user
	 *
	 * @param projectName
	 * @return
	 */
	private ProjectInfo findProject(final String projectName) {
		for (ProjectInfo project : projects) {
			if (project.projectName.equals(projectName)) {
				return project;
			}
		}
		return null;
	}

	/**
	 * creates empty project for given user
	 */
	public ProjectInfo createProject(final String projectName) throws Exception {
		if (findProject(projectName) != null) {
			return null;
		}
		ProjectInfo info = new ProjectInfo(projectName);
		projects.add(info);
		return info;
	}

	/**
	 * adding new file in repository
	 * if you don't know user, just give defaultUser ("guest") as login
	 *
	 * @param part     - file information
	 * @param filename - filename given by user
	 * @return nameToWrite - if succeed, exception if not
	 */
	public synchronized String persist(final Part part, final String filename, final String projectName) throws Exception {
		ProjectInfo info = findProject(projectName);
		if (info == null) {
			info = createProject(projectName);
		}
		String nameToWrite = info.addNewFile(part, filename);
		return nameToWrite;
	}

	/**
	 * just the same with addNewFile except for Part object (cannot be create as Part is an abstract class)
	 * adding new file in repository
	 * if you don't know user, just give defaultUser ("guest") as login
	 *
	 * @param part     - file data (stream)
	 * @param filename - filename given by user
	 * @return nameToWrite - if succeed, exception if not
	 */
	public synchronized String addNewFileForTests(ByteArrayInputStream part, final String filename, final String projectName) throws Exception {
		ProjectInfo info = findProject(projectName);
		if (info == null) {
			info = createProject(projectName);
		}
		String nameToWrite = info.addNewFileForTests(part, filename);
		return nameToWrite;
	}

	/**
	 * Return file if nameToWrite is given
	 *
	 * @param nameToWrite - name in repository
	 * @return stream (or null if not found)
	 */
	public synchronized ByteArrayInputStream getFileByID(final String nameToWrite) throws IOException {
		return FileRepository.repo.getFileByID(nameToWrite);
	}

	/**
	 * Returns all files from the project
	 */
	public synchronized ArrayList<ByteArrayInputStream> getFilesFromProject(final String projectName) throws Exception {
		ProjectInfo project = findProject(projectName);
		if (project == null || project.filenames.isEmpty()) {
			return null;
		}
		ArrayList<ByteArrayInputStream> files = new ArrayList<ByteArrayInputStream>();
		for (String name : project.filenames) {
			files.add(getFileByID(name));
		}
		return files;
	}

	/**
	 * Return a file from the project
	 */
	public synchronized ByteArrayInputStream getFileFromProject(final String filename, final String projectName) throws Exception {
		ProjectInfo project = findProject(projectName);
		if (project == null || project.filenames.isEmpty()) {
			return null;
		}
		for (String name : project.filenames) {
			if (name.equals(filename))
				return getFileByID(filename);
		}
		return null;
	}
}
