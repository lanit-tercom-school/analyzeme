package com.analyzeme.R.facade;

import java.io.ByteArrayInputStream;

/**
 * Created by lagroffe on 13.04.2016 0:18
 */
public interface GetFromR<T> {

	/**
	 * calls R using R.facade
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file as a stream
	 * @param userId      - userId of a script creator
	 * @param projectId   - id of the project with data for script
	 * @return result
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	T runScript(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception;


	/**
	 * calls R using R.facade
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file as a stream  (RScriptName is stored in FileInfo)
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return result
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	T runScript(String rScriptId, int userId, String projectId) throws Exception;

	/**
	 * calls R using R.facade
	 *
	 * @param rCommand  - string with correct R command
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return result
	 * @throws Exception if files not found, R was impossible to call or there was in error in command
	 */
	T runCommand(String rCommand, int userId, String projectId) throws Exception;

	/**
	 * calls R using R.facade
	 *
	 * @param rCommand - string with correct R command
	 * @param jsonData - some valid data in json format for command to analyze
	 * @return result
	 * @throws Exception if R was impossible to call or there was in error in command
	 */
	T runCommand(String rCommand, String jsonData) throws Exception;
}
