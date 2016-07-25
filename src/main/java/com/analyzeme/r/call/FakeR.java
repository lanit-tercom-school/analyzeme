package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.NotParsedJsonStringResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FakeR implements IRCaller {

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return auto-generated json form of result (may be errors)
     * @throws Exception if failed to call r or script errored
     */
    public NotParsedJsonStringResult runScriptDefault(final String scriptName,
                                                      final String rScript,
                                                      final List<DataSet> dataFiles) throws Exception {
        if (rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new NotParsedJsonStringResult("");
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or script errored
     */
    public ScalarResult runScriptToGetScalar(final String scriptName,
                                             final String rScript,
                                             final List<DataSet> dataFiles) throws Exception {
        if (rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new ScalarResult<Double>(0.);
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or script errored
     */
    public ColumnResult runScriptToGetVector(final String scriptName,
                                             final String rScript,
                                             final List<DataSet> dataFiles) throws Exception {
        if (rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new ColumnResult(new ArrayList());
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or script errored
     */
    public FileResult runScriptToGetVectors(final String scriptName,
                                            final String rScript,
                                            final List<DataSet> dataFiles) throws Exception {
        if (rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new FileResult(new HashMap<String, List>());
    }


    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - string with a command in r language
     * @param data       - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or command errored
     */
    public <T> NotParsedJsonStringResult runScriptDefault(final String scriptName,
                                                          final String rScript,
                                                          final Map<String, List<T>> data) throws Exception {
        if (rScript == null || rScript.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        return new NotParsedJsonStringResult("");
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - string with a command in r language
     * @param data       - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    public <T> ScalarResult runScriptToGetScalar(final String scriptName,
                                                 final String rScript,
                                                 final Map<String, List<T>> data) throws Exception {
        if (rScript == null || rScript.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        return new ScalarResult<Double>(0.);
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - string with a command in r language
     * @param data       - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or command errored
     */
    public <T> ColumnResult runScriptToGetVector(final String scriptName,
                                                 final String rScript,
                                                 final Map<String, List<T>> data) throws Exception {
        if (rScript == null || rScript.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        return new ColumnResult(new ArrayList());
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - string with a command in r language
     * @param data       - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or command errored
     */
    public <T> FileResult runScriptToGetVectors(final String scriptName,
                                                final String rScript,
                                                final Map<String, List<T>> data) throws Exception {
        if (rScript == null || rScript.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        return new FileResult(new HashMap<String, List>());
    }
}
