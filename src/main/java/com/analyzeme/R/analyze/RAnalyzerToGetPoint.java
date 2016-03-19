package com.analyzeme.R.analyze;

import com.analyzeme.R.facade.RFacade;
import com.analyzeme.analyze.Point;

import java.io.ByteArrayInputStream;

/**
 * Created by lagroffe on 19.03.2016 21:20
 */
public class RAnalyzerToGetPoint {

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
	Point runScript(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
		Point result = RFacade.runScriptToGetPoint(rScriptName, rScript, userId, projectId);
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
	Point runScript(String rScriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		Point result = RFacade.runScriptToGetPoint(rScriptName, rScript, jsonData);
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
	Point runScript(String rScriptId, int userId, String projectId) throws Exception {
		Point result = RFacade.runScriptToGetPoint(rScriptId, userId, projectId);
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
	Point runScript(String rScriptId, String jsonData) throws Exception {
		Point result = RFacade.runScriptToGetPoint(rScriptId, jsonData);
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
	Point runCommand(String rCommand, int userId, String projectId) throws Exception {
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
	Point runCommand(String rCommand, String jsonData) throws Exception {
		Point result = RFacade.runCommandToGetPoint(rCommand, jsonData);
		return result;
	}
}
