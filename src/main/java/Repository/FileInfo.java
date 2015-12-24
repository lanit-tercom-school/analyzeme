package Repository;

import java.util.Date;
import java.io.IOException;

/**
 * For one particular user:
 * - nameToWrite implies startName
 * - nameToWrite implies uploadingDate
 * - startName && uploadingDate imply nameToWrite
 * <p>
 * For whole repository:
 * - nameToWrite implies user, startName, uploadingDate
 * - user && startName && uploadingDate imply nameToWrite
 * <p>
 * Created by Laetitia_Lagroffe on 27.11.2015.
 */
public class FileInfo {

	public String startName;
	public String nameToWrite;
	public Date uploadingDate;
	public String login;

	/**
	 * @param startName   - name given by user
	 * @param nameToWrite - name generated for repository
	 */
	FileInfo(final String startName, final String nameToWrite, final String login) throws IOException {
		if(startName == null || startName.equals("")) throw new IOException();
		this.startName = startName;
		if(nameToWrite == null || nameToWrite.equals("")) throw new IOException();
		this.nameToWrite = nameToWrite;
		this.login = login;
		//default ctor fills Date with current info
		uploadingDate = new Date();
	}
}