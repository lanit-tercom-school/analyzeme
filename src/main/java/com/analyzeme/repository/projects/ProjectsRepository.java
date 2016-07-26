package com.analyzeme.repository.projects;

import com.analyzeme.data.dataset.DataSet;
import com.analyzeme.repository.filerepository.FileUploader;
import com.analyzeme.repository.filerepository.TypeOfFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagroffe on 17.02.2016 18:40
 */

public class ProjectsRepository {
    private List<ProjectInfo> projects;
    private int counter = 0;

    //info about files for demo project
    private static final String DEMO_FILE_1 =
            "{\"Data\":[{ \"x\": \"0\",\"y\": \"0\" },{ \"x\": \"1\",\"y\": \"1\" }," +
                    "{\"x\": \"2\",\"y\": \"2\"},{ \"x\": \"3\",\"y\": \"3\" }," +
                    "{ \"x\": \"4\",\"y\": \"4\" },{ \"x\": \"5\",\"y\": \"5\" }," +
                    "{ \"x\": \"6\",\"y\": \"6\" },{ \"x\": \"7\",\"y\": \"7\" }," +
                    "{ \"x\": \"8\",\"y\": \"8\" },{ \"x\": \"9\",\"y\": \"9\" }," +
                    "{ \"x\": \"10\",\"y\": \"10\" }]}";
    private static final String DEMO_FILE_1_NAME =
            "0_10.json";
    private static final String DEMO_FILE_1_REF_NAME =
            "0_10";
    private static final String DEMO_FILE_2 =
            "{\"Data\":[{ \"x\": \"1\",\"y\": \"1\"},{\"x\": \"20\",\"y\": \"20\"}," +
                    "{\"x\": \"40\", \"y\": \"40\"},{\"x\": \"60\",\"y\": \"60\"}," +
                    " {\"x\": \"80\",\"y\": \"80\"},{\"x\": \"100\",\"y\": \"100\"}]}";
    private static final String DEMO_FILE_2_NAME =
            "lineal.json";
    private static final String DEMO_FILE_2_REF_NAME =
            "lineal";

    /**
     * creates demo project
     */
    private void createDemoProject() throws Exception {
        String projectName = "demo";
        String uniqueName = "demo";
        ProjectInfo info = new ProjectInfo(projectName, uniqueName);
        DataSet demoFile1set = FileUploader.upload(DEMO_FILE_1,
                DEMO_FILE_1_NAME, DEMO_FILE_1_REF_NAME,
                TypeOfFile.SIMPLE_JSON);
        DataSet demoFile2set = FileUploader.upload(DEMO_FILE_2,
                DEMO_FILE_2_NAME, DEMO_FILE_2_REF_NAME,
                TypeOfFile.SIMPLE_JSON);
        info.persist(demoFile1set);
        info.persist(demoFile2set);
        projects.add(info);
    }

    /**
     * creates empty repository
     */
    public ProjectsRepository() throws Exception {
        projects = new ArrayList<ProjectInfo>();
        createDemoProject();
    }

    /**
     * finds project for given user
     *
     * @param projectId
     * @return
     */
    public ProjectInfo findProjectById(final String projectId) throws IOException {
        if (projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException(
                    "ProjectsRepository findProjectById(): wrong id");
        }
        for (ProjectInfo project : projects) {
            if (project.getUniqueName().equals(projectId)) {
                return project;
            }
        }
        return null;
    }

    /**
     * creates id for project (unique for user)
     *
     * @return -id for project (unique for user)
     */
    private String createUniqueName() {
        if (counter == 0) return "project";
        String uniqueName = "project_" + Integer.toString(counter);
        return uniqueName;
    }

    /**
     * creates empty project
     */
    public String createProject(final String projectName) throws Exception {
        if (projectName == null || projectName.equals("")) {
            throw new IllegalArgumentException(
                    "ProjectsRepository createProject(): empty projectName");
        }
        String uniqueName = createUniqueName();
        ProjectInfo info = new ProjectInfo(projectName, uniqueName);
        projects.add(info);
        counter++;
        return uniqueName;
    }

    /**
     * deactivates the project by id
     *
     * @param projectId - id of project to delete
     * @return true if succeed
     */
    public synchronized boolean deactivateProjectById(final String projectId)
            throws Exception {
        if (projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException(
                    "ProjectsRepository deactivateProjectById(): empty projectId");
        }
        for (ProjectInfo info : projects) {
            if (info.getUniqueName().equals(projectId)) {
                info.deactivateFiles();
                info.setIsActive(false);
                return true;
            }
        }
        return false;
    }

    /**
     * @param file - dataset
     */
    public synchronized void persist(final DataSet file,
                                     final String projectId) throws Exception {
        if (projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException(
                    "ProjectsRepository persist(): wrong projectId");
        }
        if (file == null) {
            throw new IllegalArgumentException(
                    "ProjectsRepository persist(): empty entity of DataSet");
        }
        ProjectInfo info = findProjectById(projectId);
        if (info == null) {
            throw new IllegalArgumentException(
                    "ProjectsRepository persist(): project with this id does not exist");
        }
        info.persist(file);
    }

    /**
     * Return file if referenceName is given
     *
     * @param referenceName
     * @return dataset (or null if not found)
     */
    public synchronized DataSet getByReferenceName(
            final String projectId, final String referenceName)
            throws Exception {
        if (referenceName == null || referenceName.equals("")) {
            throw new IllegalArgumentException(
                    "ProjectsRepository getByReferenceName(): wrong reference name");
        }
        return findProjectById(projectId).getDataSetByReferenceName(referenceName);
    }


    public List<ProjectInfo> getProjects() {
        return projects;
    }

    /**
     * returns all ids of projects
     *
     * @return
     */
    public List<String> returnAllProjectsIds() {
        if (projects.isEmpty()) {
            return null;
        }
        ArrayList<String> names = new ArrayList<String>();
        for (ProjectInfo info : projects) {
            names.add(info.getUniqueName());
        }
        return names;
    }

    /**
     * returns all ids of active projects
     *
     * @return
     */
    public List<String> returnAllActiveProjectsIds() {
        if (projects.isEmpty()) {
            return null;
        }
        ArrayList<String> names = new ArrayList<String>();
        for (ProjectInfo info : projects) {
            if (info.isActive()) names.add(info.getUniqueName());
        }
        return names;
    }

}
