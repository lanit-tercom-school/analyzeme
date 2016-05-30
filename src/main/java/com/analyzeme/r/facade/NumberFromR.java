package com.analyzeme.r.facade;

import java.io.ByteArrayInputStream;

/**
 * Created by lagroffe on 19.03.2016 21:19
 */

public class NumberFromR implements GetFromR<Double> {

	/**
	 * calls r using r.facade
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .r file as a stream
	 * @param userId      - userId of a script creator
	 * @param projectId   - id of the project with data for script
	 * @return double result
	 * @throws Exception if files not found, r was impossible to call or there was in error in script
	 */
	public Double runScript(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals("")) {
			throw new IllegalArgumentException();
		}
		double result = RScriptManager.runScriptToGetNumber(rScriptName, rScript, userId, projectId);
		return result;
	}


	/**
	 * calls r using r.facade
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return double result
	 * @throws Exception if files not found, r was impossible to call or there was in error in script
	 */
	public Double runScript(String rScriptId, int userId, String projectId) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
			throw new IllegalArgumentException();
		}
		double result = RScriptManager.runScriptToGetNumber(rScriptId, userId, projectId);
		return result;
	}

	/**
	 * calls r using r.facade
	 *
	 * @param rCommand  - string with correct r command
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return double result
	 * @throws Exception if files not found, r was impossible to call or there was in error in command
	 */
	public Double runCommand(String rCommand, int userId, String projectId) throws Exception {
		if (rCommand == null || rCommand.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
			throw new IllegalArgumentException();
		}
		double result = RFacade.runCommandToGetNumber(rCommand, userId, projectId);
		return result;
	}

	/**
	 * calls r using r.facade
	 *
	 * @param rCommand - string with correct r command
	 * @param jsonData - some valid data in json format for command to analyze
	 * @return double result
	 * @throws Exception if r was impossible to call or there was in error in command
	 */
	public Double runCommand(String rCommand, String jsonData) throws Exception {
		if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals("")) {
			throw new IllegalArgumentException();
		}
		double result = RFacade.runCommandToGetNumber(rCommand, jsonData);
		return result;
	}
}
