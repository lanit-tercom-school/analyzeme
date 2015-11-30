package FileRepository;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Laetitia_Lagroffe on 27.11.2015.
 */
public interface IFileRepository {

	void createRootFolder(String path) throws IOException;

	boolean isRootFolderInitialized();

	FileInfo addNewFile(Part part, String filename, String login);

	ArrayList<String> getAllWrittenNames();

	File getFileByID(String nameToWrite);

	ArrayList<File> getFiles(String name, String login);

	ArrayList<File> getFiles(String name);
}
