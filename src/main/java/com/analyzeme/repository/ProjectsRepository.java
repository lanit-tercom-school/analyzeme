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
	public ArrayList<ProjectInfo> projects;
	private int counter = 0;

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
	 * finds project for given user
	 *
	 * @param projectId
	 * @return
	 */
	public ProjectInfo findProjectById(final String projectId) {
		for (ProjectInfo project : projects) {
			if (project.uniqueName.equals(projectId)) {
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
	 * returns all names of active projects
	 *
	 * @return
	 */
	public ArrayList<String> returnAllActiveProjectsNames() {
		if (projects.isEmpty()) return null;
		ArrayList<String> names = new ArrayList<String>();
		for (ProjectInfo info : projects) {
			if (info.isActive) names.add(info.projectName);
		}
		return names;
	}

	private String createUniqueName() {
		if (counter == 0) return "project";
		String uniqueName = "project_" + Integer.toString(counter);
		return uniqueName;
	}

	/**
	 * creates empty project
	 */
	public String createProject(final String projectName) throws Exception {
		if (findProject(projectName) != null) {
			return null;
		}
		String uniqueName = createUniqueName();
		ProjectInfo info = new ProjectInfo(projectName, uniqueName);
		projects.add(info);
		counter++;
		return uniqueName;
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
	public synchronized String persist(final Part part, final String filename, String projectName) throws Exception {
		ProjectInfo info = findProject(projectName);
		if (info == null) {
			projectName = createProject(projectName);
			info = findProject(projectName);
		}
		String nameToWrite = info.addNewFile(part, filename);
		return nameToWrite;
	}

	/**
	 * adding new file in repository
	 *
	 * @param part     - file information
	 * @param filename - filename given by user
	 * @return nameToWrite - if succeed, exception if not
	 */
	public synchronized String persistById(final Part part, final String filename, final String projectId) throws Exception {
		ProjectInfo info = findProjectById(projectId);
		if (info == null) return null;
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
	public synchronized String addNewFileForTests(ByteArrayInputStream part, final String filename, String projectName) throws Exception {
		ProjectInfo info = findProject(projectName);
		if (info == null) throw new IllegalArgumentException();
		String nameToWrite = info.addNewFileForTests(part, filename);
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
	public synchronized String addNewFileForTestsById(ByteArrayInputStream part, final String filename, final String projectId) throws Exception {
		ProjectInfo info = findProjectById(projectId);
		if (info == null) throw new IllegalArgumentException();
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
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i) == null) files.remove(i);
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


	/**
	 * deletes project by id (+ erase all files)
	 *
	 * @param projectId - id of project to delete
	 * @return true if succeed
	 */
	public synchronized boolean deleteProjectCompletelyById(final String projectId) throws Exception {
		for (int i = 0; i < projects.size(); i++) {
			if (projects.get(i).uniqueName.equals(projectId)) {
				if (!deleteFilesInProjectCompletely(projects.get(i).filenames)) return false;
				projects.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * deactivates the project by id
	 *
	 * @param projectId - id of project to delete
	 * @return true if succeed
	 */
	public synchronized boolean deleteProjectById(final String projectId) throws Exception {
		for (ProjectInfo info : projects) {
			if (info.uniqueName.equals(projectId)) {
				deactivateFiles(info.filenames);
				info.isActive = false;
				info.lastChangeDate = new Date();
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns all files from the project
	 */
	public synchronized ArrayList<ByteArrayInputStream> getFilesFromProjectById(final String projectId) throws Exception {
		ProjectInfo project = findProjectById(projectId);
		if (!project.isActive) return null;
		if (project == null || project.filenames.isEmpty()) {
			return null;
		}
		ArrayList<ByteArrayInputStream> files = new ArrayList<ByteArrayInputStream>();
		for (String name : project.filenames) {
			files.add(getFileByID(name));
		}
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i) == null) files.remove(i);
		}
		return files;
	}

	/**
	 * Return a file from the project
	 */
	public synchronized ByteArrayInputStream getFileFromProjectById(final String filename, final String projectId) throws Exception {
		ProjectInfo project = findProjectById(projectId);
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
