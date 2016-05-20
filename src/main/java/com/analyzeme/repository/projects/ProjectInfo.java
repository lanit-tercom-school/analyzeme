package com.analyzeme.repository.projects;

import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.filerepository.FileRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileSystemException;
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
    private Date creationDate;
    @JsonProperty("lastChangeDate")
    private Date lastChangeDate;
    //should be refactored to the list of datasets
    private List<String> filenames;
    @JsonProperty("isActive")
    private boolean isActive = true;


    /**
     * returns all unique names of files
     *
     * @return
     */
    public List<String> returnAllNamesOfActiveFiles() throws IOException {
        if (getFilenames().isEmpty()) return null;
        ArrayList<String> names = new ArrayList<String>();
        for (String name : getFilenames()) {
            if (FileRepository.getRepo().findFileById(name).isActive()) names.add(name);
        }
        return names;
    }

    /**
     * @return json like [{"uniqueName": ..., "nameForUser": ...}, {"uniqueName": ..., "nameForUser": ...}]
     * @throws IOException
     */
    public String returnActiveFilesForList() throws IOException {
        if (getFilenames().isEmpty()) return "[]";
        JSONArray result = new JSONArray();
        for (String name : getFilenames()) {
            FileInfo info = FileRepository.getRepo().findFileById(name);
            if(info.isActive()) {
                JSONObject file = new JSONObject();
                file.put("uniqueName", info.getUniqueName());
                file.put("nameForUser", info.getNameForUser());
                result.add(file);
            }
        }
        return result.toString();
    }

    /**
     * @param name       - name of a project (should be unique for user)
     * @param uniqueName - name in repo for future usage
     * @throws IOException
     */
    ProjectInfo(final String name, final String uniqueName) throws IOException {
        if (name == null || name.equals("")) throw new IllegalArgumentException();
        this.setProjectName(name);
        if (uniqueName == null || uniqueName.equals("")) throw new IllegalArgumentException();
        this.uniqueName = uniqueName;
        //default ctor fills Date with current info (number of milliseconds since the Unix epoch (first moment of 1970) in the UTC time zone)
        creationDate = new Date();
        setLastChangeDate(new Date());
        filenames = new ArrayList<String>();
    }

    public String addNewFile(MultipartFile file, String filename) throws Exception {
        if (filename == null || filename.equals("") || file == null) {
            throw new IllegalArgumentException();
        }
        String nameInRepo = FileRepository.getRepo().persist(file, filename);
        if (nameInRepo == null || nameInRepo.equals("")) {
            throw new NullPointerException("Error while loading the file");
        }
        filenames.add(nameInRepo);
        setLastChangeDate(new Date());
        return nameInRepo;
    }

    /**
     * @param filename
     * @param file
     * @return
     * @throws Exception
     */
    public String addNewFile(String filename, String file) throws Exception {
        if (filename == null || filename.equals("") || file == null) {
            throw new IllegalArgumentException();
        }
        String nameInRepo = FileRepository.getRepo().persist(file, filename);
        if (nameInRepo == null || nameInRepo.equals("")) {
            throw new FileSystemException(filename);
        }
        filenames.add(nameInRepo);
        setLastChangeDate(new Date());
        return nameInRepo;
    }

    public String addNewFileForTests(ByteArrayInputStream part, String filename) throws Exception {
        if (filename == null || filename.equals("") || part == null) {
            throw new IllegalArgumentException();
        }
        String nameInRepo = FileRepository.getRepo().persist(part, filename);
        if (nameInRepo == null || nameInRepo.equals("")) {
            throw new FileSystemException(filename);
        }
        filenames.add(nameInRepo);
        setLastChangeDate(new Date());
        return nameInRepo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) throws IOException {
        if (projectName == null || projectName.equals("")) throw new IllegalArgumentException();
        this.projectName = projectName;
    }

    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Date lastChangeDate) throws IOException {
        if (lastChangeDate == null) throw new IllegalArgumentException();
        this.lastChangeDate = lastChangeDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public List<String> getFilenames() {
        return filenames;
    }
}
