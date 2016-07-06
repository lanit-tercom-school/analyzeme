package com.analyzeme.r.call;

import com.analyzeme.analyze.Point;
import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataSet;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagroffe on 25.03.2016 1:14
 */

public class FakeR implements IRCaller {

    //------------------
    //default for scripts
    //return - json
    //may be errors
    //------------------

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or script errored
     */
    public String runScriptDefault(final String scriptName,
                                   ByteArrayInputStream rScript,
                                   final ArrayList<DataSet> dataFiles) throws Exception {
        if (scriptName == null || rScript == null ||
                scriptName.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        String result = "";
        return result;
    }


    //------------------
    //script for files
    //------------------

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return double result
     * @throws Exception if failed to call r or script errored
     */
    public ScalarResult runScriptToGetScalar(final String scriptName,
                                             ByteArrayInputStream rScript,
                                             final ArrayList<DataSet> dataFiles) throws Exception {
        if (scriptName == null || rScript == null ||
                scriptName.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        double result = 0;
        return result;
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return one point
     * @throws Exception if failed to call r or script errored
     */
    public ColumnResult runScriptToGetVector(final String scriptName,
                                             ByteArrayInputStream rScript,
                                             final ArrayList<DataSet> dataFiles) throws Exception {
        if (scriptName == null || rScript == null ||
                scriptName.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        Point result = new Point();
        return result;
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return List<Point>
     * @throws Exception if failed to call r or script errored
     */
    public FileResult runScriptToGetVectors(final String scriptName,
                                            ByteArrayInputStream rScript,
                                            final ArrayList<DataSet> dataFiles) throws Exception {
        if (scriptName == null || rScript == null ||
                scriptName.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        List<Point> result = new ArrayList<Point>();
        return result;
    }

    //------------------
    //default for commands
    //return - json
    //may be errors
    //------------------

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or command errored
     */
    public String runCommandDefault(final String rCommand,
                                    final ArrayList<DataSet> dataFiles) throws Exception {
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        String result = "";
        return result;
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or command errored
     */
    public String runCommandDefault(final String rCommand,
                                    final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        String result = "";
        return result;
    }


    //------------------
    //command for files
    //------------------

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return double result
     * @throws Exception if failed to call r or command errored
     */
    public ScalarResult runCommandToGetScalar(final String rCommand,
                                              final ArrayList<DataSet> dataFiles) throws Exception {
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        double result = 0;
        return result;
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return one point
     * @throws Exception if failed to call r or command errored
     */
    public ColumnResult runCommandToGetVector(final String rCommand,
                                              final ArrayList<DataSet> dataFiles) throws Exception {
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        Point result = new Point();
        return result;
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return List<Point>
     * @throws Exception if failed to call r or command errored
     */
    public FileResult runCommandToGetVectors(final String rCommand,
                                             final ArrayList<DataSet> dataFiles) throws Exception {
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        List<Point> result = new ArrayList<Point>();
        return result;
    }

    //------------------
    //command for data
    //------------------

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return double result
     * @throws Exception if failed to call r or command errored
     */
    public ScalarResult runCommandToGetScalar(final String rCommand,
                                              final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        double result = 0;
        return result;
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return one point
     * @throws Exception if failed to call r or command errored
     */
    public ColumnResult runCommandToGetVector(final String rCommand,
                                              final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        Point result = new Point();
        return result;
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return List<Point>
     * @throws Exception if failed to call r or command errored
     */
    public FileResult runCommandToGetVectors(final String rCommand,
                                             final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        List<Point> result = new ArrayList<Point>();
        return result;
    }
}
