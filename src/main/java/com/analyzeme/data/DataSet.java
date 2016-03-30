package com.analyzeme.data;

import java.io.ByteArrayInputStream;

/**
 * Created by lagroffe on 30.03.2016 13:17
 */

public class DataSet {
	private String nameForUser;
	private ISourceInfo file;

	public DataSet(final String nameForUser, final ISourceInfo file) throws Exception {
		if (nameForUser == null || nameForUser.equals("") || file == null)
			throw new IllegalArgumentException();
		this.nameForUser = nameForUser;
		this.file = file;
	}

	public ByteArrayInputStream getData() throws Exception {
		return file.getFileData();
	}

	public String getNameForUser() {
		return nameForUser;
	}
}
