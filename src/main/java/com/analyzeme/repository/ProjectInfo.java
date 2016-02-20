package com.analyzeme.repository;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * Created by lagroffe on 17.02.2016 18:40
 */
public class ProjectInfo {

	public String projectName;
	public Date creationDate;
	public Date lastChangeDate;
	public String username;
	public ArrayList<String> filenames;

	/**
	 * @param name     - name of a project (shoud be unique for user)
	 * @param username - user name of project owner
	 * @throws IOException
	 */
	ProjectInfo(final String name, final String username) throws IOException {
		if (name == null || name.equals("")) throw new IOException();
		this.projectName = name;
		if (username == null || username.equals("")) throw new IOException();
		this.username = username;
		//default ctor fills Date with current info (number of milliseconds since the Unix epoch (first moment of 1970) in the UTC time zone)
		creationDate = new Date();
		lastChangeDate = new Date();
		filenames = new ArrayList<String>();
	}

	public String addNewFile(Part part, String filename) throws Exception {
		if (filename == null || filename.equals("") || part == null) {
			throw new DataFormatException();
		}
		String nameInRepo = FileRepository.repo.addNewFile(part, filename, username);
		if (nameInRepo == null || nameInRepo.equals("")) {
			throw new FileSystemException(filename);
		}
		filenames.add(nameInRepo);
		lastChangeDate = new Date();
		return nameInRepo;
	}

	public String addNewFileForTests(ByteArrayInputStream part, String filename) throws Exception {
		if (filename == null || filename.equals("") || part == null) {
			throw new DataFormatException();
		}
		String nameInRepo = FileRepository.repo.addNewFileForTests(part, filename, username);
		if (nameInRepo == null || nameInRepo.equals("")) {
			throw new FileSystemException(filename);
		}
		filenames.add(nameInRepo);
		lastChangeDate = new Date();
		return nameInRepo;
	}
}
