package com.analyzeme.controllers;

import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.FileInRepositoryResolver;
import com.analyzeme.parsers.InfoToJson;
import com.analyzeme.repository.UserInfo;
import com.analyzeme.repository.UsersRepository;
import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.filerepository.FileRepository;
import com.analyzeme.repository.filerepository.FileUploader;
import com.analyzeme.repository.filerepository.TypeOfFile;
import com.analyzeme.repository.projects.ProjectInfo;
import com.analyzeme.streamreader.StreamToString;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

@RestController
public class FileController {
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.controllers.FileController");
    }

    /**
     * handles files upload
     * gets in url user id and project id
     * tries to find that user and his project (now just adds to default project of default user)
     * adds new file
     **/
    @RequestMapping(value = "/upload/{user_id}/{project_id}", method = RequestMethod.POST,
            headers = {"content-type= multipart/form-data"})
    public void doPost(@PathVariable("user_id") final int userId,
                       @PathVariable("project_id") final String projectUniqueName,
                       @RequestParam(value = "file") final MultipartFile multipartFile,
                       HttpServletResponse response) throws IOException {
        LOGGER.debug("doPost(user, project): method started");
        try {
            String fileName = multipartFile.getOriginalFilename();
            LOGGER.trace("doPost(user, project): filename is extracted");

            //next line is TEMPORARY (before JS is ready)
            String referenceName = fileName;
            if (!checkReferenceName(fileName, referenceName)) {
                LOGGER.info("doPost(user, project): reference name is incorrect");
                throw new IllegalArgumentException("Wrong referenceName");
            }
            LOGGER.trace("doPost(user, project): reference name is checked");

            InputStream fileStream = multipartFile.getInputStream();
            String mime = checkContentOfFile(fileName, fileStream);
            TypeOfFile type = getType(mime);
            LOGGER.debug("doPost(user, project): type of file is checked");

            //TODO: after users added, change next line to UsersRepository.checkInitialization();
            UsersRepository.checkInitializationAndCreate();
            UserInfo user = UsersRepository.findUser(userId);
            ProjectInfo project = user.getProjects().findProjectById(projectUniqueName);
            if (project == null) {
                LOGGER.info("doPost(user, project): project does not exist");
                throw new IllegalArgumentException(
                        "FileController doPost(): project does not exists");
            }

            DataSet set = FileUploader.upload(multipartFile, fileName, fileName, type);
            LOGGER.debug("doPost(user, project): uploading completed");
            project.persist(set);
            LOGGER.debug("doPost(user, project): file in project");

            response.setHeader("fileName", set.getReferenceName());
            response.setHeader("Data", StreamToString.convertStream(set.getData()));
            LOGGER.debug("doPost(user, project): method finished");
        } catch (IOException e) {
            LOGGER.info("doPost(user, project): ", e.toString());
            throw e;
        } catch (Exception ex) {
            LOGGER.warn("doPost(user, project): ", ex.toString());
        }
    }

    @RequestMapping(value = "/upload/demo", method = RequestMethod.POST,
            headers = {"content-type= multipart/form-data"})
    public void doPost(@RequestParam(value = "file") final MultipartFile multipartFile,
                       HttpServletResponse response) throws IOException {
        LOGGER.debug("doPost(): method started");
        try {
            String fileName = multipartFile.getOriginalFilename();
            LOGGER.trace("doPost(): filename is extracted");

            //next line is TEMPORARY (before JS is ready)
            String referenceName = fileName;
            if (!checkReferenceName(fileName, referenceName)) {
                LOGGER.info("doPost(): reference name is incorrect");
                throw new IllegalArgumentException("Wrong referenceName");
            }
            LOGGER.trace("doPost(): reference name is checked");

            InputStream fileStream = multipartFile.getInputStream();
            String mime = checkContentOfFile(fileName, fileStream);
            TypeOfFile type = getType(mime);
            LOGGER.debug("doPost(): type of file is checked");

            UsersRepository.checkInitializationAndCreate();
            try {
                UsersRepository.findUser("guest");
                LOGGER.debug("doPost(): guest user is found");
            } catch (IllegalArgumentException e) {
                //login, email, password  (IN THIS ORDER)
                String[] param = {"guest", "guest@mail.sth", "1234"};
                UsersRepository.newItem(param);
                LOGGER.debug("doPost(); guest user is created");
            }

            DataSet set = FileUploader.upload(multipartFile, fileName, fileName, type);
            LOGGER.debug("doPost(): uploading completed");
            UsersRepository.findUser("guest").getProjects().findProjectById("demo").persist(set);
            LOGGER.debug("doPost(): file in project");

            response.setHeader("fileName", set.getReferenceName());
            response.setHeader("Data", StreamToString.convertStream(set.getData()));
            LOGGER.debug("doPost(): method finished");
        } catch (IOException e) {
            LOGGER.info("doPost(): ", e.toString());
            throw e;
        } catch (Exception ex) {
            LOGGER.warn("doPost(): ", ex.toString());
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
    @RequestMapping(value = "/file/{user_id}/{project_id}/{reference_name}/data",
            method = RequestMethod.GET)
    public String getDataByReferenceName(@PathVariable("user_id") final int userId,
                                         @PathVariable("project_id") final String projectId,
                                         @PathVariable("reference_name") final String referenceName,
                                         HttpServletResponse response)
            throws Exception {
        LOGGER.debug("getDataByReferenceName(): method started");
        try {
            FileInRepositoryResolver res = new FileInRepositoryResolver();
            res.setProject(userId, projectId);
            DataSet file = res.getDataSet(referenceName);
            LOGGER.debug("getDataByReferenceName(): dataset is found");
            String Data = StreamToString.convertStream(file.getData());
            LOGGER.debug("getDataByReferenceName(): dataset is converted");
            return Data;
        } catch (IOException ex) {
            LOGGER.warn("getDataByReferenceName(): ", ex.toString());
            throw ex;
        } catch (Exception e) {
            LOGGER.warn("getDataByReferenceName(): ", e.toString());
            throw e;
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
    @RequestMapping(value = "/file/{unique_name}/delete",
            method = RequestMethod.DELETE)
    public boolean doDelete(@PathVariable("unique_name") final String uniqueName)
            throws IOException {
        LOGGER.debug("doDelete(): method started");
        try {
            //this call deactivates file
            //to delete it completely use deleteFileByIdCompletely
            return FileRepository.getRepo().deleteFileById(uniqueName);
        } catch (Exception e) {
            LOGGER.info("doDelete(): ", e.toString());
            return false;
        }
    }

    /**
     * returns info about file
     * example : {"uniqueName":"0_10.json","nameForUser":"0_10.json","isActive":"true","uploadingDate":"Wed Apr 20 18:25:28 MSK 2016"}
     */
    @RequestMapping(value = "/file/{reference_name}/getInfo",
            method = RequestMethod.GET)
    public String getFileInfo(
            @PathVariable("reference_name") final String referenceName) throws Exception {
        LOGGER.debug("getFileInfo(): method started");
        if (referenceName == null || referenceName.equals("")) {
            LOGGER.info("getFileInfo(): argument is empty");
            throw new IllegalArgumentException();
        }
        FileInfo info = FileRepository.getRepo()
                .findFileById(referenceName);
        if (info == null) {
            LOGGER.info("getFileInfo(): file is not found");
            throw new IllegalArgumentException("File not found");
        }
        LOGGER.debug("getFileInfo(): file is found");
        return InfoToJson.convertFileInfo(info);
    }

    /**
     * returns info about fields
     * example : {"dataname":"0_10.json","fields":[{"fieldName":"x","fieldId":"x"},{"fieldName":"y","fieldId":"y"}]}
     */
    @RequestMapping(
            value = "/file/{user_id}/{project_id}/{reference}/getFields",
            method = RequestMethod.GET)
    public String getFileFields(@PathVariable("user_id") final int userId,
                                @PathVariable("project_id") final String projectId,
                                @PathVariable("reference") final String reference) throws Exception {
        LOGGER.debug("getFileFields(): method started");
        if (reference == null || reference.equals("")
                || userId <= 0 || projectId == null
                || projectId.equals("")) {
            LOGGER.info("getFileFields(): incorrect argument");
            throw new IllegalArgumentException();
        }
        DataSet set = UsersRepository.findUser(userId)
                .getProjects().findProjectById(projectId)
                .getDataSetByReferenceName(reference);
        if (set == null) {
            LOGGER.info("getFileFields(): file is not found");
            throw new IllegalArgumentException("File not found");
        }
        LOGGER.debug("getFileFields(): file is found");
        return InfoToJson.convertDataSet(set);
    }

    /**
     * returns full info about file
     * example : {"uniqueName":"0_10.json","nameForUser":"0_10.json","isActive":"true","fields":[{"fieldName":"x","fieldId":"x"},{"fieldName":"y","fieldId":"y"}],"uploadingDate":"Wed Apr 20 18:25:28 MSK 2016"}
     */
    @RequestMapping(
            value = "/file/{user_id}/{project_id}/{reference}/getFullInfo",
            method = RequestMethod.GET)
    public String getFullFileInfo(@PathVariable("user_id") final int userId,
                                  @PathVariable("project_id") final String projectId,
                                  @PathVariable("reference") final String reference) throws Exception {
        LOGGER.debug("getFullFileInfo(): method started");
        if (reference == null || reference.equals("")
                || userId <= 0 || projectId == null
                || projectId.equals("")) {
            LOGGER.info("getFullFileInfo(): incorrect argument");
            throw new IllegalArgumentException();
        }
        DataSet set = UsersRepository.findUser(userId)
                .getProjects().findProjectById(projectId)
                .getDataSetByReferenceName(reference);
        if (set == null) {
            LOGGER.info("getFullFileInfo(): file is not found");
            throw new IllegalArgumentException("File not found");
        }

        LOGGER.debug("getFullFileInfo(): file is found");
        return InfoToJson.convertInfoAboutFile(
                FileRepository.getRepo()
                        .findFileById(set.getFile().getToken()), set);
    }

    private boolean checkReferenceName(final String fileName,
                                       final String referenceName)
            throws IllegalArgumentException {
        LOGGER.debug("checkReferenceName(): method started");
        if (fileName == null || fileName.equals("")
                || referenceName == null || referenceName.equals("")) {
            LOGGER.info("checkReferenceName(): empty argument");
            throw new IllegalArgumentException(
                    "checkReferenceName: empty parameter");
        }
        if (Pattern.matches("[a-zA-Z.0-9_]+", referenceName)) {
            LOGGER.debug("checkReferenceName(): reference name is correct");
            return true;
        }
        LOGGER.info("checkReferenceName(): reference name is not correct");
        return false;
    }

    public String checkContentOfFile(final String fileName, InputStream inputStream) throws IOException {
        LOGGER.debug("checkContentOfFile(): method started");
        AutoDetectParser parser = new AutoDetectParser();
        Detector detector = parser.getDetector();
        Metadata metadata = new Metadata();
        metadata.add(Metadata.RESOURCE_NAME_KEY, fileName);
        MediaType mediaType = detector.detect(inputStream, metadata);
        LOGGER.debug("checkContentOfFile(): mime type is detected");
        return mediaType.toString();
    }

    public TypeOfFile getType(final String mime) throws Exception {
        if (mime.equals("application/json")) {
            return TypeOfFile.SIMPLE_JSON;
        } else if (mime.equals("text/csv")) {
            return TypeOfFile.CSV;
        } else if (mime.equals("application/vnd.ms-excel") ||
                mime.equals("application/msexcel") ||
                mime.equals("application/x-msexcel") ||
                mime.equals("application/x-ms-excel") ||
                mime.equals("application/x-excel") ||
                mime.equals("application/x-dos_ms_excel") ||
                mime.equals("application/xls") ||
                mime.equals("application/x-xlsl") ||
                mime.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return TypeOfFile.EXCEL;
        } else {
            LOGGER.info("getType(): type of file is not supported");
            throw new IllegalArgumentException("This type of file is not supported");
        }
    }
}
