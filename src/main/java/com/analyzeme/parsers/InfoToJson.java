package com.analyzeme.parsers;

import com.analyzeme.data.DataSet;
import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.projects.ProjectInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by lagroffe on 28.03.2016 18:31
 */
public class InfoToJson {
	public static JSONObject convertToJsonObject(ProjectInfo info) throws IllegalArgumentException {
		if (info == null)
			throw new IllegalArgumentException("ProjectInfo cannot be null");

		JSONObject project = new JSONObject();
		project.put("projectName", info.getProjectName());
		project.put("login", "guest");
		project.put("creationDate", info.getCreationDate().toString());
		project.put("lastChangeDate", info.getLastChangeDate().toString());
		project.put("projectId", info.getUniqueName());
		project.put("isActive", info.isActive());
		return project;
	}

	public static String convert(ProjectInfo info) throws IllegalArgumentException {
		if (info == null)
			throw new IllegalArgumentException("ProjectInfo cannot be null");

		return convertToJsonObject(info).toString();
	}

	public static String convert(ProjectInfo[] infos) {
		if (infos.length == 0)
			return "{ \"Projects\" : []}";

		JSONArray result = new JSONArray();
		for (ProjectInfo info : infos) {

			result.add(convertToJsonObject(info));
		}
		return "{ \"Projects\" : " + result.toString() + "}";
	}

	public static String convert(List<ProjectInfo> infos) {
		if (infos.isEmpty())
			return "{ \"Projects\" : []}";

		JSONArray result = new JSONArray();
		for (ProjectInfo info : infos) {
			result.add(convertToJsonObject(info));
		}
		return "{ \"Projects\" : " + result.toString() + "}";
	}

	public static JSONObject convertFileInfoToJsonObject(FileInfo info) throws IllegalArgumentException {
		if (info == null)
			throw new IllegalArgumentException("FileInfo cannot be null");

		JSONObject obj = new JSONObject();
		obj.put("nameForUser", info.getNameForUser());
		obj.put("uniqueName", info.getUniqueName());
		obj.put("uploadingDate", info.getUploadingDate().toString());
		obj.put("isActive", info.isActive());
		return obj;
	}

	public static String convertFileInfo(FileInfo info) throws IllegalArgumentException {
		if (info == null)
			throw new IllegalArgumentException("FileInfo cannot be null");

		return convertFileInfoToJsonObject(info).toString();
	}

	public static String convertFileInfo(FileInfo[] infos) {
		if (infos.length == 0)
			return "{ \"Files\" : []}";

		JSONArray result = new JSONArray();
		for (FileInfo info : infos) {

			result.add(convertFileInfoToJsonObject(info));
		}
		return "{ \"Files\" : " + result.toString() + "}";
	}

	public static String convertFileInfo(List<FileInfo> infos) {
		if (infos.isEmpty())
			return "{ \"Files\" : []}";

		JSONArray result = new JSONArray();
		for (FileInfo info : infos) {
			result.add(convertFileInfoToJsonObject(info));
		}
		return "{ \"Files\" : " + result.toString() + "}";
	}

	public static JSONObject convertDataSetToJsonObject(DataSet info) throws IllegalArgumentException {
		if (info == null)
			throw new IllegalArgumentException("DataSet cannot be null");

		JSONObject obj = new JSONObject();

		obj.put("dataname", info.getReferenceName());
		JSONArray fields = new JSONArray();
		Map<String, String> f = info.getFieldsWithNames();
		for (Map.Entry<String, String> entry : f.entrySet()) {
			JSONObject field = new JSONObject();
			field.put("fieldId", entry.getKey());
			field.put("fieldName", entry.getValue());
			fields.add(field);
		}
		obj.put("fields", fields);
		return obj;
	}

	public static String convertDataSet(DataSet info) throws IllegalArgumentException {
		if (info == null)
			throw new IllegalArgumentException("DataSet cannot be null");

		return convertDataSetToJsonObject(info).toString();
	}


	public static String convertDataSet(DataSet[] infos) {
		if (infos.length == 0)
			return "{ \"Files\" : []}";

		JSONArray result = new JSONArray();
		for (DataSet info : infos) {

			result.add(convertDataSetToJsonObject(info));
		}
		return "{ \"Files\" : " + result.toString() + "}";
	}

	public static String convertDataSet(List<DataSet> infos) {
		if (infos.isEmpty())
			return "{ \"Files\" : []}";

		JSONArray result = new JSONArray();
		for (DataSet info : infos) {
			result.add(convertDataSetToJsonObject(info));
		}
		return "{ \"Files\" : " + result.toString() + "}";
	}

	public static JSONObject convertInfoAboutFileToJsonObject(FileInfo info, DataSet data) throws IllegalArgumentException {
		if (info == null)
			throw new IllegalArgumentException("ProjectInfo cannot be null");

		JSONObject result = new JSONObject();
		result.put("nameForUser", info.getNameForUser());
		result.put("uniqueName", info.getUniqueName());
		result.put("uploadingDate", info.getUploadingDate().toString());
		result.put("isActive", info.isActive());
		JSONArray fields = new JSONArray();
		Map<String, String> f = data.getFieldsWithNames();
		for (Map.Entry<String, String> entry : f.entrySet()) {
			JSONObject field = new JSONObject();
			field.put("fieldId", entry.getKey());
			field.put("fieldName", entry.getValue());
			fields.add(field);
		}
		result.put("fields", fields);

		return result;
	}

	public static String convertInfoAboutFile(FileInfo info, DataSet data) throws IllegalArgumentException {
		if (info == null)
			throw new IllegalArgumentException("ProjectInfo cannot be null");

		return convertInfoAboutFileToJsonObject(info, data).toString();
	}

	public static String convertInfoAboutFile(List<FileInfo> infos, List<DataSet> datas) throws IllegalArgumentException {
		if (infos.size() != datas.size())
			throw new IllegalArgumentException("FileInfo should have its DataSet");
		if (infos.isEmpty())
			return "{ \"Files Infos\" : []}";

		JSONArray result = new JSONArray();
		for (int i = 0; i < infos.size(); i++) {

			result.add(convertInfoAboutFileToJsonObject(infos.get(i), datas.get(i)));
		}
		return "{ \"Files Infos\" : " + result.toString() + "}";
	}
}
