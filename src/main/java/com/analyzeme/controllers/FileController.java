package com.analyzeme.controllers;

import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.FileInRepositoryResolver;
import com.analyzeme.parsers.InfoToJson;
import com.analyzeme.repository.*;
import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.filerepository.FileRepository;
import com.analyzeme.repository.filerepository.FileUploader;
import com.analyzeme.repository.filerepository.TypeOfFile;
import com.analyzeme.repository.projects.ProjectInfo;
import com.analyzeme.streamreader.StreamToString;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Olga on 05.11.2015.
 */

@RestController
public class FileController {

    /**
     * handles files upload
     * gets in url user id and project id
     * tries to find that user and his project (now just adds to default project of default user)
     * adds new file
     **/
    @RequestMapping(value = "/upload/{user_id}/{project_id}", method = RequestMethod.POST,
            headers = {"content-type= multipart/form-data"})
    public void doPost(@PathVariable("user_id") final int userId, @PathVariable("project_id") final String projectUniqueName,
                       @RequestParam(value = "file") final MultipartFile multipartFile,
                       HttpServletResponse response) throws IOException {
        try {
            String fileName = multipartFile.getOriginalFilename();
            //TODO: now type checking is only formal, change with #202
            TypeOfFile type = null;
            if (fileName.endsWith(".json")) {
                type = TypeOfFile.SIMPLE_JSON;
            } else if (fileName.endsWith(".csv")) {
                type = TypeOfFile.CSV;
            } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx") ) {
                type = TypeOfFile.EXCEL;
            }

            //TODO: after users added, change next line to UsersRepository.checkInitialization();
            UsersRepository.checkInitializationAndCreate();
            UserInfo user = UsersRepository.findUser(userId);
            ProjectInfo project = user.getProjects().findProjectById(projectUniqueName);
            if (project == null) {
                throw new IllegalArgumentException("FileController doPost(): project does not exists");
            }

            DataSet set = FileUploader.upload(multipartFile, fileName, fileName, type);
            project.persist(set);
            response.setHeader("fileName", set.getReferenceName());
            String Data = StreamToString.convertStream(set.getData());
            response.setHeader("Data", Data);

            response.setCharacterEncoding("UTF-32");
            response.getWriter().write("unique name: " + set.getReferenceName());
        } catch (IOException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/upload/demo", method = RequestMethod.POST,
            headers = {"content-type= multipart/form-data"})
    public void doPost(@RequestParam(value = "file") final MultipartFile multipartFile,
                       HttpServletResponse response) throws IOException {
        try {
            String fileName = multipartFile.getOriginalFilename();
            //TODO: now type checking is only formal, change with #202
            TypeOfFile type = null;
            if (fileName.endsWith(".json")) {
                type = TypeOfFile.SIMPLE_JSON;
            } else if (fileName.endsWith(".csv")) {
                type = TypeOfFile.CSV;
            }

            UsersRepository.checkInitializationAndCreate();
            try {
                UsersRepository.findUser("guest");
            } catch (IllegalArgumentException e) {
                //login, email, password  (IN THIS ORDER)
                String[] param = {"guest", "guest@mail.sth", "1234"};
                UsersRepository.newItem(param);
            }

            DataSet set = FileUploader.upload(multipartFile, fileName, fileName, type);
            UsersRepository.findUser("guest").getProjects().findProjectById("demo").persist(set);

            response.setHeader("fileName", set.getReferenceName());
            String Data = StreamToString.convertStream(set.getData());
            response.setHeader("Data", Data);

            response.setCharacterEncoding("UTF-32");
            response.getWriter().write("unique name: " + set.getFile().getToken());
        } catch (IOException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param userId        - id of a user who uploaded this data
     * @param projectId     - project with this data
     * @param referenceName - reference name (now id in repository is used here)
     * @param response
     * @return String with json like  {"Data": [{"x":"1", "y":"1"}, ...]}
     * @throws IOException
     */
    @RequestMapping(value = "/file/{user_id}/{project_id}/{reference_name}/data", method = RequestMethod.GET)
    public String getDataByReferenceName(@PathVariable("user_id") final int userId, @PathVariable("project_id") final String projectId, @PathVariable("reference_name") final String referenceName, HttpServletResponse response)
            throws IOException {
        try {
            FileInRepositoryResolver res = new FileInRepositoryResolver();
            res.setProject(userId, projectId);
            DataSet file = res.getDataSet(referenceName);
            String Data = StreamToString.convertStream(file.getData());
            return Data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * deletes file by unique name
     *
     * @param uniqueName
     * @return true if file was deleted successfully
     * false otherwise
     * IOException
     */
    @RequestMapping(value = "/file/{unique_name}/delete", method = RequestMethod.DELETE)
    public boolean doGet(@PathVariable("unique_name") final String uniqueName)
            throws IOException {
        try {
            //this call deactivates file
            //to delete it completely use deleteFileByIdCompletely
            return FileRepository.getRepo().deleteFileById(uniqueName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * returns info about file
     * example : {"uniqueName":"0_10.json","nameForUser":"0_10.json","isActive":"true","uploadingDate":"Wed Apr 20 18:25:28 MSK 2016"}
     */
    @RequestMapping(value = "/file/{reference_name}/getInfo", method = RequestMethod.GET)
    public String getFileInfo(@PathVariable("reference_name") final String referenceName) throws Exception {
        if (referenceName == null || referenceName.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInfo info = FileRepository.getRepo().findFileById(referenceName);
        if (info == null) {
            throw new IllegalArgumentException("File not found");
        }
        return InfoToJson.convertFileInfo(info);
    }

    /**
     * returns info about fields
     * example : {"dataname":"0_10.json","fields":[{"fieldName":"x","fieldId":"x"},{"fieldName":"y","fieldId":"y"}]}
     */
    @RequestMapping(value = "/file/{user_id}/{project_id}/{reference}/getFields", method = RequestMethod.GET)
    public String getFileFields(@PathVariable("user_id") final int userId, @PathVariable("project_id") final String projectId, @PathVariable("reference") final String reference) throws Exception {
        if (reference == null || reference.equals("") || userId <= 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        DataSet set = UsersRepository.findUser(userId).getProjects().findProjectById(projectId).getDataSetByReferenceName(reference);
        if (set == null) {
            throw new IllegalArgumentException("File not found");
        }
        return InfoToJson.convertDataSet(set);
    }

    /**
     * returns full info about file
     * example : {"uniqueName":"0_10.json","nameForUser":"0_10.json","isActive":"true","fields":[{"fieldName":"x","fieldId":"x"},{"fieldName":"y","fieldId":"y"}],"uploadingDate":"Wed Apr 20 18:25:28 MSK 2016"}
     */
    @RequestMapping(value = "/file/{user_id}/{project_id}/{reference}/getFullInfo", method = RequestMethod.GET)
    public String getFullFileInfo(@PathVariable("user_id") final int userId, @PathVariable("project_id") final String projectId, @PathVariable("reference") final String reference) throws Exception {
        if (reference == null || reference.equals("") || userId <= 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        DataSet set = UsersRepository.findUser(userId).getProjects().findProjectById(projectId).getDataSetByReferenceName(reference);
        if (set == null) {
            throw new IllegalArgumentException("File not found");
        }

        return InfoToJson.convertInfoAboutFile(FileRepository.getRepo().findFileById(set.getFile().getToken()), set);
    }
}
