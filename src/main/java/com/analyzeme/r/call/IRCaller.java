package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataSet;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;


public interface IRCaller {

    //------------------
    //script for files
    //------------------

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return json form of result (may be errors, auto-generated)
     * @throws Exception if failed to call r or script errored
     */
    String runScriptDefault(final String scriptName,
                            ByteArrayInputStream rScript,
                            final ArrayList<DataSet> dataFiles) throws Exception;

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or script errored
     */
    ScalarResult runScriptToGetScalar(final String scriptName,
                                      ByteArrayInputStream rScript,
                                      final ArrayList<DataSet> dataFiles) throws Exception;

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return vector (~column)
     * @throws Exception if failed to call r or script errored
     */
    ColumnResult runScriptToGetVector(final String scriptName,
                                      ByteArrayInputStream rScript,
                                      final ArrayList<DataSet> dataFiles) throws Exception;

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return group of vectors (~columns)
     * @throws Exception if failed to call r or script errored
     */
    FileResult runScriptToGetVectors(final String scriptName,
                                     ByteArrayInputStream rScript,
                                     final ArrayList<DataSet> dataFiles) throws Exception;


    //------------------
    //command for files
    //------------------

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return json form of result (may be errors, auto-generated)
     * @throws Exception if failed to call r or command errored
     */
    String runCommandDefault(final String rCommand,
                             final ArrayList<DataSet> dataFiles) throws Exception;

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    ScalarResult runCommandToGetScalar(final String rCommand,
                                       final ArrayList<DataSet> dataFiles) throws Exception;

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return vector (~column)
     * @throws Exception if failed to call r or command errored
     */
    ColumnResult runCommandToGetVector(final String rCommand, final ArrayList<DataSet> dataFiles) throws Exception;

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return group of vectors (~columns)
     * @throws Exception if failed to call r or command errored
     */
    FileResult runCommandToGetVectors(final String rCommand,
                                      final ArrayList<DataSet> dataFiles) throws Exception;

    //------------------
    //command for data
    //------------------


    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return json form of result (may be errors, auto-generated)
     * @throws Exception if failed to call r or command errored
     */
    String runCommandDefault(final String rCommand,
                             final String jsonData) throws Exception;


    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    ScalarResult runCommandToGetScalar(final String rCommand,
                                       final String jsonData) throws Exception;

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return vector (~column)
     * @throws Exception if failed to call r or command errored
     */
    ColumnResult runCommandToGetVector(final String rCommand,
                                       final String jsonData) throws Exception;

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return group of vectors (~columns)
     * @throws Exception if failed to call r or command errored
     */
    FileResult runCommandToGetVectors(final String rCommand,
                                      final String jsonData) throws Exception;
}
