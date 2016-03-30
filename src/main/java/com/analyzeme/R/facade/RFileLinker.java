package com.analyzeme.R.facade;

import com.analyzeme.data.DataSet;
import com.analyzeme.data.DataSetResolversFactory;
import com.analyzeme.data.IDataSetResolver;
import com.analyzeme.data.RepositoryDataResolver;
import com.analyzeme.streamreader.StreamToString;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lagroffe on 15.03.2016 22:32
 */

//TODO: unit-tests
class RFileLinker {
	//if our special variables look like y_from__repo__MyFile.json__
	private static String regexp = ".?_from__([A-Za-z]+)__([A-Za-z0-9-,]+.[a-z0-9]+)__";


	//just for developing
	public static void testParse() {
		ArrayList<String> result = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher("here are some code \n {} : \n some other \n x_from_file.json_ \n y_from__repo__MyData.json__ \n y <- c(x_from__repo__MyJson.excel__)");
		while (m.find()) {
			System.out.println(m.group(2));
		}
	}

	/**
	 * finds out what files  are necessary for the script to run
	 *
	 * @param rScript - script to call, correct .R file as a stream
	 * @return DataSets of necessary files
	 * @throws Exception if there were some mistakes in parsing or there are no necessary files
	 */
	public static ArrayList<DataSet> parse(ByteArrayInputStream rScript, RepositoryDataResolver resolver) throws Exception {
		if (rScript == null || resolver == null)
			throw new IllegalArgumentException();
		ArrayList<DataSet> result = new ArrayList<DataSet>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher(StreamToString.ConvertStream(rScript));
		while (m.find()) {
			IDataSetResolver res;
			if (m.group(1).equalsIgnoreCase("repo")) {
				res = resolver;
			} else {
				res = DataSetResolversFactory.getDataSetResolver(m.group(1));
			}
			result.add(res.getDataSet(m.group(2)));
		}

		return result;
	}

	/**
	 * finds out what files are necessary for the script to run
	 *
	 * @param rScript -script to call, correct .R file as a stream
	 * @return names of files
	 * @throws Exception if there were some mistakes in parsing or there are no necessary files
	 */
	public static ArrayList<String> parseForTests(ByteArrayInputStream rScript) throws Exception {
		if (rScript == null)
			throw new IllegalArgumentException();
		ArrayList<String> result = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher(StreamToString.ConvertStream(rScript));
		while (m.find()) {
			result.add(m.group(2));
		}

		return result;
	}


	/**
	 * finds out what files are necessary for the command to run
	 *
	 * @param rCommand = correct R command to parse
	 * @return DataSets of necessary files
	 * @throws Exception if there were some mistakes in parsing or there are no necessary files
	 */
	public static ArrayList<DataSet> parse(String rCommand, RepositoryDataResolver resolver) throws Exception {
		if (rCommand == null || rCommand.equals("") || resolver == null)
			throw new IllegalArgumentException();
		ArrayList<DataSet> result = new ArrayList<DataSet>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher(rCommand);
		while (m.find()) {
			IDataSetResolver res;
			if (m.group(1).equalsIgnoreCase("repo")) {
				res = resolver;
			} else {
				res = DataSetResolversFactory.getDataSetResolver(m.group(1));
			}
			result.add(res.getDataSet(m.group(2)));
		}

		return result;
	}

	/**
	 * finds out what files are necessary for the command to run
	 *
	 * @param rCommand = correct R command to parse
	 * @return names of files
	 * @throws Exception if there were some mistakes in parsing or there are no necessary files
	 */
	public static ArrayList<String> parseForTests(String rCommand) throws Exception {
		if (rCommand == null || rCommand.equals(""))
			throw new IllegalArgumentException();
		ArrayList<String> result = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher(rCommand);
		while (m.find()) {
			result.add(m.group(2));
		}

		return result;
	}
}
