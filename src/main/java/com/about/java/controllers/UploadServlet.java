package com.about.java.controllers;

import Repository.FileRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10,      // 10MB
		maxRequestSize = 1024 * 1024 * 50)   // 50MB
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
				responseToJS = FileRepository.repo.addNewFile(part, fileName, "guest");
			}
			response.setCharacterEncoding("UTF-32");
			response.getWriter().write("{\"nameToWrite\": \"" + responseToJS + "\"}");
			//response.getWriter().write("File " + fileName + " was successfully uploaded");
		} catch (IOException ex) {
			throw ex;
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
