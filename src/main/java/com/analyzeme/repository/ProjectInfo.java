package com.analyzeme.repository;

import org.springframework.web.multipart.MultipartFile;

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
	public String uniqueName;
	public Date creationDate;
	public Date lastChangeDate;
	public ArrayList<String> filenames;
	public boolean isActive = true;


	/**
	 * returns all unique names of files
	 *
	 * @return
	 */
	public ArrayList<String> returnAllNames() {
		if (filenames.isEmpty()) return null;
		ArrayList<String> names = new ArrayList<String>();
		for (String name : filenames) {
			if (FileRepository.repo.findFileById(name).isActive) names.add(name);
		}
		return names;
	}

	/**
	 * @param name       - name of a project (should be unique for user)
	 * @param uniqueName - name in repo for future usage
	 * @throws IOException
	 */
	ProjectInfo(final String name, final String uniqueName) throws IOException {
		if (name == null || name.equals("")) throw new IOException();
		this.projectName = name;
		if (uniqueName == null || uniqueName.equals("")) throw new IOException();
		this.uniqueName = uniqueName;
		//default ctor fills Date with current info (number of milliseconds since the Unix epoch (first moment of 1970) in the UTC time zone)
		creationDate = new Date();
		lastChangeDate = new Date();
		filenames = new ArrayList<String>();
	}

	public String addNewFile(MultipartFile file, String filename) throws Exception {
		if (filename == null || filename.equals("") || file == null) {
			throw new DataFormatException();
		}
		String nameInRepo = FileRepository.repo.addNewFile(file, filename);
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
		String nameInRepo = FileRepository.repo.addNewFileForTests(part, filename);
		if (nameInRepo == null || nameInRepo.equals("")) {
			throw new FileSystemException(filename);
		}
		filenames.add(nameInRepo);
		lastChangeDate = new Date();
		return nameInRepo;
	}
}
