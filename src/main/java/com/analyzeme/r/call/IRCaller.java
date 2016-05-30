package com.analyzeme.r.call;

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
	 * @param rScript    - script to call, correct .r file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return json form of result (may be errors)
	 * @throws Exception if failed to call r or script errored
	 */
	String runScript(final String scriptName, ByteArrayInputStream rScript, final ArrayList<DataSet> dataFiles) throws Exception;

	//------------------
	//script for files
	//------------------

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .r file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return double result
	 * @throws Exception if failed to call r or script errored
	 */
	double runScriptToGetNumber(final String scriptName, ByteArrayInputStream rScript, final ArrayList<DataSet> dataFiles) throws Exception;

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .r file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return one point
	 * @throws Exception if failed to call r or script errored
	 */
	Point runScriptToGetPoint(final String scriptName, ByteArrayInputStream rScript, final ArrayList<DataSet> dataFiles) throws Exception;

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .r file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return List<Point>
	 * @throws Exception if failed to call r or script errored
	 */
	List<Point> runScriptToGetPoints(final String scriptName, ByteArrayInputStream rScript, final ArrayList<DataSet> dataFiles) throws Exception;

	//------------------
	//default for commands
	//return - json
	//may be errors
	//------------------

	/**
	 * @param rCommand  - string with a command in r language
	 * @param dataFiles - data necessary for the script
	 * @return json form of result (may be errors)
	 * @throws Exception if failed to call r or command errored
	 */
	String runCommand(final String rCommand, final ArrayList<DataSet> dataFiles) throws Exception;

	/**
	 * @param rCommand - string with a command in r language
	 * @param jsonData - data necessary for the script
	 * @return json form of result (may be errors)
	 * @throws Exception if failed to call r or command errored
	 */
	String runCommand(final String rCommand, final String jsonData) throws Exception;


	//------------------
	//command for files
	//------------------

	/**
	 * @param rCommand  - string with a command in r language
	 * @param dataFiles - data necessary for the script
	 * @return double result
	 * @throws Exception if failed to call r or command errored
	 */
	double runCommandToGetNumber(final String rCommand, final ArrayList<DataSet> dataFiles) throws Exception;

	/**
	 * @param rCommand  - string with a command in r language
	 * @param dataFiles - data necessary for the script
	 * @return one point
	 * @throws Exception if failed to call r or command errored
	 */
	Point runCommandToGetPoint(final String rCommand, final ArrayList<DataSet> dataFiles) throws Exception;

	/**
	 * @param rCommand  - string with a command in r language
	 * @param dataFiles - data necessary for the script
	 * @return List<Point>
	 * @throws Exception if failed to call r or command errored
	 */
	List<Point> runCommandToGetPoints(final String rCommand, final ArrayList<DataSet> dataFiles) throws Exception;

	//------------------
	//command for data
	//------------------

	/**
	 * @param rCommand - string with a command in r language
	 * @param jsonData - data necessary for the script
	 * @return double result
	 * @throws Exception if failed to call r or command errored
	 */
	double runCommandToGetNumber(final String rCommand, final String jsonData) throws Exception;

	/**
	 * @param rCommand - string with a command in r language
	 * @param jsonData - data necessary for the script
	 * @return one point
	 * @throws Exception if failed to call r or command errored
	 */
	Point runCommandToGetPoint(final String rCommand, final String jsonData) throws Exception;

	/**
	 * @param rCommand - string with a command in r language
	 * @param jsonData - data necessary for the script
	 * @return List<Point>
	 * @throws Exception if failed to call r or command errored
	 */
	List<Point> runCommandToGetPoints(final String rCommand, final String jsonData) throws Exception;
}
