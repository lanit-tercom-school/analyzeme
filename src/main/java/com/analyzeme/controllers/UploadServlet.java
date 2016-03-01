package com.analyzeme.controllers;

/**
 * Created by Olga on 05.11.2015.
 */

import com.analyzeme.repository.FileRepository;
import com.analyzeme.repository.UsersRepository;
import com.analyzeme.streamreader.StreamToString;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10,      // 10MB
		maxRequestSize = 1024 * 1024 * 50)   // 50MB
//@RestController
public class UploadServlet extends HttpServlet {

	/**
	 * handles file upload
	 */
	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws ServletException, IOException {
		try {
			String fileName = "";
			String responseToJS = "";
			for (Part part : request.getParts()) {
				fileName = extractFileName(part);
				UsersRepository.repo.checkInitializationAndCreate();
				if (UsersRepository.repo.findUser("guest") == null) {
					//login, email, password  (IN THIS ORDER)
					String[] param = {"guest", "guest@mail.sth", "1234"};
					UsersRepository.repo.newItem(param);
					UsersRepository.repo.newProject("guest", "default");
				}
				//part, filename, projectName, username (IN THIS ORDER)
				//String[] param = {fileName, "default", "guest"};
				//responseToJS = UsersRepository.repo.persist(part, param);

				//part, filename, projectId, username (IN THIS ORDER)
				String[] param = {fileName, "project", "guest"};
				responseToJS = UsersRepository.repo.persistByProjectId(part, param);
			}
			//Set responseHeader "Data" and "fileName";
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
	 * handles files upload
	 * gets in url user id and project id
	 * tries to find that user and his project (now just adds to default project of default user)
	 * adds new file
	 **/
//    @RequestMapping(value = "upload/{user_id}/{project_id}", method = RequestMethod.POST)
//    public void doPost(@PathVariable("user_id") int userId, @PathVariable("project_id") int projectId,
//                           @RequestBody FileInfo fileInfo,
//                               HttpServletRequest request, HttpServletResponse response) throws IOException {
//        try {
//            String fileName = "";
//            String responseToJS = "";
//
//            for (Part part : request.getParts()) {
//                fileName = extractFileName(part);
//                UsersRepository.repo.checkInitializationAndCreate();
//                UserInfo user = UsersRepository.repo.findUser(userId);
//                if (user == null) {
//                    //login, email, password  (IN THIS ORDER)
//                    String[] param = {"guest", "guest@mail.sth", "1234"};
//                    UsersRepository.repo.newItem(param);
//                    //todo here should be an error
//                }
//                ProjectInfo project = user.projects.findProject(projectId);
//                if (project == null) {
//                    //todo here should be an error too
//                }
//                //todo future variant : String[] param = {fileName, project.projectName, user.login};
//                //part, filename, projectName, username (IN THIS ORDER)
//                String[] param = {fileName, "default", "guest"};
//                responseToJS = UsersRepository.repo.persist(part, param);
//            }
//            //Set responseHeader "Data" and "fileName";
//            response.setHeader("fileName", responseToJS);
//            ByteArrayInputStream file = FileRepository.repo.getFileByID(responseToJS);
//            String Data = StreamToString.ConvertStream(file);
//            response.setHeader("Data", Data);
//
//
//            response.setCharacterEncoding("UTF-32");
//            response.getWriter().write("unique name: " + responseToJS);
//        } catch (IOException e) {
//            throw e;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) throws IOException {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}
}
