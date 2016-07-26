package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.NotParsedResult;
import com.analyzeme.analyzers.result.VectorResult;
import com.analyzeme.analyzers.result.VectorsResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.dataset.DataSet;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataEntryType;
import com.analyzeme.scripts.Script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FakeR implements IRCaller {

    /**
     * @param script - script to call
     * @param dataFiles  - data necessary for the script
     * @return auto-generated json form of result (may be errors)
     * @throws Exception if failed to call r or script errored
     */
    public NotParsedResult runScriptDefault(final Script script,
                                            final List<DataSet> dataFiles) throws Exception {
        if (script == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new NotParsedResult("");
    }

    /**
     * @param script - script to call
     * @param dataFiles  - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or script errored
     */
    public ScalarResult runScriptToGetScalar(final Script script,
                                             final List<DataSet> dataFiles) throws Exception {
        if (script == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new ScalarResult(new DataEntry(DataEntryType.DOUBLE, 0.));
    }

    /**
     * @param script - script to call
     * @param dataFiles  - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or script errored
     */
    public VectorResult runScriptToGetVector(final Script script,
                                             final List<DataSet> dataFiles) throws Exception {
        if (script == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new VectorResult(new ArrayList());
    }

    /**
     * @param script - script to call
     * @param dataFiles  - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or script errored
     */
    public VectorsResult runScriptToGetVectors(final Script script,
                                               final List<DataSet> dataFiles) throws Exception {
        if (script == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        return new VectorsResult(new HashMap<String, List<DataEntry>>());
    }


    /**
     * @param script - script to call
     * @param data       - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or command errored
     */
    public NotParsedResult runScriptDefault(final Script script,
                                            final Map<String, List<DataEntry>> data) throws Exception {
        if (script == null || data == null) {
            throw new IllegalArgumentException();
        }
        return new NotParsedResult("");
    }

    /**
     * @param script - script to call
     * @param data       - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    public ScalarResult runScriptToGetScalar(final Script script,
                                             final Map<String, List<DataEntry>> data) throws Exception {
        if (script == null || data == null) {
            throw new IllegalArgumentException();
        }
        return new ScalarResult(new DataEntry(DataEntryType.DOUBLE, 0.));
    }

    /**
     * @param script - script to call
     * @param data       - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or command errored
     */
    public VectorResult runScriptToGetVector(final Script script,
                                             final Map<String, List<DataEntry>> data) throws Exception {
        if (script == null || data == null) {
            throw new IllegalArgumentException();
        }
        return new VectorResult(new ArrayList());
    }

    /**
     * @param script - script to call
     * @param data       - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or command errored
     */
    public VectorsResult runScriptToGetVectors(final Script script,
                                               final Map<String, List<DataEntry>> data) throws Exception {
        if (script == null || data == null) {
            throw new IllegalArgumentException();
        }
        return new VectorsResult(new HashMap<String, List<DataEntry>>());
    }
}
