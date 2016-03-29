package com.analyzeme.R.facade;

import com.analyzeme.R.call.FakeR;
import com.analyzeme.R.call.IRCaller;
import com.analyzeme.R.call.Renjin;
import com.analyzeme.R.call.Rserve;
import com.analyzeme.analyze.Point;
import com.analyzeme.repository.FileInfo;
import com.analyzeme.repository.FileRepository;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagroffe on 15.03.2016 21:45
 */

//TODO: when IAnalyzers are ready, make all public functions here accessible in the package only

public class RFacade {
	private static IRCaller caller;

	static {
		caller = new Renjin();
	}

	public RFacade(String type) throws IllegalArgumentException {
		if (type.equals("Rserve")) {
			caller = new Rserve();
		} else if (type.equals("Renjin")) {
			caller = new Renjin();
		} else if (type.equals("Fake")) {
			caller = new FakeR();
		} else {
			throw new IllegalArgumentException();
		}
	}


	/*----------------------------------------------------------------------------------------------------------------------------
	* Different types of command call
	*
	* Differs by:
	* - return value:
	* 		a) default - json string
	* 		b) double
	* 		c) Point
	* 		d) List<Point>
	* - way to give data:
	* 		a) as json string
	* 		b) as files from repository
	*----------------------------------------------------------------------------------------------------------------------------
     */

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rCommand  - string with correct R command
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in command
	 */
	public static String runCommand(String rCommand, int userId, String projectId) throws Exception {
		if (rCommand.equals("") || rCommand == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		ArrayList<String> files = RParser.parse(rCommand, userId, projectId);
		ArrayList<ByteArrayInputStream> filesData = new ArrayList<ByteArrayInputStream>();
		for (String name : files) {
			filesData.add(FileRepository.getRepo().getFileByID(name));
		}
		String result = caller.runCommand(rCommand, filesData);
		return result;
	}

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rCommand - string with correct R command
	 * @param jsonData - some valid data in json format for command to analyze
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in command
	 */
	public static String runCommand(String rCommand, String jsonData) throws Exception {
		if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		String result = caller.runCommand(rCommand, jsonData);
		return result;
	}

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rCommand  - string with correct R command
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return double result
	 * @throws Exception if files not found, R was impossible to call or there was in error in command
	 */
	public static double runCommandToGetNumber(String rCommand, int userId, String projectId) throws Exception {
		if (rCommand.equals("") || rCommand == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		ArrayList<String> files = RParser.parse(rCommand, userId, projectId);
		ArrayList<ByteArrayInputStream> filesData = new ArrayList<ByteArrayInputStream>();
		for (String name : files) {
			filesData.add(FileRepository.getRepo().getFileByID(name));
		}
		double result = caller.runCommandToGetNumber(rCommand, filesData);
		return result;
	}

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rCommand - string with correct R command
	 * @param jsonData - some valid data in json format for command to analyze
	 * @return double result
	 * @throws Exception if R was impossible to call or there was in error in command
	 */
	public static double runCommandToGetNumber(String rCommand, String jsonData) throws Exception {
		if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		double result = caller.runCommandToGetNumber(rCommand, jsonData);
		return result;
	}

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rCommand  - string with correct R command
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return one point
	 * @throws Exception if files not found, R was impossible to call or there was in error in command
	 */
	public static Point runCommandToGetPoint(String rCommand, int userId, String projectId) throws Exception {
		if (rCommand.equals("") || rCommand == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		ArrayList<String> files = RParser.parse(rCommand, userId, projectId);
		ArrayList<ByteArrayInputStream> filesData = new ArrayList<ByteArrayInputStream>();
		for (String name : files) {
			filesData.add(FileRepository.getRepo().getFileByID(name));
		}
		Point result = caller.runCommandToGetPoint(rCommand, filesData);
		return result;
	}

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rCommand - string with correct R command
	 * @param jsonData - some valid data in json format for command to analyze
	 * @return one point
	 * @throws Exception if R was impossible to call or there was in error in command
	 */
	public static Point runCommandToGetPoint(String rCommand, String jsonData) throws Exception {
		if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		Point result = caller.runCommandToGetPoint(rCommand, jsonData);
		return result;
	}

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rCommand  - string with correct R command
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return List<Point>
	 * @throws Exception if files not found, R was impossible to call or there was in error in command
	 */
	public static List<Point> runCommandToGetPoints(String rCommand, int userId, String projectId) throws Exception {
		if (rCommand.equals("") || rCommand == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		ArrayList<String> files = RParser.parse(rCommand, userId, projectId);
		ArrayList<ByteArrayInputStream> filesData = new ArrayList<ByteArrayInputStream>();
		for (String name : files) {
			filesData.add(FileRepository.getRepo().getFileByID(name));
		}
		List<Point> result = caller.runCommandToGetPoints(rCommand, filesData);
		return result;
	}

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rCommand - string with correct R command
	 * @param jsonData - some valid data in json format for command to analyze
	 * @return List<Point>
	 * @throws Exception if R was impossible to call or there was in error in command
	 */
	public static List<Point> runCommandToGetPoints(String rCommand, String jsonData) throws Exception {
		if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		List<Point> result = caller.runCommandToGetPoints(rCommand, jsonData);
		return result;
	}


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
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file as a stream
	 * @param userId      - userId of a script creator
	 * @param projectId   - id of the project with data for script
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static String runScript(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		ArrayList<String> files = RParser.parse(rScript, userId, projectId);
		ArrayList<ByteArrayInputStream> filesData = new ArrayList<ByteArrayInputStream>();
		for (String name : files) {
			filesData.add(FileRepository.getRepo().getFileByID(name));
		}
		String result = caller.runScript(rScriptName, rScript, filesData);
		return result;
	}

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file
	 * @param jsonData    - some valid data in json format for command to analyze
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static String runScript(String rScriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		String result = caller.runScript(rScriptName, rScript, jsonData);
		return result;
	}

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file as a stream  (RScriptName is stored in FileInfo)
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static String runScript(String rScriptId, int userId, String projectId) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
		ArrayList<String> files = RParser.parse(script.getData(), userId, projectId);
		ArrayList<ByteArrayInputStream> filesData = new ArrayList<ByteArrayInputStream>();
		for (String name : files) {
			filesData.add(FileRepository.getRepo().getFileByID(name));
		}
		String result = caller.runScript(script.getUniqueName(), script.getData(), filesData);
		return result;
	}

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file (RScriptName is stored in FileInfo)
	 * @param jsonData  - some valid data in json format for command to analyze
	 * @return json result (mistakes are possible)
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static String runScript(String rScriptId, String jsonData) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
		String result = caller.runScript(script.getUniqueName(), script.getData(), jsonData);
		return result;
	}


	/*******************************
	 * To get number
	 * *****************************
	 */

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file as a stream
	 * @param userId      - userId of a script creator
	 * @param projectId   - id of the project with data for script
	 * @return double result
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static double runScriptToGetNumber(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		ArrayList<String> files = RParser.parse(rScript, userId, projectId);
		ArrayList<ByteArrayInputStream> filesData = new ArrayList<ByteArrayInputStream>();
		for (String name : files) {
			filesData.add(FileRepository.getRepo().getFileByID(name));
		}
		double result = caller.runScriptToGetNumber(rScriptName, rScript, filesData);
		return result;
	}


	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file
	 * @param jsonData    - some valid data in json format for command to analyze
	 * @return double result
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	public static double runScriptToGetNumber(String rScriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		double result = caller.runScriptToGetNumber(rScriptName, rScript, jsonData);
		return result;
	}


	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file as a stream  (RScriptName is stored in FileInfo)
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return double result
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static double runScriptToGetNumber(String rScriptId, int userId, String projectId) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
		ArrayList<String> files = RParser.parse(script.getData(), userId, projectId);
		ArrayList<ByteArrayInputStream> filesData = new ArrayList<ByteArrayInputStream>();
		for (String name : files) {
			filesData.add(FileRepository.getRepo().getFileByID(name));
		}
		double result = caller.runScriptToGetNumber(script.getUniqueName(), script.getData(), filesData);
		return result;
	}


	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file (RScriptName is stored in FileInfo)
	 * @param jsonData  - some valid data in json format for command to analyze
	 * @return double result
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	public static double runScriptToGetNumber(String rScriptId, String jsonData) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
		double result = caller.runScriptToGetNumber(script.getUniqueName(), script.getData(), jsonData);
		return result;
	}

