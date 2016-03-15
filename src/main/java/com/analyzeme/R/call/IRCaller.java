package com.analyzeme.R.call;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by lagroffe on 15.03.2016 22:33
 */

//TODO: discuss the type of return value (stream may be better)
public interface IRCaller {

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param dataFiles  - data necessary for the script as streams from files
	 * @return json string with results
	 * @throws Exception if failed to call R or script errored
	 */
	String runScript(String scriptName, ByteArrayInputStream rScript, ArrayList<ByteArrayInputStream> dataFiles) throws Exception;

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param jsonData   - data necessary for the script
	 * @return json string with results
	 * @throws Exception if failed to call R or script errored
	 */
	String runScript(String scriptName, ByteArrayInputStream rScript, String jsonData) throws Exception;

	/**
	 * @param rCommand  - string with a command in R language
	 * @param dataFiles - data necessary for the script as streams from files
	 * @return json string with results
	 * @throws Exception if failed to call R or command errored
	 */
	String runCommand(String rCommand, ArrayList<ByteArrayInputStream> dataFiles) throws Exception;

	/**
	 * @param rCommand - string with a command in R language
	 * @param jsonData - data necessary for the script
	 * @return json string with results
	 * @throws Exception if failed to call R or command errored
	 */
	String runCommand(String rCommand, String jsonData) throws Exception;


	//----------------------------------------------------------------------------------------
	//the following depends on the type of integration
	//it will be removed from the interface later
	//
	//if R has separate back-end:

	/**
	 * convert stream to file and send it where R can call it from
	 *
	 * @param rScriptName - name of the script to be called
	 * @param rScript     - stream with data from .R file
	 * @return true if succeded
	 */
	boolean sendScript(String rScriptName, ByteArrayInputStream rScript);

	/**
	 * convert stream to file and send it where R can use it from
	 *
	 * @param dataFileName - name of the file in the script
	 * @param dataFile     - stream with data from the file we know R can work with
	 * @return true if succeded
	 */
	boolean sendFile(String dataFileName, ByteArrayInputStream dataFile);

	//----------------------------------------------------------------------------------------
}
