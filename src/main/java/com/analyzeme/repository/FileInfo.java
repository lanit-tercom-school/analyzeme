package com.analyzeme.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * For one particular user:
 * - nameToWrite implies startName
 * - nameToWrite implies uploadingDate
 * - startName && uploadingDate imply nameToWrite
 *
 * For whole repository:
 * - nameToWrite implies user, startName, uploadingDate
 * - user && startName && uploadingDate imply nameToWrite
 */
public class FileInfo {

	public String startName;
	public String nameToWrite;
	public Date uploadingDate;
	public String username;
	public ByteArrayInputStream data;

	private synchronized static byte[] getBytesFromInputStream(InputStream is) throws IOException {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] buffer = new byte[0xFFFF];

			for (int len; (len = is.read(buffer)) != -1; )
				os.write(buffer, 0, len);

			os.flush();

			return os.toByteArray();
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * @param startName   - name given by user
	 * @param nameToWrite - name generated for repository
	 * @param username    - username
	 * @param data        - input stream with byte info (to extract from Part use method getInputStream)
	 */
	FileInfo(final String startName, final String nameToWrite, final String username, final InputStream data) throws IOException {
		if (startName == null || startName.equals("")) throw new IOException();
		this.startName = startName;
		if (nameToWrite == null || nameToWrite.equals("")) throw new IOException();
		this.nameToWrite = nameToWrite;
		if (username == null || username.equals("")) throw new IOException();
		this.username = username;
		this.data = new ByteArrayInputStream(getBytesFromInputStream(data));
		//default ctor fills Date with current info (number of milliseconds since the Unix epoch (first moment of 1970) in the UTC time zone)
		uploadingDate = new Date();
	}
}
