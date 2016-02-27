package com.analyzeme.controllers;

import com.analyzeme.repository.UsersRepository;

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
			ArrayList<String> projects = UsersRepository.repo.findUser("guest").projects.returnAllNames();
			response.setHeader("ProjectNames", projects.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
