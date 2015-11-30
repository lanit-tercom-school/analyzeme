package FileRepository;

import java.util.Date;

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
	FileInfo(final String startName, final String nameToWrite, final String login) {
		this.startName = startName;
		this.nameToWrite = nameToWrite;
		this.login = login;
		//default ctor fills Date with current info
		uploadingDate = new Date();
	}

	FileInfo() {
	}
}
