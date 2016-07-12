package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.NotParsedJsonStringResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataArray;
import com.analyzeme.data.DataSet;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.streamreader.StreamToString;
import org.renjin.sexp.*;
import org.renjin.sexp.Vector;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * Created by lagroffe on 25.03.2016 2:57
 */

public class Renjin implements IRCaller {
    private static ScriptEngineManager manager = null;
    private static ScriptEngine engine = null;


    private static List renjinNotNamedVectorToList(final Vector v) {
        if (v == null) {
            throw new IllegalArgumentException("Renjin to get ColumnResult: impossible to evaluate; cause: null result");
        }
        List<Double> result = new ArrayList<Double>();
        for (int i = 0; i < v.length(); i++) {
            //TODO: refactor not for double only
            result.add(v.getElementAsDouble(i));
        }
        return result;
    }

    static Map<String, List<Double>> renjinDataFrameToFile(final SEXP result) throws Exception {
        if (result == null) {
            throw new IllegalArgumentException("Renjin to get FileResult: impossible to evaluate; cause: null result");
        }
        ListVector res = (ListVector) result;
        if (res.hasAttributes()) {
            AtomicVector names = res.getNames();
            //TODO: refactor not for double only
            Map<String, List<Double>> toReturn = new HashMap<>();
            for (int i = 0; i < names.length(); i++) {
                Vector tempVector = res.getElementAsVector(names.getElementAsString(i));
                List<Double> tempList = new ArrayList<>();
                for (int j = 0; j < tempVector.length(); j++) {
                    tempList.add(tempVector.getElementAsDouble(j));
                }
                toReturn.put(names.getElementAsString(i), tempList);
            }
            return toReturn;
        }
        return null;
    }

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

    private static void insertDataFromJson(final String jsonData) throws Exception {
        JsonParser jsonParser = new JsonParser();
        //TODO: refactor to work with other types, not only double
        DataArray<Double> parsed = jsonParser.parse(new ByteArrayInputStream(jsonData.getBytes()));
        Map<String, List<Double>> data = parsed.getMap();
        for (Map.Entry<String, List<Double>> entry : data.entrySet()) {
            double[] array = new double[entry.getValue().size()];
            for (int i = 0; i < entry.getValue().size(); i++) {
                array[i] = entry.getValue().get(i);
            }
            engine.put(entry.getKey(), array);
        }
    }

    private SEXP runScript(ByteArrayInputStream scriptStream) throws Exception {
        String script = StreamToString.convertStreamANSI(scriptStream);
        return runCommand(script);
    }

    private SEXP runCommand(final String script) throws Exception {
        try {
            SEXP result = (SEXP) engine.eval(script);
            if (result == null) {
                throw new IllegalArgumentException("Incorrect script with null result");
            } else {
                return result;
            }
        } catch (Exception e) {
            throw e;
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
        Vector res = (Vector) runScript(rScript);
        deleteData();
        if (res == null) {
            throw new IllegalArgumentException("Impossible to evaluate");
        }
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
        Vector res = (Vector) runScript(rScript);
        deleteData();
        try {
            Map<String, List<Double>> r = renjinDataFrameToFile(res);
            return new FileResult(r);
        } catch (Exception e) {
            throw e;
        }
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
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or command errored
     */
    public NotParsedJsonStringResult runCommandDefault(final String rCommand,
                                                       final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertDataFromJson(jsonData);
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
        Vector res = (Vector) runCommand(rCommand);
        if (res == null) {
            throw new IllegalArgumentException("Impossible to evaluate");
        }
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
        Vector res = (Vector) runCommand(rCommand);
        deleteData();
        try {
            Map<String, List<Double>> r = renjinDataFrameToFile(res);
            return new FileResult(r);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    public ScalarResult runCommandToGetScalar(final String rCommand,
                                              final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertDataFromJson(jsonData);
        SEXP result = runCommand(rCommand);
        deleteData();
        //TODO: refactor to work with other types of ScalarResult (not only double)
        return new ScalarResult<Double>(result.asReal());
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or command errored
     */
    public ColumnResult runCommandToGetVector(final String rCommand,
                                              final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertDataFromJson(jsonData);
        Vector res = (Vector) runCommand(rCommand);
        if (res == null) {
            throw new IllegalArgumentException("Impossible to evaluate");
        }
        deleteData();
        return new ColumnResult(renjinNotNamedVectorToList(res));
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or command errored
     */
    public FileResult runCommandToGetVectors(final String rCommand,
                                             final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }

        initialize();
        insertDataFromJson(jsonData);
        Vector res = (Vector) runCommand(rCommand);
        deleteData();
        try {
            Map<String, List<Double>> r = renjinDataFrameToFile(res);
            return new FileResult(r);
        } catch (Exception e) {
            throw e;
        }
    }
}
