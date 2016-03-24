package com.analyzeme.R.analyze;

import com.analyzeme.R.ScriptManager.RScriptManager;
import com.analyzeme.R.facade.RFacade;

import java.io.ByteArrayInputStream;

/**
 * Created by lagroffe on 19.03.2016 21:03
 */

public class DefaultFromR {

	/**
	 * calls R using R.facade
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file as a stream
	 * @param userId      - userId of a script creator
	 * @param projectId   - id of the project with data for script
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static String runScript(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
		String result = RScriptManager.runScript(rScriptName, rScript, userId, projectId);
		return result;
	}

	/**
	 * calls R using R.facade
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file
	 * @param jsonData    - some valid data in json format for command to analyze
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static String runScript(String rScriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		String result = RScriptManager.runScript(rScriptName, rScript, jsonData);
		return result;
	}

	/**
	 * calls R using R.facade
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file as a stream  (RScriptName is stored in FileInfo)
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static String runScript(String rScriptId, int userId, String projectId) throws Exception {
		String result = RScriptManager.runScript(rScriptId, userId, projectId);
		return result;
	}

	/**
	 * calls R using R.facade
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file (RScriptName is stored in FileInfo)
	 * @param jsonData  - some valid data in json format for command to analyze
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static String runScript(String rScriptId, String jsonData) throws Exception {
		String result = RScriptManager.runScript(rScriptId, jsonData);
		return result;
	}

	/**
	 * calls R using R.facade
	 *
	 * @param rCommand  - string with correct R command
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in command
	 */
	public static String runCommand(String rCommand, int userId, String projectId) throws Exception {
		String result = RFacade.runCommand(rCommand, userId, projectId);
		return result;
	}

	/**
	 * calls R using R.facade
	 *
	 * @param rCommand - string with correct R command
	 * @param jsonData - some valid data in json format for command to analyze
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in command
	 */
	public static String runCommand(String rCommand, String jsonData) throws Exception {
		String result = RFacade.runCommand(rCommand, jsonData);
		return result;
	}
}
