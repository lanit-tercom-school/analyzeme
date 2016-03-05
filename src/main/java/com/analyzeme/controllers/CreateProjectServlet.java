package com.analyzeme.controllers;

import com.analyzeme.repository.UsersRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lagroffe on 27.02.2016 23:33
 */

@WebServlet("/CreateProjectServlet")
public class CreateProjectServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String projectName = request.getHeader("projectName");
			//when other users created, CheckInitializationAndCreate() should be called from user creator only
			//now it's possible to create a default user here
			UsersRepository.repo.checkInitializationAndCreate();
			if (UsersRepository.repo.findUser("guest") == null) {
				//login, email, password
				String[] param = {"guest", "guest@mail.sth", "1234"};
				UsersRepository.repo.newItem(param);
			}
			//now username is used here
			//to use userId just change "guest" to int with it
			String project = UsersRepository.repo.newProject("guest", projectName);
			if (project == null) {
				response.setHeader("Success", "project was not created");
			} else {
				response.setHeader("projectId", project);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
