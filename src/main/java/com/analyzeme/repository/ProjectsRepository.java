package com.analyzeme.repository;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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
	public ProjectInfo findProject(final String projectName) {
		for (ProjectInfo project : projects) {
			if (project.projectName.equals(projectName)) {
				return project;
			}
		}
		return null;
	}

	/**
	 * returns all names of projects
	 *
	 * @return
	 */
	public ArrayList<String> returnAllProjectsNames() {
		if (projects.isEmpty()) return null;
		ArrayList<String> names = new ArrayList<String>();
		for (ProjectInfo info : projects) {
			names.add(info.projectName);
		}
		return names;
	}

	/**
	 * creates empty project
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
	 * deletes all files with unique names in ArrayList
	 *
	 * @param filenames - ArrayList<String> of names in repository
	 * @return true if succeed
	 */
	private boolean deleteFilesInProjectCompletely(final ArrayList<String> filenames) {
		for (String filename : filenames) {
			if (!FileRepository.repo.deleteFileByIdCompletely(filename)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * deletes project by name (+ erase all files)
	 *
	 * @param projectName - id of project to delete
	 * @return true if succeed
	 */
	public synchronized boolean deleteProjectCompletely(final String projectName) throws Exception {
		for (int i = 0; i < projects.size(); i++) {
			if (projects.get(i).projectName.equals(projectName)) {
				if (!deleteFilesInProjectCompletely(projects.get(i).filenames)) return false;
				projects.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * deactivates all files with unique names in ArrayList
	 *
	 * @param filenames - ArrayList<String> of names in repository
	 * @return true if succeed
	 */
	private boolean deactivateFiles(final ArrayList<String> filenames) {
		for (String filename : filenames) {
			for (FileInfo info : FileRepository.files) {
				if (info.uniqueName.equals(filename)) {
					info.isActive = false;
				}
			}
		}
		return true;
	}

	/**
	 * deactivates the project by name
	 *
	 * @param projectName - id of project to delete
	 * @return true if succeed
	 */
	public synchronized boolean deleteProject(final String projectName) throws Exception {
		for (ProjectInfo info : projects) {
			if (info.projectName.equals(projectName)) {
				deactivateFiles(info.filenames);
				info.isActive = false;
				info.lastChangeDate = new Date();
				return true;
			}
		}
		return false;
	}


	/**
	 * adding new file in repository
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
		if (!project.isActive) return null;
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
		if (!project.isActive) return null;
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
