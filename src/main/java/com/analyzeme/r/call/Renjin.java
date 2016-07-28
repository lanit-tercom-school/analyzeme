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
import org.renjin.sexp.SEXP;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;

import static com.analyzeme.r.call.RenjinResultHandler.renjinNotNamedVectorToList;
import static com.analyzeme.r.call.RenjinResultHandler.resultToFile;

public class Renjin implements IRCaller {
    private static ScriptEngineManager manager = null;
    private static ScriptEngine engine = null;


    private static void initialize() {
        if (engine == null) {
            manager = new ScriptEngineManager();
            engine = manager.getEngineByName("Renjin");
        }
    }


    private SEXP runScript(final Script script) throws Exception {
        SEXP result = (SEXP) engine.eval(script.getScript());
        if (result == null) {
            throw new IllegalArgumentException("Renjin run: incorrect script; cause: null result");
        } else {
            return result;
        }
    }

    /**
     * clean up r memory after script was executed
     */
    private static void deleteData() throws Exception {
        engine.eval("rm(list = ls())");
    }

    private SEXP getSEXP(final Script script, final List<DataSet> data) throws Exception {
        if (script == null || data == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        RenjinInputHandler.insertData(engine, data, script.getInputType());
        SEXP result = runScript(script);
        deleteData();
        return result;
    }

    private SEXP getSEXP(final Script script, final DataArray data) throws Exception {
        if (script == null || data == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        RenjinInputHandler.insertData(engine, data, script.getInputType());
        SEXP res = runScript(script);
        deleteData();
        return res;
    }

    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return auto-generated json (not our format, may be errors)
     * @throws Exception if failed to call r or script errored
     */
    public NotParsedResult runScriptDefault(final Script script,
                                            final List<DataSet> data) throws Exception {
        SEXP result = getSEXP(script, data);
        return new NotParsedResult(result.toString());
    }


    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or script errored
     */
    public ScalarResult runScriptToGetScalar(final Script script,
                                             final List<DataSet> data) throws Exception {
        SEXP result = getSEXP(script, data);
        //TODO: refactor to work not only with double
        return new ScalarResult(new DataEntry(DataEntryType.DOUBLE, result.asReal()));
    }


    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or script errored
     */
    public VectorResult runScriptToGetVector(final Script script,
                                             final List<DataSet> data) throws Exception {
        SEXP result = getSEXP(script, data);
        return new VectorResult(renjinNotNamedVectorToList(result));
    }

    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or script errored
     */
    public VectorsResult runScriptToGetVectors(final Script script,
                                               final List<DataSet> data) throws Exception {
        SEXP result = getSEXP(script, data);
        return new VectorsResult(resultToFile(result));
    }


    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return json form of result (may be errors, auto-generated)
     * @throws Exception if failed to call r or command errored
     */
    public NotParsedResult runScriptDefault(final Script script,
                                            final DataArray data) throws Exception {
        SEXP result = getSEXP(script, data);
        return new NotParsedResult(result.toString());
    }


    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    public ScalarResult runScriptToGetScalar(final Script script,
                                             final DataArray data) throws Exception {
        SEXP result = getSEXP(script, data);
        //TODO: refactor to work with other types of ScalarResult (not only double)
        return new ScalarResult(new DataEntry(DataEntryType.DOUBLE, result.asReal()));
    }

    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return vector (~column)
     * @throws Exception if failed to call r or command errored
     */
    public VectorResult runScriptToGetVector(final Script script,
                                             final DataArray data) throws Exception {
        SEXP result = getSEXP(script, data);
        return new VectorResult(renjinNotNamedVectorToList(result));
    }

    /**
     * @param script - script to call
     * @param data   - data necessary for the script
     * @return group of vectors (~columns)
     * @throws Exception if failed to call r or command errored
     */
    public VectorsResult runScriptToGetVectors(final Script script,
                                               final DataArray data) throws Exception {
        SEXP result = getSEXP(script, data);
        return new VectorsResult(resultToFile(result));
    }
}
