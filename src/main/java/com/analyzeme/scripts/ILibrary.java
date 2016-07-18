package com.analyzeme.scripts;


import java.util.List;

public interface ILibrary {
    /**
     * @return list with all scripts' names in the library
     */
    List<String> getAllScriptsNames();

    /**
     * @param name - name of script given by user
     * @return script as a string
     * @throws Exception
     */
    Script getScript(final String name) throws Exception;

    /**
     * @param script information about script
     * @throws Exception
     */
    void addScript(final Script script) throws Exception;
}
