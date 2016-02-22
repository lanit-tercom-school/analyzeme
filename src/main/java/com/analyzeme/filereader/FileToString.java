package com.analyzeme.filereader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class convert File into String
 * Created by Sergey on 10.02.2016.
 */
public class FileToString {
	/**
	 * Function what convert File into String.
	 *
	 * @param path     path to file
	 * @param encoding encoding of file
	 * @return String  value of file
	 * @throws IOException
	 */
	public static String convertFile(String path, Charset encoding)
			throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
