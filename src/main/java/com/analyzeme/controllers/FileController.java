package com.analyzeme.controllers;

import com.analyzeme.repository.FileRepository;
import com.analyzeme.repository.ProjectInfo;
import com.analyzeme.repository.UserInfo;
import com.analyzeme.repository.UsersRepository;
import com.analyzeme.streamreader.StreamToString;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
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
    public void doPost(@PathVariable("user_id") int userId, @PathVariable("project_id") String projectUniqueName,
                       @RequestParam(value = "file") MultipartFile multipartFile,
                       HttpServletResponse response) throws IOException {
        try {
            String responseToJS;
            String fileName = multipartFile.getOriginalFilename();

            UsersRepository.repo.checkInitializationAndCreate();
            UserInfo user = UsersRepository.repo.findUser(userId);
            if (user == null) {
                throw new Exception();
            }
            ProjectInfo project = user.projects.findProject(projectUniqueName);
            if (project == null) {
                throw new Exception();
            }

            //part, filename, projectName, username (IN THIS ORDER)
            String[] param = {fileName, project.projectName, user.login};
            //String[] param = {fileName, "default", "guest"};
            responseToJS = UsersRepository.repo.persist(multipartFile, param);

            //Set responseHeaders "Data" and "fileName";
            response.setHeader("fileName", responseToJS);
            ByteArrayInputStream file = FileRepository.repo.getFileByID(responseToJS);
            String Data = StreamToString.ConvertStream(file);
            response.setHeader("Data", Data);

            response.setCharacterEncoding("UTF-32");
            response.getWriter().write("unique name: " + responseToJS);
        } catch (IOException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/upload/demo", method = RequestMethod.POST,
            headers = {"content-type= multipart/form-data"})
    public void doPost(@RequestParam(value = "file") MultipartFile multipartFile,
                       HttpServletResponse response) throws IOException {
        try {
            String responseToJS;
            String fileName = multipartFile.getOriginalFilename();

            UsersRepository.repo.checkInitializationAndCreate();
            UserInfo user = UsersRepository.repo.findUser("guest");
            if (user == null) {
                //login, email, password  (IN THIS ORDER)
                String[] param = {"guest", "guest@mail.sth", "1234"};
                UsersRepository.repo.newItem(param);
                user = UsersRepository.repo.findUser("guest");
            }
            user.projects.createProject("default");

            //part, filename, projectName, username (IN THIS ORDER)
            String[] param = {fileName, "default", "guest"};
            responseToJS = UsersRepository.repo.persist(multipartFile, param);

            //Set responseHeaders "Data" and "fileName";
            response.setHeader("fileName", responseToJS);
            ByteArrayInputStream file = FileRepository.repo.getFileByID(responseToJS);
            String Data = StreamToString.ConvertStream(file);
            response.setHeader("Data", Data);

            response.setCharacterEncoding("UTF-32");
            response.getWriter().write("unique name: " + responseToJS);
        } catch (IOException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
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
    public boolean doGet(@PathVariable("unique_name") String uniqueName)
            throws IOException {
        try {
            //this call deactivates file
            //to delete it completely use deleteFileByIdCompletely
            return FileRepository.repo.deleteFileById(uniqueName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
