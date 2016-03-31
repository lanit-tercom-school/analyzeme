package com.analyzeme.R.call;

import com.analyzeme.analyze.Point;
import com.analyzeme.data.DataSet;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagroffe on 25.03.2016 1:14
 */

public class FakeR implements IRCaller {

	//------------------
	//default for scripts
	//return - json
	//may be errors
	//------------------

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return json form of result (may be errors)
	 * @throws Exception if failed to call R or script errored
	 */
	public String runScript(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception {
		if (scriptName.equals("") || scriptName == null || rScript == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		String result = "";
		return result;
	}


	//------------------
	//script for files
	//------------------

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return double result
	 * @throws Exception if failed to call R or script errored
	 */
	public double runScriptToGetNumber(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception {
		if (scriptName.equals("") || scriptName == null || rScript == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		double result = 0;
		return result;
	}

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return one point
	 * @throws Exception if failed to call R or script errored
	 */
	public Point runScriptToGetPoint(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception {
		if (scriptName.equals("") || scriptName == null || rScript == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		Point result = new Point();
		return result;
	}

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return List<Point>
	 * @throws Exception if failed to call R or script errored
	 */
	public List<Point> runScriptToGetPoints(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception {
		if (scriptName.equals("") || scriptName == null || rScript == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		List<Point> result = new ArrayList<Point>();
		return result;
	}

	//------------------
	//default for commands
	//return - json
	//may be errors
	//------------------

	/**
	 * @param rCommand  - string with a command in R language
	 * @param dataFiles - data necessary for the script
	 * @return json form of result (may be errors)
	 * @throws Exception if failed to call R or command errored
	 */
	public String runCommand(String rCommand, ArrayList<DataSet> dataFiles) throws Exception {
		if (rCommand.equals("") || rCommand == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		String result = "";
		return result;
	}

	/**
	 * @param rCommand - string with a command in R language
	 * @param jsonData - data necessary for the script
	 * @return json form of result (may be errors)
	 * @throws Exception if failed to call R or command errored
	 */
	public String runCommand(String rCommand, String jsonData) throws Exception {
		if (rCommand.equals("") || rCommand == null || jsonData == null || jsonData.isEmpty())
			throw new IllegalArgumentException();
		String result = "";
		return result;
	}


	//------------------
	//command for files
	//------------------

	/**
	 * @param rCommand  - string with a command in R language
	 * @param dataFiles - data necessary for the script
	 * @return double result
	 * @throws Exception if failed to call R or command errored
	 */
	public double runCommandToGetNumber(String rCommand, ArrayList<DataSet> dataFiles) throws Exception {
		if (rCommand.equals("") || rCommand == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		double result = 0;
		return result;
	}

	/**
	 * @param rCommand  - string with a command in R language
	 * @param dataFiles - data necessary for the script
	 * @return one point
	 * @throws Exception if failed to call R or command errored
	 */
	public Point runCommandToGetPoint(String rCommand, ArrayList<DataSet> dataFiles) throws Exception {
		if (rCommand.equals("") || rCommand == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		Point result = new Point();
		return result;
	}

	/**
	 * @param rCommand  - string with a command in R language
	 * @param dataFiles - data necessary for the script
	 * @return List<Point>
	 * @throws Exception if failed to call R or command errored
	 */
	public List<Point> runCommandToGetPoints(String rCommand, ArrayList<DataSet> dataFiles) throws Exception {
		if (rCommand.equals("") || rCommand == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		List<Point> result = new ArrayList<Point>();
		return result;
	}

	//------------------
	//command for data
	//------------------

	/**
	 * @param rCommand - string with a command in R language
	 * @param jsonData - data necessary for the script
	 * @return double result
	 * @throws Exception if failed to call R or command errored
	 */
	public double runCommandToGetNumber(String rCommand, String jsonData) throws Exception {
		if (rCommand.equals("") || rCommand == null || jsonData == null || jsonData.isEmpty())
			throw new IllegalArgumentException();
		double result = 0;
		return result;
	}

	/**
	 * @param rCommand - string with a command in R language
	 * @param jsonData - data necessary for the script
	 * @return one point
	 * @throws Exception if failed to call R or command errored
	 */
	public Point runCommandToGetPoint(String rCommand, String jsonData) throws Exception {
		if (rCommand.equals("") || rCommand == null || jsonData == null || jsonData.isEmpty())
			throw new IllegalArgumentException();
		Point result = new Point();
		return result;
	}

	/**
	 * @param rCommand - string with a command in R language
	 * @param jsonData - data necessary for the script
	 * @return List<Point>
	 * @throws Exception if failed to call R or command errored
	 */
	public List<Point> runCommandToGetPoints(String rCommand, String jsonData) throws Exception {
		if (rCommand.equals("") || rCommand == null || jsonData == null || jsonData.isEmpty())
			throw new IllegalArgumentException();
		List<Point> result = new ArrayList<Point>();
		return result;
	}
}
