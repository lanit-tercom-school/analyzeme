package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.NotParsedJsonStringResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataSet;
import com.analyzeme.streamreader.StreamToString;
import org.renjin.sexp.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.ByteArrayInputStream;
import java.util.*;

import static com.analyzeme.r.call.RenjinResultHandler.renjinNotNamedVectorToList;
import static com.analyzeme.r.call.RenjinResultHandler.resultToFile;

/**
 * Created by lagroffe on 25.03.2016 2:57
 */

public class Renjin implements IRCaller {
    private static ScriptEngineManager manager = null;
    private static ScriptEngine engine = null;


    private static void initialize() {
        if (engine == null) {
            manager = new ScriptEngineManager();
            engine = manager.getEngineByName("Renjin");
        }
    }

    private static void insertData(final List<DataSet> dataFiles)
            throws Exception {
        for (DataSet set : dataFiles) {
            for (String field : set.getFields()) {
                //TODO: refactor to work with other types, not only double
                List<Double> value = set.getByField(field);
                double[] v1 = new double[value.size()];
                int i = 0;
                for (Double v : value) {
                    v1[i++] = v;
                }
                engine.put(field + "_from__repo__" +
                        set.getReferenceName() + "__", v1);
            }
        }
    }

    private static <T> void insertDataFromMap(final Map<String, List<T>> data) throws Exception {
        for (Map.Entry<String, List<T>> entry : data.entrySet()) {
            //TODO: refactor to work with other types, not only double
            double[] array = new double[entry.getValue().size()];
            for (int i = 0; i < entry.getValue().size(); i++) {
                array[i] = (double)((Double) entry.getValue().get(i));
            }
            engine.put(entry.getKey(), array);
        }
    }

    private SEXP runScript(ByteArrayInputStream scriptStream) throws Exception {
        String script = StreamToString.convertStreamANSI(scriptStream);
        return runCommand(script);
    }

    private SEXP runCommand(final String script) throws Exception {
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
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return auto-generated json (not our format, may be errors)
     * @throws Exception if failed to call r or script errored
     */
    public NotParsedJsonStringResult runScriptDefault(final String scriptName,
                                                      ByteArrayInputStream rScript,
                                                      final ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (scriptName == null || scriptName.equals("") ||
                rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        SEXP result = runScript(rScript);
        deleteData();
        return new NotParsedJsonStringResult(result.toString());
    }


    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or script errored
     */
    public ScalarResult runScriptToGetScalar(final String scriptName,
                                             ByteArrayInputStream rScript,
                                             final ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (scriptName == null || scriptName.equals("") ||
                rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        SEXP result = runScript(rScript);
        deleteData();
        //TODO: refactor to work not only with double
        return new ScalarResult<Double>(result.asReal());
    }


    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or script errored
     */
    public ColumnResult runScriptToGetVector(final String scriptName,
                                             ByteArrayInputStream rScript,
                                             final ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (scriptName == null || scriptName.equals("") ||
                rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        SEXP res = runScript(rScript);
        deleteData();
        return new ColumnResult(renjinNotNamedVectorToList(res));
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or script errored
     */
    public FileResult runScriptToGetVectors(final String scriptName,
                                            ByteArrayInputStream rScript,
                                            final ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (scriptName == null || scriptName.equals("") ||
                rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        SEXP res = runScript(rScript);
        deleteData();
        return new FileResult(resultToFile(res));
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or command errored
     */
    public NotParsedJsonStringResult runCommandDefault(final String rCommand,
                                                       final ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        SEXP result = runCommand(rCommand);
        deleteData();
        return new NotParsedJsonStringResult(result.toString());
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    public ScalarResult runCommandToGetScalar(final String rCommand,
                                              final ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") ||
                dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        SEXP result = runCommand(rCommand);
        deleteData();
        //TODO: refactor to work with other types of ScalarResult (not only double)
        return new ScalarResult<Double>(result.asReal());
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or command errored
     */
    public ColumnResult runCommandToGetVector(final String rCommand,
                                              final ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") ||
                dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        SEXP res = runCommand(rCommand);
        deleteData();
        return new ColumnResult(renjinNotNamedVectorToList(res));
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or command errored
     */
    public FileResult runCommandToGetVectors(final String rCommand,
                                             final ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        SEXP res = runCommand(rCommand);
        deleteData();
        return new FileResult(resultToFile(res));
    }

    /**
     * @param rCommand - string with a command in r language
     * @param data     - data necessary for the script
     * @return json form of result (may be errors, auto-generated)
     * @throws Exception if failed to call r or command errored
     */
    public <T> NotParsedJsonStringResult runCommandDefault(final String rCommand,
                                                           final Map<String, List<T>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertDataFromMap(data);
        SEXP result = runCommand(rCommand);
        deleteData();
        return new NotParsedJsonStringResult(result.toString());
    }


    /**
     * @param rCommand - string with a command in r language
     * @param data     - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    public <T> ScalarResult runCommandToGetScalar(final String rCommand,
                                                  final Map<String, List<T>> data) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertDataFromMap(data);
        SEXP result = runCommand(rCommand);
        deleteData();
        //TODO: refactor to work with other types of ScalarResult (not only double)
        return new ScalarResult<Double>(result.asReal());
    }

    /**
     * @param rCommand - string with a command in r language
     * @param data     - data necessary for the script
     * @return vector (~column)
     * @throws Exception if failed to call r or command errored
     */
    public <T> ColumnResult runCommandToGetVector(final String rCommand,
                                                  final Map<String, List<T>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertDataFromMap(data);
        SEXP res = runCommand(rCommand);
        deleteData();
        return new ColumnResult(renjinNotNamedVectorToList(res));
    }

    /**
     * @param rCommand - string with a command in r language
     * @param data     - data necessary for the script
     * @return group of vectors (~columns)
     * @throws Exception if failed to call r or command errored
     */
    public <T> FileResult runCommandToGetVectors(final String rCommand,
                                                 final Map<String, List<T>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertDataFromMap(data);
        SEXP res = runCommand(rCommand);
        deleteData();
        return new FileResult(resultToFile(res));
    }
}
