package com.analyzeme.R.facade;


import com.analyzeme.analyze.Point;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by lagroffe on 25.03.2016 1:26
 */

/**
 * TODO: in the future manager should be able to:
 * TODO: - store some information about currently running scripts and maybe opmtimize parameters in Settings
 * TODO: - give some information about currently running scripts
 */

public class RScriptManager {

	/*----------------------------------------------------------------------------------------------------------------------------
	* Different types of script call
	*
	* Differs by:
	* - return value:
	* 		a) default - json string
	* 		b) double
	* 		c) Point
	* 		d) List<Point>
	* - way to point to the script:
	* 		a) as a stream
	* 		b) as file from repository
	* - way to give data:
	* 		a) as json string
	* 		b) as files from repository
	*----------------------------------------------------------------------------------------------------------------------------
     */


	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file as a stream
	 * @param userId      - userId of a script creator
	 * @param projectId   - id of the project with data for script
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	static String runScript(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScript(rScriptName, rScript, userId, projectId);
	}

	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file
	 * @param jsonData    - some valid data in json format for command to analyze
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	static String runScript(String rScriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScript(rScriptName, rScript, jsonData);
	}

	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file as a stream  (RScriptName is stored in FileInfo)
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	static String runScript(String rScriptId, int userId, String projectId) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScript(rScriptId, userId, projectId);
	}

	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file (RScriptName is stored in FileInfo)
	 * @param jsonData  - some valid data in json format for command to analyze
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	static String runScript(String rScriptId, String jsonData) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScript(rScriptId, jsonData);
	}


	/*******************************
	 * To get number
	 * *****************************
	 */

	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file as a stream
	 * @param userId      - userId of a script creator
	 * @param projectId   - id of the project with data for script
	 * @return double result
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	static double runScriptToGetNumber(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScriptToGetNumber(rScriptName, rScript, userId, projectId);
	}


	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file
	 * @param jsonData    - some valid data in json format for command to analyze
	 * @return double result
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	static double runScriptToGetNumber(String rScriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScriptToGetNumber(rScriptName, rScript, jsonData);
	}


	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file as a stream  (RScriptName is stored in FileInfo)
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return double result
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	static double runScriptToGetNumber(String rScriptId, int userId, String projectId) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScriptToGetNumber(rScriptId, userId, projectId);
	}


	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file (RScriptName is stored in FileInfo)
	 * @param jsonData  - some valid data in json format for command to analyze
	 * @return double result
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	static double runScriptToGetNumber(String rScriptId, String jsonData) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScriptToGetNumber(rScriptId, jsonData);
	}

	/*******************************
	 * To get Point
	 * *****************************
	 */

	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file as a stream
	 * @param userId      - userId of a script creator
	 * @param projectId   - id of the project with data for script
	 * @return one point
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	static Point runScriptToGetPoint(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScriptToGetPoint(rScriptName, rScript, userId, projectId);
	}


	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file
	 * @param jsonData    - some valid data in json format for command to analyze
	 * @return one point
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	static Point runScriptToGetPoint(String rScriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScriptToGetPoint(rScriptName, rScript, jsonData);
	}


	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file as a stream  (RScriptName is stored in FileInfo)
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return one point
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	static Point runScriptToGetPoint(String rScriptId, int userId, String projectId) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScriptToGetPoint(rScriptId, userId, projectId);
	}


	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file (RScriptName is stored in FileInfo)
	 * @param jsonData  - some valid data in json format for command to analyze
	 * @return one point
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	static Point runScriptToGetPoint(String rScriptId, String jsonData) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScriptToGetPoint(rScriptId, jsonData);
	}


	/*******************************
	 * To get List<Point>
	 * *****************************
	 */

	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file as a stream
	 * @param userId      - userId of a script creator
	 * @param projectId   - id of the project with data for script
	 * @return List<Point>
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	static List<Point> runScriptToGetPoints(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScriptToGetPoints(rScriptName, rScript, userId, projectId);
	}

	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file
	 * @param jsonData    - some valid data in json format for command to analyze
	 * @return List<Point>
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	static List<Point> runScriptToGetPoints(String rScriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScriptToGetPoints(rScriptName, rScript, jsonData);
	}


	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file as a stream  (RScriptName is stored in FileInfo)
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return List<Point>
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	static List<Point> runScriptToGetPoints(String rScriptId, int userId, String projectId) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScriptToGetPoints(rScriptId, userId, projectId);
	}


	/**
	 * calls R using some logic from RFacade TODO: class should remember about it
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file (RScriptName is stored in FileInfo)
	 * @param jsonData  - some valid data in json format for command to analyze
	 * @return List<Point>
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	static List<Point> runScriptToGetPoints(String rScriptId, String jsonData) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		return RFacade.runScriptToGetPoints(rScriptId, jsonData);
	}
}
