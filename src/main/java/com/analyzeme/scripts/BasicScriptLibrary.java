package com.analyzeme.scripts;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BasicScriptLibrary implements ILibrary {
    private static final String CONFIGS = "rscripts_info.txt";
    private static final String FOLDER = "r/";
    private List<Script> scripts = new ArrayList<Script>();
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.scripts.BasicScriptLibrary");
    }

    public BasicScriptLibrary() throws Exception {
        InputStream stream = GithubDownloader.download(CONFIGS);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream));
        String name;
        InputStream tempStream;
        while ((name = reader.readLine()) != null) {
            tempStream = GithubDownloader.download(FOLDER + name);
            addScriptFromDisk(name, IOUtils.toString(tempStream));
        }
    }

    /**
     * @param name   - name of script given by user
     * @param script script as a string
     * @throws Exception
     */

    private void addScriptFromDisk(final String name,
                                   final String script) throws Exception {
        LOGGER.debug("addScriptFromDisk(): method started");
        Script s = FormattedScriptUploader.upload(
                script, name);
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
