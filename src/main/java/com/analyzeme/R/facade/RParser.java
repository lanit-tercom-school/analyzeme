package com.analyzeme.R.facade;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by lagroffe on 15.03.2016 22:32
 */

/**
 * Visible in package to call from RConnector
 */

class RParser {

	/**
	 * finds out what files in the given project are necessary for the script to run
	 *
	 * @param rScript   -script to call, correct .R file as a stream
	 * @param userId    - userId of a script creator
	 * @param projectId - id of the project with data for script
	 * @return uniqueFileNames in repository of files connected to the script
	 * @throws Exception if there were some mistakes in parsing or there are no necessary files
	 */
	public static ArrayList<String> parse(ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
		ArrayList<String> result = new ArrayList<String>();
		//logic to be implemented
		return result;
	}

	/**
	 * finds out what files in the given project are necessary for the command to run
	 *
	 * @param rCommand  = correct R command to parse
	 * @param userId    - userId of a command caller
	 * @param projectId - id of the project with data for command
	 * @return uniqueFileNames in repository of files connected to the command
	 * @throws Exception if there were some mistakes in parsing or there are no necessary files
	 */
	public static ArrayList<String> parse(String rCommand, int userId, String projectId) throws Exception {
		ArrayList<String> result = new ArrayList<String>();
		//logic to be implemented
		return result;
	}
}
