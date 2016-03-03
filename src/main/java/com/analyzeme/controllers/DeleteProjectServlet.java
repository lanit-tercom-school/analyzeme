package com.analyzeme.controllers;

/**
 * Created by lagroffe on 28.02.2016 0:11
 */

import com.analyzeme.repository.UsersRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteProjectServlet")
public class DeleteProjectServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (UsersRepository.repo.checkInitialization() == null) {
				response.setHeader("Success", "project doesn't exist");
				return;
			}
			String projectName = request.getHeader("projectName");
			//to change to deleting by id use ...projects.deleteProjectById(projectId)
			//deleteProject or deleteProjectById deactivate project and all files in it
			//to remove them completely use deleteProjectCompletely or deleteProjectCompletelyById
			boolean a = UsersRepository.repo.findUser("guest").projects.deleteProject(projectName);
			response.setHeader("Success", a ? "project deleted" : "project not deleted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
