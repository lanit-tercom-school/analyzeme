package com.analyzeme.controllers;

import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.JsonPointRepositoryDataResolver;
import com.analyzeme.parsers.InfoToJson;
import com.analyzeme.repository.*;
import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.filerepository.FileRepository;
import com.analyzeme.repository.projects.ProjectInfo;
import com.analyzeme.streamreader.StreamToString;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Olga on 05.11.2015.
 */

@RestController
public class FileController {

	/**
	 * handles files upload
	 * gets in url user id and project id
	 * tries to find that user and his project (now just adds to default project of default user)
	 * adds new file
	 **/
	@RequestMapping(value = "/upload/{user_id}/{project_id}", method = RequestMethod.POST,
			headers = {"content-type= multipart/form-data"})
	public void doPost(@PathVariable("user_id") int userId, @PathVariable("project_id") String projectUniqueName,
					   @RequestParam(value = "file") MultipartFile multipartFile,
					   HttpServletResponse response) throws IOException {
		try {
			String responseToJS;
			String fileName = multipartFile.getOriginalFilename();

			UsersRepository.getRepo().checkInitializationAndCreate();
			UserInfo user = UsersRepository.getRepo().findUser(userId);
			ProjectInfo project = user.getProjects().findProjectById(projectUniqueName);
			if (project == null) {
				throw new Exception();
			}

			//filename, projectId, userId
			String[] param = {fileName, projectUniqueName, Integer.toString(userId)};
			responseToJS = UsersRepository.getRepo().persistByIds(multipartFile, param);

			//Set responseHeaders "Data" and "fileName";
			response.setHeader("fileName", responseToJS);
			ByteArrayInputStream file = FileRepository.getRepo().getFileByID(responseToJS);
			String Data = StreamToString.ConvertStream(file);
			response.setHeader("Data", Data);

			response.setCharacterEncoding("UTF-32");
			response.getWriter().write("unique name: " + responseToJS);
		} catch (IOException e) {
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/upload/demo", method = RequestMethod.POST,
			headers = {"content-type= multipart/form-data"})
	public void doPost(@RequestParam(value = "file") MultipartFile multipartFile,
					   HttpServletResponse response) throws IOException {
		try {
			String responseToJS;
			String fileName = multipartFile.getOriginalFilename();

			UsersRepository.getRepo().checkInitializationAndCreate();
			try {
				UsersRepository.getRepo().findUser("guest");
			} catch (IllegalArgumentException e) {
				//login, email, password  (IN THIS ORDER)
				String[] param = {"guest", "guest@mail.sth", "1234"};
				UsersRepository.getRepo().newItem(param);
			}

			//part, filename, projectName, username (IN THIS ORDER)
			String[] param = {fileName, "demo", "guest"};
			responseToJS = UsersRepository.getRepo().persist(multipartFile, param);

			//Set responseHeaders "Data" and "fileName";
			response.setHeader("fileName", responseToJS);
			ByteArrayInputStream file = FileRepository.getRepo().getFileByID(responseToJS);
			String Data = StreamToString.ConvertStream(file);
			response.setHeader("Data", Data);

			response.setCharacterEncoding("UTF-32");
			response.getWriter().write("unique name: " + responseToJS);
		} catch (IOException e) {
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * deletes file by unique name
	 *
	 * @param uniqueName
	 * @return true if file was deleted successfully
	 * false otherwise
	 * IOException
	 */
	@RequestMapping(value = "/file/{unique_name}/delete", method = RequestMethod.DELETE)
	public boolean doGet(@PathVariable("unique_name") String uniqueName)
			throws IOException {
		try {
			//this call deactivates file
			//to delete it completely use deleteFileByIdCompletely
			return FileRepository.getRepo().deleteFileById(uniqueName);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * returns info about file
	 * example : {"uniqueName":"0_10.json","nameForUser":"0_10.json","isActive":"true","uploadingDate":"Wed Apr 20 18:25:28 MSK 2016"}
	 */
	@RequestMapping(value = "/file/{unique_name}/getInfo", method = RequestMethod.GET)
	public String getFileInfo(@PathVariable("unique_name") String uniqueName) throws Exception {
		if (uniqueName == null || uniqueName.equals(""))
			throw new IllegalArgumentException();
		FileInfo info = FileRepository.getRepo().findFileById(uniqueName);
		if (info == null)
			throw new NullPointerException("File not found");
		return InfoToJson.convertFileInfo(info);
	}

	/**
	 * returns info about fields
	 * example : {"dataname":"0_10.json","fields":[{"fieldName":"x","fieldId":"x"},{"fieldName":"y","fieldId":"y"}]}
	 */
	@RequestMapping(value = "/file/{user_id}/{project_id}/{nameForUser}/getFields", method = RequestMethod.GET)
	public String getFileFields(@PathVariable("user_id") int userId, @PathVariable("project_id") String projectId, @PathVariable("nameForUser") String filename) throws Exception {
		if (filename == null || filename.equals("") || userId <= 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		JsonPointRepositoryDataResolver res = new JsonPointRepositoryDataResolver();
		res.setProject(userId, projectId);
		DataSet data = res.createDataSet(filename);
		if (data == null)
			throw new NullPointerException("File not found");
		return InfoToJson.convertDataSet(data);
	}

	/**
	 * returns full info about file
	 * example : {"uniqueName":"0_10.json","nameForUser":"0_10.json","isActive":"true","fields":[{"fieldName":"x","fieldId":"x"},{"fieldName":"y","fieldId":"y"}],"uploadingDate":"Wed Apr 20 18:25:28 MSK 2016"}
	 */
	@RequestMapping(value = "/file/{user_id}/{project_id}/{nameForUser}/getFullInfo", method = RequestMethod.GET)
	public String getFullFileInfo(@PathVariable("user_id") int userId, @PathVariable("project_id") String projectId, @PathVariable("nameForUser") String filename) throws Exception {
		if (filename == null || filename.equals("") || userId <= 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		JsonPointRepositoryDataResolver res = new JsonPointRepositoryDataResolver();
		res.setProject(userId, projectId);
		DataSet data = res.createDataSet(filename);
		if (data == null)
			throw new NullPointerException("File not found");
		FileInfo info = FileRepository.getRepo().findFileById(data.getFile().getToken());
		if (info == null)
			throw new NullPointerException("getInfo does not work correctly");

		return InfoToJson.convertInfoAboutFile(info, data);
	}
}
