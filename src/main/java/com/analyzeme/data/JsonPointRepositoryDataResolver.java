package com.analyzeme.data;

import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.UsersRepository;

/**
 * Created by lagroffe on 30.03.2016 13:17
 */

public class JsonPointRepositoryDataResolver implements IDataSetResolver {
	private String[] params;

	//now unique name is used here until reference name will be implemented

	public void setProject(int userId, String projectId) throws IllegalArgumentException {
		if (userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		this.params = new String[2];
		this.params[0] = Integer.toString(userId);
		this.params[1] = projectId;
	}

	public DataSet getDataSet(String name) throws Exception {
		FileInfo info = UsersRepository.getRepo().findFileById(name, params);
		ISourceInfo file = new JsonPointFileInRepositoryInfo(info.getUniqueName());
		DataSet set = new DataSet(name, file);
		set.addField("x");
		set.addField("y");
		return set;
	}
}
