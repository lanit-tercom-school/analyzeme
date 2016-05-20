package com.analyzeme.data.resolvers;

import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.ISourceInfo;
import com.analyzeme.data.resolvers.sourceinfo.JsonPointFileInRepositoryInfo;
import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.UsersRepository;
import com.analyzeme.repository.projects.ProjectInfo;
import org.eclipse.jetty.server.Authentication;

/**
 * Created by lagroffe on 30.03.2016 13:17
 */

public class JsonPointRepositoryDataResolver implements IDataSetResolver {
    private String[] params;

    public void setProject(final int userId, final String projectId) throws IllegalArgumentException {
        if (userId == 0 || projectId == null || projectId.equals(""))
            throw new IllegalArgumentException();
        this.params = new String[2];
        this.params[0] = Integer.toString(userId);
        this.params[1] = projectId;
    }

    public DataSet createDataSet(final String referenceName) throws Exception {
        ProjectInfo project = UsersRepository.getRepo().findUser(Integer.parseInt(params[0])).getProjects().findProjectById(params[1]);
        FileInfo info = project.getByReferenceName(referenceName);
        ISourceInfo file = new JsonPointFileInRepositoryInfo(info.getUniqueName());
        DataSet set = new DataSet(referenceName, file);
        set.addField("x");
        set.addField("y");
        return set;
    }
}
