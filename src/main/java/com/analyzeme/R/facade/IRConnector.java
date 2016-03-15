package com.analyzeme.R.facade;

import java.io.ByteArrayInputStream;

/**
 * Created by lagroffe on 15.03.2016 21:45
 */

//TODO: discuss the type of return value (stream may be better)
public interface IRConnector {

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file as a stream
	 * @param userId      - userId of a script creator
	 * @param projectId   - id of the project with data for script
	 * @return json string with results (maybe png image in the future)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	String runScript(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception;

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file
	 * @param jsonData    - some valid data in json format for command to analyze
	 * @return json string with results (maybe png image in the future)
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	String runScript(String rScriptName, ByteArrayInputStream rScript, String jsonData) throws Exception;

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file as a stream  (RScriptName is stored in FileInfo)
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return json string with results (maybe png image in the future)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	String runScript(String rScriptId, int userId, String projectId) throws Exception;

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file (RScriptName is stored in FileInfo)
	 * @param jsonData  - some valid data in json format for command to analyze
	 * @return json string with results (maybe png image in the future)
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	String runScript(String rScriptId, String jsonData) throws Exception;

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rCommand  - string with correct R command
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return json string with results (maybe png image in the future)
	 * @throws Exception if files not found, R was impossible to call or there was in error in command
	 */
	String runCommand(String rCommand, int userId, String projectId) throws Exception;

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rCommand - string with correct R command
	 * @param jsonData - some valid data in json format for command to analyze
	 * @return json string with results (maybe png image in the future)
	 * @throws Exception if R was impossible to call or there was in error in command
	 */
	String runCommand(String rCommand, String jsonData) throws Exception;
}
