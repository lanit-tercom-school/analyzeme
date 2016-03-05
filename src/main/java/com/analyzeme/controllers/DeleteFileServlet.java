package com.analyzeme.controllers;

import com.analyzeme.repository.FileRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lagroffe on 01.03.2016 22:33
 */

@WebServlet("/DeleteFileServlet")
public class DeleteFileServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String uniqueName = request.getHeader("filename");
			//this call deactivates file
			//to delete it completely use deleteFileByIdCompletely
			response.setHeader("Success", FileRepository.repo.deleteFileById(uniqueName) ? "file deleted" : "file not deleted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
