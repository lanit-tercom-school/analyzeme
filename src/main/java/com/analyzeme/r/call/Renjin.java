package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataSet;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.streamreader.StreamToString;
import org.renjin.primitives.matrix.Matrix;
import org.renjin.sexp.AttributeMap;
import org.renjin.sexp.SEXP;
import org.renjin.sexp.Vector;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lagroffe on 25.03.2016 2:57
 */

public class Renjin implements IRCaller {
    private static ScriptEngineManager manager = null;
    private static ScriptEngine engine = null;


    private static List renjinVectorToList(Vector v) {
        List<Double> result = new ArrayList<Double>();
        for(int i = 0; i < v.length(); i++) {
            //TODO: refactor not for double only
            result.add(v.getElementAsDouble(i));
        }
        return result;
    }

    private static Map<String, List<Double>> renjinVectorToFile(final Vector res) {
        if (res == null) {
            throw new IllegalArgumentException("Impossible to evaluate");
        }
        if (res.hasAttributes()) {
            AttributeMap attributes = res.getAttributes();
            Vector dim = attributes.getDim();
            if (dim == null || dim.length() == 1) {
                throw new IllegalArgumentException("Wrong type of command");
            } else if (dim.length() == 2) {
                Matrix m = new Matrix(res);
                //TODO: refactor to work not only with double
                List<List<Double>> result = new ArrayList<List<Double>>();
                for(int i = 0; i < m.getNumCols(); i++) {
                    result.add(new ArrayList<Double>());
                }
                for (int i = 0; i < m.getNumRows(); i++) {
                    for(int j = 0; j < m.getNumCols(); j++) {
                        result.get(j).add(m.getElementAsDouble(i, j));
                    }
                }
                Map<String, List<Double>> r = new HashMap<String, List<Double>>();
                for(int i = 0; i < result.size(); i++) {
                    r.put("col"+(int)i, result.get(i));
                }
                return r;
            } else {
                throw new IllegalArgumentException("Wrong type of command");
            }
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
            for(String field : set.getFields()) {
                //TODO: refactor to work with other types, not only double
                List<Double> value = set.getByField(field);
                double[] v1 = new double[value.size()];
                int i = 0;
                for(Double v : value) {
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
        Map<String, List<Double>> data = jsonParser.parse(jsonData);
        for (Map.Entry<String, List<Double>> entry : data.entrySet()) {
            double[] array = new double[entry.getValue().size()];
            for(int i = 0; i < entry.getValue().size(); i++) {
                array[i] = entry.getValue().get(i);
            }
            engine.put(entry.getKey(), array);
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
    public String runScriptDefault(final String scriptName,
                                   ByteArrayInputStream rScript,
                                   final ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (scriptName == null || scriptName.equals("") ||
                rScript == null || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        String script = StreamToString.convertStreamANSI(rScript);
        SEXP result = (SEXP) engine.eval(script);
        deleteData();
        return result.toString();
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
        String script = StreamToString.convertStreamANSI(rScript);
        //TODO: refactor to work with other types of ScalarResult (not only double)
        double result = ((SEXP) engine.eval(script)).asReal();
        deleteData();
        return new ScalarResult<Double>(result);
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
        String script = StreamToString.convertStreamANSI(rScript);
        Vector res = ((Vector) engine.eval(script));
        if (res == null) {
            throw new IllegalArgumentException("Impossible to evaluate");
        }
        deleteData();
        return new ColumnResult(renjinVectorToList(res));
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
        String script = StreamToString.convertStreamANSI(rScript);
        Vector res = ((Vector) engine.eval(script));
        deleteData();
        try {
            Map<String, List<Double>> r = renjinVectorToFile(res);
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
    public String runCommandDefault(final String rCommand,
                                    final ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        SEXP result = (SEXP) engine.eval(rCommand);
        deleteData();
        return result.toString();
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
        initialize();
        insertDataFromJson(jsonData);
        SEXP result = (SEXP) engine.eval(rCommand);
        deleteData();
        return result.toString();
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
        //TODO: refactor to work with other types of ScalarResult (not only double)
        double result = ((SEXP) engine.eval(rCommand)).asReal();
        deleteData();
        return new ScalarResult<Double>(result);
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
        Vector res = ((Vector) engine.eval(rCommand));
        if (res == null) {
            throw new IllegalArgumentException("Impossible to evaluate");
        }
        deleteData();
        return new ColumnResult(renjinVectorToList(res));
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
        Vector res = ((Vector) engine.eval(rCommand));
        deleteData();
        try {
            Map<String, List<Double>> r = renjinVectorToFile(res);
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
        //TODO: refactor to work with other types of ScalarResult (not only double)
        double result = ((SEXP) engine.eval(rCommand)).asReal();
        deleteData();
        return new ScalarResult<Double>(result);
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
        Vector res = ((Vector) engine.eval(rCommand));
        if (res == null) {
            throw new IllegalArgumentException("Impossible to evaluate");
        }
        deleteData();
        return new ColumnResult(renjinVectorToList(res));
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
        Vector res = ((Vector) engine.eval(rCommand));
        deleteData();
        try {
            Map<String, List<Double>> r = renjinVectorToFile(res);
            return new FileResult(r);
        } catch (Exception e) {
            throw e;
        }
    }
}
