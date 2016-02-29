package com.analyzeme.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * FileInfo object contains binary file data and
 * all information about file
 */

public class FileInfo {
	public String nameForUser;
	public String uniqueName;
	public Date uploadingDate;
	public ByteArrayInputStream data;
	public boolean isActive = true;

	/**
	 * function to convert input stream to bytes array
	 *
	 * @throws IOException
	 */
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
	 * @param nameForUser - name given by user
	 * @param uniqueName  - name generated for repository
	 * @param data        - input stream with byte info (to extract from Part use method getInputStream)
	 */
	FileInfo(final String nameForUser, final String uniqueName, final InputStream data) throws IOException {
		if (nameForUser == null || nameForUser.equals("")) throw new IOException();
		this.nameForUser = nameForUser;
		if (uniqueName == null || uniqueName.equals("")) throw new IOException();
		this.uniqueName = uniqueName;
		this.data = new ByteArrayInputStream(getBytesFromInputStream(data));
		//default ctor fills Date with current info (number of milliseconds since the Unix epoch (first moment of 1970) in the UTC time zone)
		uploadingDate = new Date();
	}
}
