package com.analyzeme.r.call;

import com.analyzeme.analyze.Point;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    private static void insertData(List<DataSet> dataFiles) throws Exception {
        for (DataSet set : dataFiles) {
            for(String field : set.getFields()) {
                 List<Double> value = set.getByField(field);
                double[] v1 = new double[value.size()];
                int i = 0;
                for(Double v : value) {
                    v1[i++] = v;
                }
                engine.put(field + "_from__repo__" + set.getReferenceName() + "__", v1);
            }
        }
    }

    /**
     * clean up r memory after script was executed
     */
    private static void deleteData() throws Exception {
        engine.eval("rm(list = ls())");
    }

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
    public String runScript(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (scriptName == null || scriptName.equals("") || rScript == null || dataFiles == null)
            throw new IllegalArgumentException();
        initialize();
        insertData(dataFiles);
        String script = StreamToString.convertStreamANSI(rScript);
        SEXP result = (SEXP) engine.eval(script);
        deleteData();
        return result.toString();
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
    public double runScriptToGetNumber(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (scriptName == null || scriptName.equals("") || rScript == null || dataFiles == null)
            throw new IllegalArgumentException();
        initialize();
        insertData(dataFiles);
        String script = StreamToString.convertStreamANSI(rScript);
        double result = ((SEXP) engine.eval(script)).asReal();
        deleteData();
        return result;
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return one point
     * @throws Exception if failed to call r or script errored
     */
    public Point runScriptToGetPoint(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (scriptName == null || scriptName.equals("") || rScript == null || dataFiles == null)
            throw new IllegalArgumentException();
        initialize();
        insertData(dataFiles);
        String script = StreamToString.convertStreamANSI(rScript);
        Vector res = ((Vector) engine.eval(script));
        if (res == null) {
            throw new NullPointerException("Impossible to evaluate");
        }
        Point result = new Point();
        result.setX(res.getElementAsDouble(0));
        result.setY(res.getElementAsDouble(1));
        deleteData();
        return result;
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return List<Point>
     * @throws Exception if failed to call r or script errored
     */
    public List<Point> runScriptToGetPoints(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (scriptName == null || scriptName.equals("") || rScript == null || dataFiles == null)
            throw new IllegalArgumentException();
        initialize();
        insertData(dataFiles);
        String script = StreamToString.convertStreamANSI(rScript);
        Vector res = ((Vector) engine.eval(script));
        if (res == null) {
            throw new NullPointerException("Impossible to evaluate");
        }
        List<Point> result = new ArrayList<Point>();
        if (res.hasAttributes()) {
            AttributeMap attributes = res.getAttributes();
            Vector dim = attributes.getDim();
            if (dim == null || dim.length() == 1) {
                throw new IllegalArgumentException("Wrong type of command");
            } else if (dim.length() == 2) {
                Matrix m = new Matrix(res);
                for (int i = 0; i < m.getNumRows(); i++) {
                    Point p = new Point();
                    p.setX(m.getElementAsDouble(i, 0));
                    p.setY(m.getElementAsDouble(i, 1));
                    result.add(p);
                }
            } else {
                throw new IllegalArgumentException("Wrong type of command");
            }
        }
        deleteData();
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
    public String runCommand(String rCommand, ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") || dataFiles == null)
            throw new IllegalArgumentException();
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
    public String runCommand(String rCommand, String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals(""))
            throw new IllegalArgumentException();
        initialize();

        InputStream is = new ByteArrayInputStream(jsonData.getBytes());
        JsonParser jsonParser;
        jsonParser = new JsonParser();
        Point[] data = jsonParser.getPointsFromPointJson(is);

        double[] x = new double[data.length];
        double[] y = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            x[i] = data[i].getX();
            y[i] = data[i].getY();
        }
        engine.put("x", x);
        engine.put("y", y);
        SEXP result = (SEXP) engine.eval(rCommand);
        deleteData();
        return result.toString();
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
    public double runCommandToGetNumber(String rCommand, ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") || dataFiles == null)
            throw new IllegalArgumentException();
        initialize();
        insertData(dataFiles);
        double result = ((SEXP) engine.eval(rCommand)).asReal();
        deleteData();
        return result;
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return one point
     * @throws Exception if failed to call r or command errored
     */
    public Point runCommandToGetPoint(String rCommand, ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") || dataFiles == null)
            throw new IllegalArgumentException();
        initialize();
        insertData(dataFiles);
        Vector res = ((Vector) engine.eval(rCommand));
        if (res == null) {
            throw new NullPointerException("Impossible to evaluate");
        }
        Point result = new Point();
        result.setX(res.getElementAsDouble(0));
        result.setY(res.getElementAsDouble(1));
        deleteData();
        return result;
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return List<Point>
     * @throws Exception if failed to call r or command errored
     */
    public List<Point> runCommandToGetPoints(String rCommand, ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") || dataFiles == null)
            throw new IllegalArgumentException();
        initialize();
        insertData(dataFiles);
        Vector res = ((Vector) engine.eval(rCommand));
        if (res == null) {
            throw new NullPointerException("Impossible to evaluate");
        }
        List<Point> result = new ArrayList<Point>();
        if (res.hasAttributes()) {
            AttributeMap attributes = res.getAttributes();
            Vector dim = attributes.getDim();
            if (dim == null || dim.length() == 1) {
                throw new IllegalArgumentException("Wrong type of command");
            } else if (dim.length() == 2) {
                Matrix m = new Matrix(res);
                for (int i = 0; i < m.getNumRows(); i++) {
                    Point p = new Point();
                    p.setX(m.getElementAsDouble(i, 0));
                    p.setY(m.getElementAsDouble(i, 1));
                    result.add(p);
                }
            } else {
                throw new IllegalArgumentException("Wrong type of command");
            }
        }
        deleteData();
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
    public double runCommandToGetNumber(String rCommand, String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals(""))
            throw new IllegalArgumentException();
        initialize();

        InputStream is = new ByteArrayInputStream(jsonData.getBytes());
        JsonParser jsonParser;
        jsonParser = new JsonParser();
        Point[] data = jsonParser.getPointsFromPointJson(is);

        double[] x = new double[data.length];
        double[] y = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            x[i] = data[i].getX();
            y[i] = data[i].getY();
        }
        engine.put("x", x);
        engine.put("y", y);
        double result = ((SEXP) engine.eval(rCommand)).asReal();
        deleteData();
        return result;
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return one point
     * @throws Exception if failed to call r or command errored
     */
    public Point runCommandToGetPoint(String rCommand, String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals(""))
            throw new IllegalArgumentException();
        initialize();

        InputStream is = new ByteArrayInputStream(jsonData.getBytes());
        JsonParser jsonParser;
        jsonParser = new JsonParser();
        Point[] data = jsonParser.getPointsFromPointJson(is);

        double[] x = new double[data.length];
        double[] y = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            x[i] = data[i].getX();
            y[i] = data[i].getY();
        }
        engine.put("x", x);
        engine.put("y", y);
        Vector res = ((Vector) engine.eval(rCommand));
        if (res == null) {
            throw new NullPointerException("Impossible to evaluate");
        }
        Point result = new Point();
        result.setX(res.getElementAsDouble(0));
        result.setY(res.getElementAsDouble(1));
        deleteData();
        return result;
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return List<Point>
     * @throws Exception if failed to call r or command errored
     */
    public List<Point> runCommandToGetPoints(String rCommand, String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals(""))
            throw new IllegalArgumentException();

        initialize();

        InputStream is = new ByteArrayInputStream(jsonData.getBytes());
        JsonParser jsonParser;
        jsonParser = new JsonParser();
        Point[] data = jsonParser.getPointsFromPointJson(is);

        double[] x = new double[data.length];
        double[] y = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            x[i] = data[i].getX();
            y[i] = data[i].getY();
        }
        engine.put("x", x);
        engine.put("y", y);
        Vector res = ((Vector) engine.eval(rCommand));
        List<Point> result = new ArrayList<Point>();
        if (res == null) {
            throw new NullPointerException("Impossible to evaluate");
        }
        if (res.hasAttributes()) {
            AttributeMap attributes = res.getAttributes();
            Vector dim = attributes.getDim();
            if (dim == null || dim.length() == 1) {
                throw new IllegalArgumentException("Wrong type of command");
            } else if (dim.length() == 2) {
                Matrix m = new Matrix(res);
                for (int i = 0; i < m.getNumRows(); i++) {
                    Point p = new Point();
                    p.setX(m.getElementAsDouble(i, 0));
                    p.setY(m.getElementAsDouble(i, 1));
                    result.add(p);
                }
            } else {
                throw new IllegalArgumentException("Wrong type of command");
            }
        }
        deleteData();
        return result;
    }
}
