package FileRepositoty;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Юыќур on 23.11.2015.
 */
public interface IFileRepository {

    public File getFileByName(String name);

    public List<String> getAllNames();

    public boolean isRootFolderInitialized()throws IOException;

    public void createRootFolder(String path)throws IOException;

    public boolean addNewFile(Part part, String fileName);
}
