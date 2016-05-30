package com.analyzeme.r.facade;

import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.DataSetResolversFactory;
import com.analyzeme.data.resolvers.IDataSetResolver;
import com.analyzeme.data.resolvers.FileInRepositoryResolver;
import com.analyzeme.streamreader.StreamToString;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lagroffe on 15.03.2016 22:32
 */

//TODO: unit-tests
class RFileLinker {
	//if our special variables look like
	//y_from__repo__myFile.json__
	//y_from__webRepo__webToken__
	//time_from__repo__0_10.json__
	private static String regexp = "([A-Za-z]+)_from__([A-Za-z]+)__([A-Za-z0-9,_.]+)__";
	private static int fieldGroup = 1;
	private static int sourceGroup = 2;
	private static int fileGroup = 3;


	//just for developing
	public static void testParse() {
		ArrayList<String> result = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher("here are some code \n {} : \n some other \n x_from_file.json_ \n y_from__repo__MyData.json__ \n y <- c(x_from__repo__MyJson.excel__)");
		while (m.find()) {
			System.out.println(m.group(fileGroup));
		}
	}

	private static <T> T findInList(List<T> list, T obj) {
		for (T item : list) {
			if (item.equals(obj))
				return item;
		}
		return null;
	}

	/**
	 * finds out what files  are necessary for the script to run
	 *
	 * @param rScript - script to call, correct .r file as a stream
	 * @return DataSets of necessary files
	 * @throws Exception if there were some mistakes in parsing or there are no necessary files
	 */
	public static ArrayList<DataSet> parse(ByteArrayInputStream rScript, FileInRepositoryResolver resolver) throws Exception {
		if (rScript == null || resolver == null)
			throw new IllegalArgumentException();
		ArrayList<DataSet> result = new ArrayList<DataSet>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher(StreamToString.convertStream(rScript));
		while (m.find()) {
			IDataSetResolver res;
			if (m.group(sourceGroup).equalsIgnoreCase("repo")) {
				res = resolver;
			} else {
				res = DataSetResolversFactory.getDataSetResolver(null);
			}
			DataSet data = res.getDataSet(m.group(fileGroup));
			DataSet found = findInList(result, data);
			if (found == null) {
				data.addField(m.group(fieldGroup));
				result.add(data);
			} else {
				found.addField(m.group(fieldGroup));
			}
		}
		return result;
	}

	/**
	 * finds out what files are necessary for the script to run
	 *
	 * @param rScript -script to call, correct .r file as a stream
	 * @return names of files
	 * @throws Exception if there were some mistakes in parsing or there are no necessary files
	 */
	public static ArrayList<String> parseForTests(ByteArrayInputStream rScript) throws Exception {
		if (rScript == null)
			throw new IllegalArgumentException();
		ArrayList<String> result = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher(StreamToString.convertStream(rScript));
		while (m.find()) {
			result.add(m.group(fileGroup));
		}
		return result;
	}


	/**
	 * finds out what files are necessary for the command to run
	 *
	 * @param rCommand = correct r command to parse
	 * @return DataSets of necessary files
	 * @throws Exception if there were some mistakes in parsing or there are no necessary files
	 */
	public static ArrayList<DataSet> parse(String rCommand, FileInRepositoryResolver resolver) throws Exception {
		if (rCommand == null || rCommand.equals("") || resolver == null)
			throw new IllegalArgumentException();
		ArrayList<DataSet> result = new ArrayList<DataSet>();

		Pattern pattern = Pattern.compile(regexp);
		Matcher m = pattern.matcher(rCommand);
		while (m.find()) {
			IDataSetResolver res;
			if (m.group(sourceGroup).equalsIgnoreCase("repo")) {
				res = resolver;
			} else {
				res = DataSetResolversFactory.getDataSetResolver(null);
			}
			DataSet data = res.getDataSet(m.group(fileGroup));
			DataSet found = findInList(result, data);
			if (found == null) {
				data.addField(m.group(fieldGroup));
				result.add(data);
			} else {
				found.addField(m.group(fieldGroup));
			}
		}
		return result;
	}

	/**
	 * finds out what files are necessary for the command to run
	 *
	 * @param rCommand = correct r command to parse
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
			result.add(m.group(fileGroup));
		}
		return result;
	}
}
