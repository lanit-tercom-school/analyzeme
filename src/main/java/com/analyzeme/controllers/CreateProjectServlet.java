package com.analyzeme.controllers;

/**
 * Created by lagroffe on 27.02.2016 23:33
 */

import com.analyzeme.repository.UsersRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CreateProjectServlet")
public class CreateProjectServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String projectName = request.getParameter("projectName");
			UsersRepository.repo.checkInitializationAndCreate();
			boolean a = UsersRepository.repo.newProject("guest", projectName);
			response.setHeader("Success", a ? "project created" : "project not created");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
