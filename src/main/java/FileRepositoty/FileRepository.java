package FileRepositoty;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.servlet.http.Part;

/**
 * Created by Olga on 18.11.2015.
 */
public class FileRepository implements IFileRepository {
    /**
     * The directory where uploaded files are kept
     */
    private File rootFolder;

    private FileRepository() {
    }

    public static final IFileRepository FILE_REPOSITORY = new FileRepository();

    /**
     * gets list of filenames
     */
    public List<String> getAllNames() {
        List<String> names = new ArrayList<String>();
        for (File file : getAllFiles())
        {
            names.add(file.getName());
        }
        return names;
    }

    /**
     * gets file by name (returns null if file do not exist)
     */
    public File getFileByName(String name)
    {
        for (File file : getAllFiles())
        {
            if(file.getName().equals(name))
            {
                return file;
            }
        }
        return null;
    }

    private List<File> getAllFiles() {
        List<File> files = new ArrayList<File>();
        for (File file : rootFolder.listFiles()) {
            files.add(file);
        }
        return files;
    }

    /**
     * checks if rootFolder is attached to the root directory
     */
    public boolean isRootFolderInitialized() {
        return (rootFolder != null);
    }

    /**
     * creates root directory
     */
    public void createRootFolder(String path) throws IOException {
        rootFolder = new File(path);
        rootFolder.mkdir();
    }

    /**
     * adds new file to the root directory
     */
    public boolean addNewFile(Part part, String fileName) {
        String fileNameToWrite = fileName;
        if (!nameIsCorrect(fileName)) {
            fileNameToWrite = createCorrectName(fileName);
        }
        try {
            part.write(rootFolder.getCanonicalPath() + File.separator + fileNameToWrite);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String createCorrectName(String fileName) {
        final char point = '.';
        String extension = fileName.substring(fileName.indexOf(point) + 1);
        String name = fileName.substring(0, fileName.indexOf(point)) + "_";
        int index = 1;
        while (true) {
            if (nameIsCorrect(name + Integer.toString(index) + point + extension)) {
                return name + Integer.toString(index) + point + extension;
            }
            index++;
        }
    }

    private boolean nameIsCorrect(String fileName) {
        for (File file : getAllFiles()) {
            if (file.getName().equals(fileName)) {
                return false;
            }
        }
        return true;
    }
}