	/*******************************
	 * To get Point
	 * *****************************
	 */

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file as a stream
	 * @param userId      - userId of a script creator
	 * @param projectId   - id of the project with data for script
	 * @return one point
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static Point runScriptToGetPoint(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		ArrayList<String> files = RParser.parse(rScript, userId, projectId);
		ArrayList<ByteArrayInputStream> filesData = new ArrayList<ByteArrayInputStream>();
		for (String name : files) {
			filesData.add(FileRepository.getRepo().getFileByID(name));
		}
		Point result = caller.runScriptToGetPoint(rScriptName, rScript, filesData);
		return result;
	}


	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file
	 * @param jsonData    - some valid data in json format for command to analyze
	 * @return one point
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	public static Point runScriptToGetPoint(String rScriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		Point result = caller.runScriptToGetPoint(rScriptName, rScript, jsonData);
		return result;
	}


	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file as a stream  (RScriptName is stored in FileInfo)
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return one point
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static Point runScriptToGetPoint(String rScriptId, int userId, String projectId) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
		ArrayList<String> files = RParser.parse(script.getData(), userId, projectId);
		ArrayList<ByteArrayInputStream> filesData = new ArrayList<ByteArrayInputStream>();
		for (String name : files) {
			filesData.add(FileRepository.getRepo().getFileByID(name));
		}
		Point result = caller.runScriptToGetPoint(script.getUniqueName(), script.getData(), filesData);
		return result;
	}


	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file (RScriptName is stored in FileInfo)
	 * @param jsonData  - some valid data in json format for command to analyze
	 * @return one point
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	public static Point runScriptToGetPoint(String rScriptId, String jsonData) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
		Point result = caller.runScriptToGetPoint(script.getUniqueName(), script.getData(), jsonData);
		return result;
	}


	/*******************************
	 * To get List<Point>
	 * *****************************
	 */

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file as a stream
	 * @param userId      - userId of a script creator
	 * @param projectId   - id of the project with data for script
	 * @return List<Point>
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static List<Point> runScriptToGetPoints(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		ArrayList<String> files = RParser.parse(rScript, userId, projectId);
		ArrayList<ByteArrayInputStream> filesData = new ArrayList<ByteArrayInputStream>();
		for (String name : files) {
			filesData.add(FileRepository.getRepo().getFileByID(name));
		}
		List<Point> result = caller.runScriptToGetPoints(rScriptName, rScript, filesData);
		return result;
	}

	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - script to call, correct .R file
	 * @param jsonData    - some valid data in json format for command to analyze
	 * @return List<Point>
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	public static List<Point> runScriptToGetPoints(String rScriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		if (rScriptName == null || rScriptName.equals("") || rScript == null || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		List<Point> result = caller.runScriptToGetPoints(rScriptName, rScript, jsonData);
		return result;
	}


	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file as a stream  (RScriptName is stored in FileInfo)
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return List<Point>
	 * @throws Exception if files not found, R was impossible to call or there was in error in script
	 */
	public static List<Point> runScriptToGetPoints(String rScriptId, int userId, String projectId) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
		ArrayList<String> files = RParser.parse(script.getData(), userId, projectId);
		ArrayList<ByteArrayInputStream> filesData = new ArrayList<ByteArrayInputStream>();
		for (String name : files) {
			filesData.add(FileRepository.getRepo().getFileByID(name));
		}
		List<Point> result = caller.runScriptToGetPoints(script.getUniqueName(), script.getData(), filesData);
		return result;
	}


	/**
	 * calls R using some logic from R.call package
	 *
	 * @param rScriptId - id in repository of file with the script to call, correct .R file (RScriptName is stored in FileInfo)
	 * @param jsonData  - some valid data in json format for command to analyze
	 * @return List<Point>
	 * @throws Exception if R was impossible to call or there was in error in script
	 */
	public static List<Point> runScriptToGetPoints(String rScriptId, String jsonData) throws Exception {
		if (rScriptId == null || rScriptId.equals("") || jsonData == null || jsonData.equals(""))
			throw new IllegalArgumentException();
		FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
		List<Point> result = caller.runScriptToGetPoints(script.getUniqueName(), script.getData(), jsonData);
		return result;
	}
}
