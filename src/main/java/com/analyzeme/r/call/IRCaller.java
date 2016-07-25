package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.NotParsedJsonStringResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.dataWithType.DataEntry;

import java.util.List;
import java.util.Map;

public interface IRCaller {

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return json form of result (may be errors, auto-generated)
     * @throws Exception if failed to call r or script errored
     */
    NotParsedJsonStringResult runScriptDefault(final String scriptName,
                                               final String rScript,
                                               final List<DataSet> dataFiles) throws Exception;

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or script errored
     */
    ScalarResult runScriptToGetScalar(final String scriptName,
                                      final String rScript,
                                      final List<DataSet> dataFiles) throws Exception;

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return vector (~column)
     * @throws Exception if failed to call r or script errored
     */
    ColumnResult runScriptToGetVector(final String scriptName,
                                      final String rScript,
                                      final List<DataSet> dataFiles) throws Exception;

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return group of vectors (~columns)
     * @throws Exception if failed to call r or script errored
     */
    FileResult runScriptToGetVectors(final String scriptName,
                                     final String rScript,
                                     final List<DataSet> dataFiles) throws Exception;


    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - string with a script in r language
     * @param data       - data necessary for the script
     * @return json form of result (may be errors, auto-generated)
     * @throws Exception if failed to call r or command errored
     */
    NotParsedJsonStringResult runScriptDefault(final String scriptName,
                                               final String rScript,
                                               final Map<String, List<DataEntry>> data) throws Exception;


    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - string with a script in r language
     * @param data       - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    ScalarResult runScriptToGetScalar(final String scriptName,
                                      final String rScript,
                                      final Map<String, List<DataEntry>> data) throws Exception;

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - string with a script in r language
     * @param data       - data necessary for the script
     * @return vector (~column)
     * @throws Exception if failed to call r or command errored
     */
    ColumnResult runScriptToGetVector(final String scriptName,
                                      final String rScript,
                                      final Map<String, List<DataEntry>> data) throws Exception;

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - string with a script in r language
     * @param data       - data necessary for the script
     * @return group of vectors (~columns)
     * @throws Exception if failed to call r or command errored
     */
    FileResult runScriptToGetVectors(final String scriptName,
                                     final String rScript,
                                     final Map<String, List<DataEntry>> data) throws Exception;
}
