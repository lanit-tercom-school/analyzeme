package com.analyzeme.scripts;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BasicScriptLibrary implements ILibrary {
    private static final String DEFAULT_FOLDER = "E:\\Projects\\analyzeme\\src\\main\\java\\com\\analyzeme\\scripts\\r";
    private List<Script> scripts = new ArrayList<Script>();
    private final File rootFolder;
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.scripts.BasicScriptLibrary");
    }

    BasicScriptLibrary(final String rootFolder) throws Exception {
        this.rootFolder = new File(rootFolder);
        this.rootFolder.mkdir();
        loadScriptsFromDisk();
    }

    BasicScriptLibrary() throws Exception {
        rootFolder = new File(DEFAULT_FOLDER);
        this.rootFolder.mkdir();
        loadScriptsFromDisk();
    }

    private void loadScriptsFromDisk() throws Exception {
        LOGGER.debug("loadScriptsFromDisk(): method started");
        for (File file : rootFolder.listFiles()) {
            LOGGER.debug("loadScriptsFromDisk(): file in folder");
            String name = file.getName();
            InputStream stream = new FileInputStream(file);
            String script = IOUtils.toString(stream);
            addScriptFromDisk(name, script);
        }
        LOGGER.debug("loadScriptsFromDisk(): method finished");
    }

    /**
     * @param name   - name of script given by user
     * @param script script as a string
     * @throws Exception
     */

    private void addScriptFromDisk(final String name,
                                   final String script) throws Exception {
        LOGGER.debug("addScriptFromDisk(): method started");
        Script s = FormattedScriptUploader.upload(script, name);
        LOGGER.debug("addScriptFromDisk(): script parsed");
        scripts.add(s);
        LOGGER.debug("addScriptFromDisk(): method finished");
    }

    /**
     * @return list with all scripts' names in the library
     */
    public List<String> getAllScriptsNames() {
        List<String> result = new ArrayList<>();
        for (Script script : this.scripts) {
            result.add(script.getName());
        }
        return result;
    }

    /**
     * @param name - name of script given by user
     * @return script as a string
     * @throws Exception
     */
    public Script getScript(
            final String name) throws Exception {
        LOGGER.debug("getScript(): method started");
        for (Script script : scripts) {
            if (script.getName().equals(name)) {
                LOGGER.debug(
                        "getScript(): script is found");
                return script;
            }
        }

        LOGGER.info("getScript(): no script with this name",
                name);
        throw new IllegalArgumentException(
                "Script with this name does not exist");
    }

    /**
     * @param script object with info about script
     * @throws Exception
     */

    public void addScript(
            final Script script) throws Exception {
        LOGGER.info("addScript(): method is not supported");
        throw new IllegalArgumentException(
                "This method is not supported in this library");
    }
}
