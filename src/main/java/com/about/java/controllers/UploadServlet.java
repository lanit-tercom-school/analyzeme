package com.about.java.controllers;

/**
 * Created by Olga on 05.11.2015.
 */

import Repository.FileRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10,      // 10MB
		maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class UploadServlet extends HttpServlet {
	/**
	 * Name of the directory where uploaded files will be saved, relative to
	 * the web application directory.
	 */
	private static final String saveDir = "uploadFiles";

	/**
	 * handles file upload
	 */

	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws ServletException, IOException {
		String responseToJS = "";
		// gets absolute path of the web application
		String appPath = "";
		try {
			appPath = request.getServletContext().getRealPath("");
		}
		catch (Exception e) {
			responseToJS = "Impossible to get real path";
		}
		// constructs path of the directory to save uploaded file
		StringBuilder savePath = new StringBuilder();
		savePath.append(appPath);
		savePath.append(File.separator);
		savePath.append(saveDir);
		if (!FileRepository.repo.isRootFolderInitialized()) {
			try {
				FileRepository.repo.createRootFolder(savePath.toString());
			}
			catch (Exception e) {
				responseToJS = "Impossible to initialize root folder";
			}
			if(!FileRepository.repo.isRootFolderInitialized()) {
				responseToJS = "Impossible to initialize root folder";
			}
		}
		try {
			String fileName = "";
			for (Part part : request.getParts()) {
				try {
					fileName = extractFileName(part);
				}
				catch (Exception ex) {
					responseToJS += ", cannot extract filename";
				}
				if(fileName==null || fileName.equals(""))
				{
					responseToJS += ", cannot extract filename";
				}
				try {
					responseToJS += FileRepository.repo.addNewFile(part, fileName, "guest");
				}
				catch (Exception e) {
					responseToJS += ", some mistakes in addNewFile";
				}
			}
			response.setCharacterEncoding("UTF-32");
			if(responseToJS.equals("")) {
				response.getWriter().write("File " + fileName + " was successfully uploaded");
			}
			else {
				response.getWriter().write(responseToJS);
			}
		} catch (Exception ex) {
			response.getWriter().write("Unhandled exception");
			//response.getWriter().write("There was an error, please try again ");
			//response.getWriter().write("Exception info: " + ex.getMessage());
		}
	}

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