package com.analyzeme.repository.projects;

import com.analyzeme.data.dataset.DataSet;
import com.analyzeme.data.resolvers.sourceinfo.DataRepositoryInfo;
import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.filerepository.FileRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lagroffe on 17.02.2016 18:40
 */

@JsonAutoDetect
public class ProjectInfo {
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("uniqueName")
    private String uniqueName;
    @JsonProperty("creationDate")
    private final Date creationDate;
    @JsonProperty("lastChangeDate")
    private Date lastChangeDate;
    private final List<DataSet> datasets;
    @JsonProperty("isActive")
    private boolean isActive = true;
    private BigInteger counterOfDatasets;


    /**
     * @param name       - name of a project (should be unique for user)
     * @param uniqueName - name in repo for future usage
     * @throws IOException
     */
    ProjectInfo(final String name, final String uniqueName) throws IOException {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("ProjectInfo ctor: wrong name");
        }
        this.setProjectName(name);
        if (uniqueName == null || uniqueName.equals("")) {
            throw new IllegalArgumentException("ProjectInfo ctor: wrong id");
        }
        this.uniqueName = uniqueName;
        //default ctor fills Date with current info (
        // number of milliseconds since the Unix epoch (first moment of 1970) in the UTC time zone)
        creationDate = new Date();
        lastChangeDate = new Date();
        datasets = new ArrayList<DataSet>();
        counterOfDatasets = (BigInteger.ZERO);
    }

    /**
     * @return json like [{"uniqueName": ..., "nameForUser": ...}, {"uniqueName": ..., "nameForUser": ...}]
     * @throws IOException
     */
    public String returnActiveFilesForList() throws Exception {
        if (getReferenceNames().isEmpty()) {
            return "[]";
        }
        JSONArray result = new JSONArray();
        for (DataSet set : datasets) {
            if (set.getFile().getClass().
                    equals(DataRepositoryInfo.class)) {
                FileInfo info = FileRepository.getRepo().
                        findFileById(set.getFile().getToken());
                if (info != null && info.isActive()) {
                    JSONObject file = new JSONObject();
                    file.put("uniqueName", info.getUniqueName());
                    file.put("nameForUser", info.getNameForUser());
                    result.add(file);
                }
            }
        }
        return result.toString();
    }

    /**
     * returns all reference names of datasets
     *
     * @return
     */
    public List<String> returnAllNamesOfActiveFiles() throws Exception {
        if (getReferenceNames().isEmpty()) {
            return null;
        }
        ArrayList<String> names = new ArrayList<String>();
        for (String name : getReferenceNames()) {
            FileInfo info = FileRepository.getRepo().findFileById(name);
            if (info != null && info.isActive()) names.add(name);
        }
        return names;
    }

    /**
     * increments counter of datasets (never decrement it)
     */
    private void changeCounter() {
        counterOfDatasets.add(BigInteger.ONE);
    }

    /**
     * connects DataSet to the project
     *
     * @param file - DataSet of source
     * @throws Exception
     */
    public void persist(DataSet file) throws Exception {
        if (file == null) {
            throw new IllegalArgumentException(
                    "ProjectInfo persist(): empty entity of DataSet");
        }
        changeCounter();
        file.setIdInProject(counterOfDatasets);
        datasets.add(file);
        lastChangeDate = new Date();
    }

    /**
     * @return - referenceNames of datasets
     * @throws Exception
     */
    public List<String> getReferenceNames() throws Exception {
        List<String> filenames = new ArrayList<String>();
        for (DataSet set : datasets) {
            filenames.add(set.getReferenceName());
        }
        return filenames;
    }

    /**
     * @param referenceName - special name of data source given by user while uploading
     * @return - dataset with this reference name
     * @throws Exception
     */
    public DataSet getDataSetByReferenceName(final String referenceName) throws Exception {
        if (referenceName == null || referenceName.equals("")) {
            throw new IllegalArgumentException(
                    "ProjectInfo getDataSetByReferenceName(): wrong referenceName");
        }
        for (DataSet set : datasets) {
            if (set.getReferenceName().equals(referenceName)) {
                return set;
            }
        }
        return null;
    }

    /**
     * deactivates all files in filerepository connected to the project
     */
    public void deactivateFiles() throws Exception {
        for (DataSet set : datasets) {
            if (set.getFile().getClass().equals(DataRepositoryInfo.class))  {
                FileInfo info = FileRepository.getRepo().findFileById(set.getFile().getToken());
                if (info != null) {
                    info.setIsActive(false);
                }
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //STANDARD GETTERS/SETTERS
    //----------------------------------------------------------------------------------------------

    /**
     * @return - name of project given by user
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName - name of project given by user
     * @throws IOException
     */
    public void setProjectName(String projectName) throws IOException {
        if (projectName == null || projectName.equals("")) {
            throw new IllegalArgumentException(
                    "ProjectInfo setProjectName(): projectName should not be empty");
        }
        this.projectName = projectName;
        lastChangeDate = new Date();
    }

    /**
     * @return - Date of last update for project
     */
    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    /**
     * @return - if project was deactivated, false, if not, true
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @param isActive - if project was deactivated, false, if not, true
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
        lastChangeDate = new Date();
    }

    /**
     * @return - id of project (unique for user)
     */
    public String getUniqueName() {
        return uniqueName;
    }

    /**
     * @return - date of creation of the project
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @return - data source connected to the project
     */
    public List<DataSet> getDatasets() {
        return datasets;
    }
}
