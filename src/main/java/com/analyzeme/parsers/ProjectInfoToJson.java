package com.analyzeme.parsers;

import com.analyzeme.repository.ProjectInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by lagroffe on 28.03.2016 18:31
 */
public class ProjectInfoToJson {
	public static String convert(ProjectInfo[] infos) {
		JSONArray result = new JSONArray();
		for (ProjectInfo info : infos) {
			JSONObject project = new JSONObject();
			project.put("projectName", info.getProjectName());
			project.put("login", "guest");
			project.put("creationDate", info.getCreationDate());
			project.put("lastChangeDate", info.getLastChangeDate());
			project.put("projectId", info.getUniqueName());
			result.add(project);
		}
		return "{ \"Projects\" : " + result.toString() + "}";
	}

	public static String convert(List<ProjectInfo> infos) {
		JSONArray result = new JSONArray();
		for (ProjectInfo info : infos) {
			JSONObject project = new JSONObject();
			project.put("projectName", info.getProjectName());
			project.put("login", "guest");
			project.put("creationDate", info.getCreationDate());
			project.put("lastChangeDate", info.getLastChangeDate());
			project.put("projectId", info.getUniqueName());
			result.add(project);
		}
		return "{ \"Projects\" : " + result.toString() + "}";
	}
}
