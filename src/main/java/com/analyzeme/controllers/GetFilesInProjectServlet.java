package com.analyzeme.controllers;

/**
 * Created by lagroffe on 27.02.2016 23:44
 */

import com.analyzeme.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/GetFilesInProjectServlet")
public class GetFilesInProjectServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (UsersRepository.repo.checkInitialization() == null) {
				response.setHeader("Success", "no projects exists");
			}
			String projectName = request.getHeader("projectName");

			//this line will return all filenames in project, including temporary deleted files
			ArrayList<String> filenames = UsersRepository.repo.findUser("guest").projects.findProject(projectName).filenames;
			//to get only active files use:
			//ArrayList<String> filenames = UsersRepository.repo.findUser("guest").projects.findProject(projectName).returnAllNames();

			ObjectMapper mapper = new ObjectMapper();
			//arraylist to json string (not tested)
			String result = mapper.writeValueAsString(filenames);
			response.setHeader("Success", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
