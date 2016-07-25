package com.analyzeme.data.resolvers;

import com.analyzeme.data.DataSet;
import com.analyzeme.repository.UsersRepository;
import com.analyzeme.repository.projects.ProjectInfo;

public class FileInRepositoryResolver implements IDataSetResolver {
    private String[] params;

    public void setProject(final int userId, final String projectId) throws IllegalArgumentException {
        if (userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        this.params = new String[2];
        this.params[0] = Integer.toString(userId);
        this.params[1] = projectId;
    }

    public DataSet getDataSet(final String referenceName) throws Exception {
        if (referenceName == null || referenceName.equals("")) {
            throw new IllegalArgumentException();
        }
        ProjectInfo project = UsersRepository.findUser(Integer.parseInt(params[0])).getProjects().findProjectById(params[1]);
        return project.getDataSetByReferenceName(referenceName);
    }
}
