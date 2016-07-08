package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.NotParsedJsonStringResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataSet;

import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * Created by lagroffe on 25.03.2016 1:14
 */

public class FakeR implements IRCaller {

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return auto-generated json form of result (may be errors)
     * @throws Exception if failed to call r or script errored
     */
    public NotParsedJsonStringResult runScriptDefault(final String scriptName,
                                                      ByteArrayInputStream rScript,
                                                      final ArrayList<DataSet> dataFiles) throws Exception {
        if (scriptName == null || rScript == null ||
                scriptName.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new NotParsedJsonStringResult("");
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or script errored
     */
    public ScalarResult runScriptToGetScalar(final String scriptName,
                                             ByteArrayInputStream rScript,
                                             final ArrayList<DataSet> dataFiles) throws Exception {
        if (scriptName == null || rScript == null ||
                scriptName.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new ScalarResult<Double>(0.);
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or script errored
     */
    public ColumnResult runScriptToGetVector(final String scriptName,
                                             ByteArrayInputStream rScript,
                                             final ArrayList<DataSet> dataFiles) throws Exception {
        if (scriptName == null || rScript == null ||
                scriptName.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new ColumnResult(new ArrayList());
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or script errored
     */
    public FileResult runScriptToGetVectors(final String scriptName,
                                            ByteArrayInputStream rScript,
                                            final ArrayList<DataSet> dataFiles) throws Exception {
        if (scriptName == null || rScript == null ||
                scriptName.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new FileResult(new HashMap<String, List>());
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or command errored
     */
    public NotParsedJsonStringResult runCommandDefault(final String rCommand,
                                                       final ArrayList<DataSet> dataFiles) throws Exception {
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new NotParsedJsonStringResult("");
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or command errored
     */
    public NotParsedJsonStringResult runCommandDefault(final String rCommand,
                                                       final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        return new NotParsedJsonStringResult("");
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    public ScalarResult runCommandToGetScalar(final String rCommand,
                                              final ArrayList<DataSet> dataFiles) throws Exception {
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new ScalarResult<Double>(0.);
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or command errored
     */
    public ColumnResult runCommandToGetVector(final String rCommand,
                                              final ArrayList<DataSet> dataFiles) throws Exception {
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new ColumnResult(new ArrayList());
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or command errored
     */
    public FileResult runCommandToGetVectors(final String rCommand,
                                             final ArrayList<DataSet> dataFiles) throws Exception {
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new FileResult(new HashMap<String, List>());
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    public ScalarResult runCommandToGetScalar(final String rCommand,
                                              final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        return new ScalarResult<Double>(0.);
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or command errored
     */
    public ColumnResult runCommandToGetVector(final String rCommand,
                                              final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        return new ColumnResult(new ArrayList());
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or command errored
     */
    public FileResult runCommandToGetVectors(final String rCommand,
                                             final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        return new FileResult(new HashMap<String, List>());
    }
}
