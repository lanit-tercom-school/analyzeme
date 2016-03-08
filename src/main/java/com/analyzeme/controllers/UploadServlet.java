package com.analyzeme.controllers;

import com.analyzeme.repository.FileRepository;
import com.analyzeme.repository.ProjectInfo;
import com.analyzeme.repository.UserInfo;
import com.analyzeme.repository.UsersRepository;
import com.analyzeme.streamreader.StreamToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

//import javafx.application.Application;
//import com.sun.glass.ui.Application;

/**
 * Created by Olga on 05.11.2015.
 */

@Controller
//@RequestMapping(value = )
//@WebServlet("/UploadServlet")
//@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//		maxFileSize = 1024 * 1024 * 10,      // 10MB
//		maxRequestSize = 1024 * 1024 * 50)   // 50MB
//@RestController
public class UploadServlet {

    /**
     * handles file upload
     */
//	protected void doPost(HttpServletRequest request,
//						  HttpServletResponse response) throws ServletException, IOException {
//		try {
//			String fileName = "";
//			String responseToJS = "";
//			for (Part part : request.getParts()) {
//				fileName = extractFileName(part);
//				//when other users created, CheckInitializationAndCreate() should be called from user creator only
//				//now it's possible to create a default user with default project here
//				UsersRepository.repo.checkInitializationAndCreate();
//				if (UsersRepository.repo.findUser("guest") == null) {
//					//login, email, password
//					String[] param = {"guest", "guest@mail.sth", "1234"};
//					UsersRepository.repo.newItem(param);
//					UsersRepository.repo.newProject("guest", "default");
//				}
//				//in the future this block should be deleted, create project before downloading files
//				if (UsersRepository.repo.findUser("guest").projects.findProject("default") == null) {
//					UsersRepository.repo.newProject("guest", "default");
//				}
//				//should use part, filename, projectId, userId - use persistByIds in the future
//				//part, filename, projectName, username
//				String[] param = {fileName, "default", "guest"};
//				responseToJS = UsersRepository.repo.persist(part, param);
//			}
//			//Set responseHeaders "Data" and "fileName";
//			response.setHeader("fileName", responseToJS);
//			ByteArrayInputStream file = FileRepository.repo.getFileByID(responseToJS);
//			String Data = StreamToString.ConvertStream(file);
//			response.setHeader("Data", Data);
//
//			response.setCharacterEncoding("UTF-32");
//			response.getWriter().write("unique name: " + responseToJS);
//		} catch (IOException e) {
//			throw e;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
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
                       HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            String responseToJS = "";
            String fileName = multipartFile.getOriginalFilename();
            //multipartFile.

            UsersRepository.repo.checkInitializationAndCreate();
            //todo future variant UserInfo user = UsersRepository.repo.findUser(userId);
            UserInfo user = UsersRepository.repo.findUser("guest");
            if (user == null) {
                //login, email, password  (IN THIS ORDER)
                String[] param = {"guest", "guest@mail.sth", "1234"};
                UsersRepository.repo.newItem(param);
                //todo here should be an error
            }
            //user.projects = new
            ProjectInfo project = user.projects.findProject(projectUniqueName);
            if (project == null) {
                //todo here should be an error too
            }
            //todo future variant : String[] param = {fileName, project.projectName, user.login};
            //part, filename, projectName, username (IN THIS ORDER)
            String[] param = {fileName, "default", "guest"};
            InputStream s = multipartFile.getInputStream();
            responseToJS = UsersRepository.repo.persist(multipartFile, param);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
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
}
//
//    /**
//     * Extracts file name from HTTP header content-disposition
//     */
//    private String extractFileName(Part part) throws IOException {
//        String contentDisp = part.getHeader("content-disposition");
//        String[] items = contentDisp.split(";");
//        for (String s : items) {
//            if (s.trim().startsWith("filename")) {
//                return s.substring(s.indexOf("=") + 2, s.length() - 1);
//            }
//        }
//        return "";
//    }
//}
