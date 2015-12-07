package com.about.java.controllers;

/**
 * Created by Юыќур on 05.11.2015.
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
		// gets absolute path of the web application
		String appPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		StringBuilder savePath = new StringBuilder();
		savePath.append(appPath);
		savePath.append(File.separator);
		savePath.append(saveDir);
		if (!FileRepository.repo.isRootFolderInitialized()) {
			FileRepository.repo.createRootFolder(savePath.toString());
		}
		try {
			String fileName = "";
			for (Part part : request.getParts()) {
				fileName = extractFileName(part);
				Repository.FileInfo fileInRepo = FileRepository.repo.addNewFile(part, fileName, "guest");
			}
			response.setCharacterEncoding("UTF-32");
			response.getWriter().write("File " + fileName + " successfully uploaded");
		} catch (Exception ex) {
			response.getWriter().write("There was an error, please try again ");
			response.getWriter().write("Exception info: " + ex.getMessage());
		}
	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) {
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