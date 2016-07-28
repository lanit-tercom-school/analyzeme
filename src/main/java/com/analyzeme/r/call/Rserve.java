package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.NotParsedResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.analyzers.result.VectorResult;
import com.analyzeme.analyzers.result.VectorsResult;
import com.analyzeme.data.dataset.DataArray;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataEntryType;
import com.analyzeme.data.dataset.DataSet;
import com.analyzeme.scripts.Script;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
     * @param script    - script to call
     * @param dataFiles - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or script errored
     */
    public NotParsedResult runScriptDefault(final Script script,
                                            final List<DataSet> dataFiles) throws Exception {
        if (script == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        REXP result = r.eval(script.getScript());
        deleteData();
        return new NotParsedResult(result.toString());
    }

    /**
     * @param script    - script to call
     * @param dataFiles - data necessary for the script
     * @return double result
     * @throws Exception if failed to call r or script errored
     */
    public ScalarResult runScriptToGetScalar(final Script script,
                                             final List<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (script == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        double result = r.eval(script.getScript()).asDouble();
        deleteData();
        //return result;
        return null;
    }

    /**
     * @param script    - script to call
     * @param dataFiles - data necessary for the script
     * @return one point
     * @throws Exception if failed to call r or script errored
     */
    public VectorResult runScriptToGetVector(final Script script,
                                             final List<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (script == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return null;

    }

    /**
     * @param script    - script to call
     * @param dataFiles - data necessary for the script
     * @return List<Point>
     * @throws Exception if failed to call r or script errored
     */
    public VectorsResult runScriptToGetVectors(final Script script,
                                               final List<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (script == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        double[][] res = r.eval(script.getScript()).asDoubleMatrix();
        deleteData();
        return null;
    }

    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or command errored
     */
    public NotParsedResult runScriptDefault(final Script script,
                                            final DataArray data) throws Exception {
        if (script == null || data == null || data.equals("")) {
            throw new IllegalArgumentException();
        }
        return new NotParsedResult("");
    }

    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    public ScalarResult runScriptToGetScalar(final Script script,
                                             final DataArray data) throws Exception {
        if (script == null || data == null
                || data.equals("")) {
            throw new IllegalArgumentException();
        }
        return new ScalarResult(new DataEntry(DataEntryType.DOUBLE, null));
    }

    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or command errored
     */
    public VectorResult runScriptToGetVector(final Script script,
                                             final DataArray data) throws Exception {
        if (script == null || data == null
                || data.equals("")) {
            throw new IllegalArgumentException();
        }
        return new VectorResult(new ArrayList());
    }

    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or command errored
     */
    public VectorsResult runScriptToGetVectors(final Script script,
                                               final DataArray data) throws Exception {
        if (script == null || data == null
                || data.equals("")) {
            throw new IllegalArgumentException();
        }
        return new VectorsResult(new HashMap<String, List<DataEntry>>());
    }
}
