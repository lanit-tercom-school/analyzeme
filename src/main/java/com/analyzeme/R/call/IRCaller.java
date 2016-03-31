package com.analyzeme.R.call;

import com.analyzeme.analyze.Point;
import com.analyzeme.data.DataSet;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagroffe on 15.03.2016 22:33
 */

public interface IRCaller {

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
	String runScript(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception;

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
	double runScriptToGetNumber(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception;

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return one point
	 * @throws Exception if failed to call R or script errored
	 */
	Point runScriptToGetPoint(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception;

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return List<Point>
	 * @throws Exception if failed to call R or script errored
	 */
	List<Point> runScriptToGetPoints(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception;

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
	String runCommand(String rCommand, ArrayList<DataSet> dataFiles) throws Exception;

	/**
	 * @param rCommand - string with a command in R language
	 * @param jsonData - data necessary for the script
	 * @return json form of result (may be errors)
	 * @throws Exception if failed to call R or command errored
	 */
	String runCommand(String rCommand, String jsonData) throws Exception;


	//------------------
	//command for files
	//------------------

	/**
	 * @param rCommand  - string with a command in R language
	 * @param dataFiles - data necessary for the script
	 * @return double result
	 * @throws Exception if failed to call R or command errored
	 */
	double runCommandToGetNumber(String rCommand, ArrayList<DataSet> dataFiles) throws Exception;

	/**
	 * @param rCommand  - string with a command in R language
	 * @param dataFiles - data necessary for the script
	 * @return one point
	 * @throws Exception if failed to call R or command errored
	 */
	Point runCommandToGetPoint(String rCommand, ArrayList<DataSet> dataFiles) throws Exception;

	/**
	 * @param rCommand  - string with a command in R language
	 * @param dataFiles - data necessary for the script
	 * @return List<Point>
	 * @throws Exception if failed to call R or command errored
	 */
	List<Point> runCommandToGetPoints(String rCommand, ArrayList<DataSet> dataFiles) throws Exception;

	//------------------
	//command for data
	//------------------

	/**
	 * @param rCommand - string with a command in R language
	 * @param jsonData - data necessary for the script
	 * @return double result
	 * @throws Exception if failed to call R or command errored
	 */
	double runCommandToGetNumber(String rCommand, String jsonData) throws Exception;

	/**
	 * @param rCommand - string with a command in R language
	 * @param jsonData - data necessary for the script
	 * @return one point
	 * @throws Exception if failed to call R or command errored
	 */
	Point runCommandToGetPoint(String rCommand, String jsonData) throws Exception;

	/**
	 * @param rCommand - string with a command in R language
	 * @param jsonData - data necessary for the script
	 * @return List<Point>
	 * @throws Exception if failed to call R or command errored
	 */
	List<Point> runCommandToGetPoints(String rCommand, String jsonData) throws Exception;
}
