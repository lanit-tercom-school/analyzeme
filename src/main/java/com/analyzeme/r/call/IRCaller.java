package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.NotParsedResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.analyzers.result.VectorResult;
import com.analyzeme.analyzers.result.VectorsResult;
import com.analyzeme.data.dataset.DataArray;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataSet;
import com.analyzeme.scripts.Script;

import java.util.List;
import java.util.Map;

public interface IRCaller {

    /**
     * @param script    - script to call
     * @param dataFiles - data necessary for the script
     * @return json form of result (may be errors, auto-generated)
     * @throws Exception if failed to call r or script errored
     */
    NotParsedResult runScriptDefault(final Script script,
                                     final List<DataSet> dataFiles) throws Exception;

    /**
     * @param script    - script to call
     * @param dataFiles - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or script errored
     */
    ScalarResult runScriptToGetScalar(final Script script,
                                      final List<DataSet> dataFiles) throws Exception;

    /**
     * @param script    - script to call
     * @param dataFiles - data necessary for the script
     * @return vector (~column)
     * @throws Exception if failed to call r or script errored
     */
    VectorResult runScriptToGetVector(final Script script,
                                      final List<DataSet> dataFiles) throws Exception;

    /**
     * @param script    - script to call
     * @param dataFiles - data necessary for the script
     * @return group of vectors (~columns)
     * @throws Exception if failed to call r or script errored
     */
    VectorsResult runScriptToGetVectors(final Script script,
                                        final List<DataSet> dataFiles) throws Exception;


    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return json form of result (may be errors, auto-generated)
     * @throws Exception if failed to call r or command errored
     */
    NotParsedResult runScriptDefault(final Script script,
                                     final DataArray data) throws Exception;


    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    ScalarResult runScriptToGetScalar(final Script script,
                                      final DataArray data) throws Exception;

    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return vector (~column)
     * @throws Exception if failed to call r or command errored
     */
    VectorResult runScriptToGetVector(final Script script,
                                      final DataArray data) throws Exception;

    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return group of vectors (~columns)
     * @throws Exception if failed to call r or command errored
     */
    VectorsResult runScriptToGetVectors(final Script script,
                                        final DataArray data) throws Exception;
}
