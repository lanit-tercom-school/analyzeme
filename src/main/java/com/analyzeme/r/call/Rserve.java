package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.NotParsedJsonStringResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataEntryType;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rserve implements IRCaller {
    private static RConnection r = null;

    //TODO: in the future should use Settings (for host+port info)
    private void initialize() throws Exception {
        if (r == null) {
            r = new RConnection();
        }
    }

    private static void insertData(final List<DataSet> dataFiles)
            throws Exception {
        for (DataSet set : dataFiles) {
            for (String field : set.getFields()) {
                List<DataEntry> value = set.getByField(field);
                double[] v1 = new double[value.size()];
                int i = 0;
                for (DataEntry v : value) {
                    v1[i++] = v.getDoubleValue();
                }
                r.assign(field + "_from__repo__" +
                        set.getReferenceName() + "__", v1);
            }
        }
    }

    /**
     * clean up r memory after script was executed
     */
    private static void deleteData() throws Exception {
        r.eval("rm(list = ls())");
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or script errored
     */
    public NotParsedJsonStringResult runScriptDefault(final String scriptName,
                                                      final String rScript,
                                                      final List<DataSet> dataFiles) throws Exception {
        if (rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        REXP result = r.eval(rScript);
        deleteData();
        return new NotParsedJsonStringResult(result.toString());
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return double result
     * @throws Exception if failed to call r or script errored
     */
    public ScalarResult runScriptToGetScalar(final String scriptName,
                                             final String rScript,
                                             final List<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (scriptName == null || scriptName.equals("") ||
                rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        double result = r.eval(rScript).asDouble();
        deleteData();
        //return result;
        return null;
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return one point
     * @throws Exception if failed to call r or script errored
     */
    public ColumnResult runScriptToGetVector(final String scriptName,
                                             final String rScript,
                                             final List<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return null;

    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return List<Point>
     * @throws Exception if failed to call r or script errored
     */
    public FileResult runScriptToGetVectors(final String scriptName,
                                            final String rScript,
                                            final List<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (scriptName == null || scriptName.equals("") ||
                rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        double[][] res = r.eval(rScript).asDoubleMatrix();
        deleteData();
        return null;
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - string with a command in r language
     * @param data       - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or command errored
     */
    public NotParsedJsonStringResult runScriptDefault(final String scriptName,
                                                      final String rScript,
                                                      final Map<String, List<DataEntry>> data) throws Exception {
        if (rScript == null || rScript.equals("") ||
                data == null || data.equals("")) {
            throw new IllegalArgumentException();
        }
        return new NotParsedJsonStringResult("");
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rCommand   - string with a command in r language
     * @param data       - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    public ScalarResult runScriptToGetScalar(final String scriptName,
                                             final String rCommand,
                                             final Map<String, List<DataEntry>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null || data.equals("")) {
            throw new IllegalArgumentException();
        }
        return new ScalarResult(new DataEntry(DataEntryType.DOUBLE, null));
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rCommand   - string with a command in r language
     * @param data       - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or command errored
     */
    public ColumnResult runScriptToGetVector(final String scriptName,
                                             final String rCommand,
                                             final Map<String, List<DataEntry>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null || data.equals("")) {
            throw new IllegalArgumentException();
        }
        return new ColumnResult(new ArrayList());
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rCommand   - string with a command in r language
     * @param data       - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or command errored
     */
    public FileResult runScriptToGetVectors(final String scriptName,
                                            final String rCommand,
                                            final Map<String, List<DataEntry>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null || data.equals("")) {
            throw new IllegalArgumentException();
        }
        return new FileResult(new HashMap<String, List<DataEntry>>());
    }
}
