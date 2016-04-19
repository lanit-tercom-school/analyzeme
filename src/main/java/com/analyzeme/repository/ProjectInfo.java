package com.analyzeme.repository;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lagroffe on 17.02.2016 18:40
 */

public class ProjectInfo {
	private String projectName;
	private String uniqueName;
	private Date creationDate;
	private Date lastChangeDate;
	private List<String> filenames;
	private boolean isActive = true;


	/**
	 * returns all unique names of files
	 *
	 * @return
	 */
	public List<String> returnAllNames() throws IOException {
		if (getFilenames().isEmpty()) return null;
		ArrayList<String> names = new ArrayList<String>();
		for (String name : getFilenames()) {
			if (FileRepository.getRepo().findFileById(name).isActive()) names.add(name);
		}
		return names;
	}

	/**
	 * @param name       - name of a project (should be unique for user)
	 * @param uniqueName - name in repo for future usage
	 * @throws IOException
	 */
	ProjectInfo(final String name, final String uniqueName) throws IOException {
		if (name == null || name.equals("")) throw new IllegalArgumentException();
		this.setProjectName(name);
		if (uniqueName == null || uniqueName.equals("")) throw new IllegalArgumentException();
		this.uniqueName = uniqueName;
		//default ctor fills Date with current info (number of milliseconds since the Unix epoch (first moment of 1970) in the UTC time zone)
		creationDate = new Date();
		setLastChangeDate(new Date());
		filenames = new ArrayList<String>();
	}

	public String addNewFile(MultipartFile file, String filename) throws Exception {
		if (filename == null || filename.equals("") || file == null) {
			throw new IllegalArgumentException();
		}
		String nameInRepo = FileRepository.getRepo().persist(file, filename);
		if (nameInRepo == null || nameInRepo.equals("")) {
			throw new FileSystemException(filename);
		}
		filenames.add(nameInRepo);
		setLastChangeDate(new Date());
		return nameInRepo;
	}

	public String addNewFileForTests(ByteArrayInputStream part, String filename) throws Exception {
		if (filename == null || filename.equals("") || part == null) {
			throw new IllegalArgumentException();
		}
		String nameInRepo = FileRepository.getRepo().persist(part, filename);
		if (nameInRepo == null || nameInRepo.equals("")) {
			throw new FileSystemException(filename);
		}
		filenames.add(nameInRepo);
		setLastChangeDate(new Date());
		return nameInRepo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) throws IOException {
		if (projectName == null || projectName.equals("")) throw new IllegalArgumentException();
		this.projectName = projectName;
	}

	public Date getLastChangeDate() {
		return lastChangeDate;
	}

	public void setLastChangeDate(Date lastChangeDate) throws IOException {
		if (lastChangeDate == null) throw new IllegalArgumentException();
		this.lastChangeDate = lastChangeDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public List<String> getFilenames() {
		return filenames;
	}
}
