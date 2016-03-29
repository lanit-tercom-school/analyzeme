package com.analyzeme.R.facade;

import com.analyzeme.repository.FileInfo;
import com.analyzeme.repository.UsersRepository;
import com.analyzeme.streamreader.StreamToString;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lagroffe on 15.03.2016 22:32
 */

class RParser {
	//if our special variables look like y_from__MyFile.json__
	private static String regexp = ".?_from__([A-Za-z0-9-,]+.[a-z0-9]+)__";


	//just for developing
	public static void testParse() {
		ArrayList<String> result = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher("here are some code \n {} : \n some other \n x_from_file.json_ \n y_from__MyData.json__ \n y <- c(x_from__MyJson.excel__)");
		while (m.find()) {
			System.out.println(m.group(1));
		}
	}

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
		if (rScript == null || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		ArrayList<String> result = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher(StreamToString.ConvertStream(rScript));
		while (m.find()) {
			String[] params = {Integer.toString(userId), projectId};
			FileInfo info = UsersRepository.getRepo().findFile(m.group(1), params);
			result.add(info.getUniqueName());
		}

		return result;
	}

	/**
	 * finds out what files in the given project are necessary for the script to run
	 *
	 * @param rScript -script to call, correct .R file as a stream
	 * @return uniqueFileNames in repository of files connected to the script
	 * @throws Exception if there were some mistakes in parsing or there are no necessary files
	 */
	public static ArrayList<String> parseForTests(ByteArrayInputStream rScript) throws Exception {
		if (rScript == null)
			throw new IllegalArgumentException();
		ArrayList<String> result = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher(StreamToString.ConvertStream(rScript));
		while (m.find()) {
			result.add(m.group(1));
		}

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
		if (rCommand == null || rCommand.equals("") || userId == 0 || projectId == null || projectId.equals(""))
			throw new IllegalArgumentException();
		ArrayList<String> result = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher(rCommand);
		while (m.find()) {
			String[] params = {Integer.toString(userId), projectId};
			FileInfo info = UsersRepository.getRepo().findFile(m.group(1), params);
			result.add(info.getUniqueName());
		}

		return result;
	}

	/**
	 * finds out what files in the given project are necessary for the command to run
	 *
	 * @param rCommand = correct R command to parse
	 * @return uniqueFileNames in repository of files connected to the command
	 * @throws Exception if there were some mistakes in parsing or there are no necessary files
	 */
	public static ArrayList<String> parseForTests(String rCommand) throws Exception {
		if (rCommand == null || rCommand.equals(""))
			throw new IllegalArgumentException();
		ArrayList<String> result = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher(rCommand);
		while (m.find()) {
			result.add(m.group(1));
		}

		return result;
	}
}
