package com.analyzeme.streamreader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Class for Convert ByteArrayInputStream into String
 * Created by Sergey on 16.02.2016.
 */
public class StreamToString {
	/**
	 * Method for convert ByteArrayInputStream into String
	 *
	 * @param stream ByteArrayInputStream that need convert into stream
	 * @return String of data from ByteArrayInputStream
	 */
	public static String ConvertStream(ByteArrayInputStream stream) throws IOException {
		stream.reset();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = stream.read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		stream = new ByteArrayInputStream(baos.toByteArray());
		ByteArrayInputStream streamCopy = new ByteArrayInputStream(baos.toByteArray());

		int size = streamCopy.available();
		byte[] bytes = new byte[size];
		streamCopy.read(bytes, 0, size);
		String Data = new String(bytes, StandardCharsets.UTF_8); // Or any encoding.
		return Data;
	}

	/**
	 * Method for convert ByteArrayInputStream into String
	 *
	 * @param stream ByteArrayInputStream that need convert into stream
	 * @return String of data from ByteArrayInputStream
	 */
	public static String ConvertStreamANSI(ByteArrayInputStream stream) throws IOException {
		stream.reset();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = stream.read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		stream = new ByteArrayInputStream(baos.toByteArray());
		ByteArrayInputStream streamCopy = new ByteArrayInputStream(baos.toByteArray());

		int size = streamCopy.available();
		byte[] bytes = new byte[size];
		streamCopy.read(bytes, 0, size);
		String Data = new String(bytes, "Cp1252");
		return Data;
	}
}
