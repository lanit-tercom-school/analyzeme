package com.analyzeme.data;

import com.analyzeme.repository.FileRepository;

import java.io.ByteArrayInputStream;

/**
 * Created by lagroffe on 30.03.2016 13:33
 */
public class TOCHANGE implements ISourceInfo {
	private String uniqueNameInRepository;

	public TOCHANGE(final String uniqueNameInRepository) throws IllegalArgumentException {
		if (uniqueNameInRepository == null || uniqueNameInRepository.equals(""))
			throw new IllegalArgumentException();
		this.uniqueNameInRepository = uniqueNameInRepository;
	}

	public ByteArrayInputStream getFileData() throws Exception {
		return FileRepository.getRepo().getFileByID(uniqueNameInRepository);
	}
}
