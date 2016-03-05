package com.analyzeme.controllers;

import com.analyzeme.repository.ProjectInfo;
import com.analyzeme.repository.UsersRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lagroffe on 01.03.2016 22:39
 * for issue #54
 */

@WebServlet("/ReturnProjectsInfoServlet")
public class ReturnProjectsInfoServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (UsersRepository.repo.checkInitialization() == null) {
				response.setHeader("Success", "no projects exists");
			}
			JSONArray result = new JSONArray();
			//logic below should be changed when other users will be created
			//this will return info about all projects
			//to get info only about active add if(info.isActive)
			for (ProjectInfo info : UsersRepository.repo.findUser("guest").projects.projects) {
				JSONObject project = new JSONObject();
				project.put("projectName", info.projectName);
				project.put("login", "guest");
				project.put("lastChangeDate", info.lastChangeDate);
				project.put("projectId", info.uniqueName);
				result.add(project);
			}
			response.setHeader("Success", result.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
