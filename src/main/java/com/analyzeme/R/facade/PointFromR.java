package com.analyzeme.R.facade;

import com.analyzeme.R.facade.RScriptManager;
import com.analyzeme.R.facade.RFacade;
import com.analyzeme.analyze.Point;

import java.io.ByteArrayInputStream;

/**
 * Created by lagroffe on 19.03.2016 21:20
 */

public class PointFromR {

	/**
	 * calls R using R.facade
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file as a stream
	 * @param userId      - userId of a script creator
	 * @param projectId   - id of the project with data for script
	 * @return one point
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static Point runScript(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		Point result = RScriptManager.runScriptToGetPoint(rScriptName, rScript, userId, projectId);
		return result;
	}

	/**
	 * calls R using R.facade
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file
	 * @param jsonData    - some valid data in json format for command to analyze
	 * @return one point
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	public static Point runScript(String rScriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		Point result = RScriptManager.runScriptToGetPoint(rScriptName, rScript, jsonData);
		return result;
	}


	/**
	 * calls R using R.facade
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file as a stream  (RScriptName is stored in FileInfo)
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return one point
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static Point runScript(String rScriptId, int userId, String projectId) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		Point result = RScriptManager.runScriptToGetPoint(rScriptId, userId, projectId);
		return result;
	}

	/**
	 * calls R using R.facade
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file (RScriptName is stored in FileInfo)
	 * @param jsonData  - some valid data in json format for command to analyze
	 * @return one point
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	public static Point runScript(String rScriptId, String jsonData) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		Point result = RScriptManager.runScriptToGetPoint(rScriptId, jsonData);
		return result;
	}

	/**
	 * calls R using R.facade
	 *
	 * @param rCommand  - string with correct R command
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return one point
	 * @throws Exception if files not found, R was impossible to call or there was in error in command
	 */
	public static Point runCommand(String rCommand, int userId, String projectId) throws Exception {
		if (rCommand.equals("") || rCommand == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		Point result = RFacade.runCommandToGetPoint(rCommand, userId, projectId);
		return result;
	}

	/**
	 * calls R using R.facade
	 *
	 * @param rCommand - string with correct R command
	 * @param jsonData - some valid data in json format for command to analyze
	 * @return one point
	 * @throws Exception if R was impossible to call or there was in error in command
	 */
	public static Point runCommand(String rCommand, String jsonData) throws Exception {
		if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		Point result = RFacade.runCommandToGetPoint(rCommand, jsonData);
		return result;
	}
}
