package com.analyzeme.controllers;

import com.analyzeme.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lagroffe on 27.02.2016 23:44
 */
@WebServlet("/GetProjectsNamesServlet")
public class GetProjectsNamesServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (UsersRepository.repo.checkInitialization() == null) {
				response.setHeader("Success", "no projects exists");
				return;
			}
			//this returns all projects (deleted included)
			ArrayList<String> projects = UsersRepository.repo.findUser("guest").projects.returnAllProjectsNames();

			ObjectMapper mapper = new ObjectMapper();
			//arraylist to json string (not tested)
			String result = mapper.writeValueAsString(projects);
			response.setHeader("ProjectNames", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
