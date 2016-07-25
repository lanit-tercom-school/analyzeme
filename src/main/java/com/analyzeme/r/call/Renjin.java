package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.NotParsedJsonStringResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.dataWithType.DataEntryType;
import org.renjin.sexp.SEXP;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;
import java.util.Map;

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


    private SEXP runScript(final String script) throws Exception {
        SEXP result = (SEXP) engine.eval(script);
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


    /**
     * @param scriptName - name of the script to be called (can be null or empty)
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return auto-generated json (not our format, may be errors)
     * @throws Exception if failed to call r or script errored
     */
    public NotParsedJsonStringResult runScriptDefault(final String scriptName,
                                                      final String rScript,
                                                      final List<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        RenjinInputHandler.insertData(engine, dataFiles);
        SEXP result = runScript(rScript);
        deleteData();
        return new NotParsedJsonStringResult(result.toString());
    }


    /**
     * @param scriptName - name of the script to be called (can be null or empty)
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or script errored
     */
    public ScalarResult runScriptToGetScalar(final String scriptName,
                                             final String rScript,
                                             final List<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        RenjinInputHandler.insertData(engine, dataFiles);
        SEXP result = runScript(rScript);
        deleteData();
        //TODO: refactor to work not only with double
        return new ScalarResult(new DataEntry(DataEntryType.DOUBLE, result.asReal()));
    }


    /**
     * @param scriptName - name of the script to be called (can be null or empty)
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or script errored
     */
    public ColumnResult runScriptToGetVector(final String scriptName,
                                             final String rScript,
                                             final List<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        RenjinInputHandler.insertData(engine, dataFiles);
        SEXP res = runScript(rScript);
        deleteData();
        return new ColumnResult(renjinNotNamedVectorToList(res));
    }

    /**
     * @param scriptName - name of the script to be called (can be null or empty)
     * @param rScript    - script to call, correct .r file as a string
     * @param dataFiles  - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or script errored
     */
    public FileResult runScriptToGetVectors(final String scriptName,
                                            final String rScript,
                                            final List<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        RenjinInputHandler.insertData(engine, dataFiles);
        SEXP res = runScript(rScript);
        deleteData();
        return new FileResult(resultToFile(res));
    }

    /**
     * @param scriptName - name of the script to be called (can be null or empty)
     * @param rScript    - string with a command in r language
     * @param data       - data necessary for the script
     * @return json form of result (may be errors, auto-generated)
     * @throws Exception if failed to call r or command errored
     */
    public NotParsedJsonStringResult runScriptDefault(final String scriptName,
                                                      final String rScript,
                                                      final Map<String, List<DataEntry>> data) throws Exception {
        if (rScript == null || rScript.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        RenjinInputHandler.insertData(engine, data);
        SEXP result = runScript(rScript);
        deleteData();
        return new NotParsedJsonStringResult(result.toString());
    }


    /**
     * @param scriptName - name of the script to be called (can be null or empty)
     * @param rCommand   - string with a command in r language
     * @param data       - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    public ScalarResult runScriptToGetScalar(final String scriptName,
                                             final String rCommand,
                                             final Map<String, List<DataEntry>> data) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        RenjinInputHandler.insertData(engine, data);
        SEXP result = runScript(rCommand);
        deleteData();
        //TODO: refactor to work with other types of ScalarResult (not only double)
        return new ScalarResult(new DataEntry(DataEntryType.DOUBLE, result.asReal()));
    }

    /**
     * @param scriptName - name of the script to be called (can be null or empty)
     * @param rCommand   - string with a command in r language
     * @param data       - data necessary for the script
     * @return vector (~column)
     * @throws Exception if failed to call r or command errored
     */
    public ColumnResult runScriptToGetVector(final String scriptName,
                                             final String rCommand,
                                             final Map<String, List<DataEntry>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        RenjinInputHandler.insertData(engine, data);
        SEXP res = runScript(rCommand);
        deleteData();
        return new ColumnResult(renjinNotNamedVectorToList(res));
    }

    /**
     * @param scriptName - name of the script to be called (can be null or empty)
     * @param rCommand   - string with a command in r language
     * @param data       - data necessary for the script
     * @return group of vectors (~columns)
     * @throws Exception if failed to call r or command errored
     */
    public FileResult runScriptToGetVectors(final String scriptName,
                                            final String rCommand,
                                            final Map<String, List<DataEntry>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        RenjinInputHandler.insertData(engine, data);
        SEXP res = runScript(rCommand);
        deleteData();
        return new FileResult(resultToFile(res));
    }
}
