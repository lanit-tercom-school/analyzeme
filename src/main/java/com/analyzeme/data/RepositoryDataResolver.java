package com.analyzeme.data;

import com.analyzeme.repository.FileInfo;
import com.analyzeme.repository.UsersRepository;

/**
 * Created by lagroffe on 30.03.2016 13:17
 */

public class RepositoryDataResolver implements IDataSetResolver {
	private String[] params;

	public void setProject(int userId, String projectId) throws IllegalArgumentException {
		if (userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		this.params = new String[2];
		this.params[0] = Integer.toString(userId);
		this.params[1] = projectId;
	}

	public DataSet getDataSet(String name) throws Exception {
		FileInfo info = UsersRepository.getRepo().findFile(name, params);
		ISourceInfo file = new FileInRepositoryInfo(info.getUniqueName());
		return new DataSet(name, file);
	}
}
