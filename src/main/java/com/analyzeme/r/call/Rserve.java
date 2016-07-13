package com.analyzeme.r.call;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.NotParsedJsonStringResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataSet;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.streamreader.StreamToString;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lagroffe on 20.03.2016 0:38
 */


//TODO: deprecate jsonData
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
                List<Double> value = set.getByField(field);
                double[] v1 = new double[value.size()];
                int i = 0;
                for (Double v : value) {
                    v1[i++] = v;
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
        String script = StreamToString.convertStreamANSI(rScript);
        REXP result = r.eval(script);
        deleteData();
        return new NotParsedJsonStringResult(result.toString());
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
        double result = r.eval(script).asDouble();
        deleteData();
        //return result;
        return null;
    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return one point
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
        /*String script = StreamToString.convertStreamANSI(rScript);
        double[] res = r.eval(script).asDoubles();
		Point result = new Point();
		result.setX(res[0]);
		result.setY(res[1]);*/
        deleteData();
        //return result;
        return null;

    }

    /**
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param dataFiles  - data necessary for the script
     * @return List<Point>
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
        double[][] res = r.eval(script).asDoubleMatrix();
		/*ArrayList<Point> result = new ArrayList<Point>();
		for (int i = 0; i < res.length; i++) {
			Point p = new Point();
			p.setX(res[i][0]);
			p.setY(res[i][1]);
			result.add(p);
		}                    */
        deleteData();
        //return result;
        return null;
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
    public NotParsedJsonStringResult runCommandDefault(final String rCommand,
                                                       final ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        REXP result = r.eval(rCommand);
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

        InputStream is = new ByteArrayInputStream(jsonData.getBytes());
        JsonParser jsonParser;
        jsonParser = new JsonParser();
		/*Point[] data = jsonParser.parse(is).toPointArray();

		double[] x = new double[data.length];
		double[] y = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			x[i] = data[i].getX();
			y[i] = data[i].getY();
		}
		r.assign("x", x);
		r.assign("y", y);
		REXP result = r.eval(rCommand);
		deleteData();*/
        return new NotParsedJsonStringResult(null);
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
    public ScalarResult runCommandToGetScalar(final String rCommand,
                                              final ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
        double result = r.eval(rCommand).asDouble();
        deleteData();
        //return result;
        return null;
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return one point
     * @throws Exception if failed to call r or command errored
     */
    public ColumnResult runCommandToGetVector(final String rCommand,
                                              final ArrayList<DataSet> dataFiles) throws Exception {
        //dataFiles can be empty for simple commands
        if (rCommand == null || rCommand.equals("") || dataFiles == null) {
            throw new IllegalArgumentException();
        }
        initialize();
        insertData(dataFiles);
		/*double[] res = r.eval(rCommand).asDoubles();
		Point result = new Point();
		result.setX(res[0]);
		result.setY(res[1]);      */
        deleteData();
        //return result;
        return null;
    }

    /**
     * @param rCommand  - string with a command in r language
     * @param dataFiles - data necessary for the script
     * @return List<Point>
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
		/*double[][] res = r.eval(rCommand).asDoubleMatrix();
		ArrayList<Point> result = new ArrayList<Point>();
		for (int i = 0; i < res.length; i++) {
			Point p = new Point();
			p.setX(res[i][0]);
			p.setY(res[i][1]);
			result.add(p);
		}                */
        deleteData();
        //return result;
        return null;
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
    public ScalarResult runCommandToGetScalar(final String rCommand,
                                              final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        initialize();

        InputStream is = new ByteArrayInputStream(jsonData.getBytes());
		/*JsonParser jsonParser;
		jsonParser = new JsonParser();
		Point[] data = jsonParser.parse(is).toPointArray();

		double[] x = new double[data.length];
		double[] y = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			x[i] = data[i].getX();
			y[i] = data[i].getY();
		}
		r.assign("x", x);
		r.assign("y", y);
		double result = r.eval(rCommand).asDouble();
		deleteData();
		//return result; */
        return null;
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return one point
     * @throws Exception if failed to call r or command errored
     */
    public ColumnResult runCommandToGetVector(final String rCommand,
                                              final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        initialize();

		/*InputStream is = new ByteArrayInputStream(jsonData.getBytes());
		JsonParser jsonParser;
		jsonParser = new JsonParser();
		Point[] data = jsonParser.parse(is).toPointArray();

		double[] x = new double[data.length];
		double[] y = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			x[i] = data[i].getX();
			y[i] = data[i].getY();
		}
		r.assign("x", x);
		r.assign("y", y);
		double[] res = r.eval(rCommand).asDoubles();
		Point result = new Point();
		result.setX(res[0]);
		result.setY(res[1]);
		deleteData();
		//return result;  */
        return null;
    }

    /**
     * @param rCommand - string with a command in r language
     * @param jsonData - data necessary for the script
     * @return List<Point>
     * @throws Exception if failed to call r or command errored
     */
    public FileResult runCommandToGetVectors(final String rCommand,
                                             final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        initialize();

		/*InputStream is = new ByteArrayInputStream(jsonData.getBytes());
		JsonParser jsonParser;
		jsonParser = new JsonParser();
		Point[] data = jsonParser.parse(is).toPointArray();

		double[] x = new double[data.length];
		double[] y = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			x[i] = data[i].getX();
			y[i] = data[i].getY();
		}
		r.assign("x", x);
		r.assign("y", y);
		double[][] res = r.eval(rCommand).asDoubleMatrix();
		ArrayList<Point> result = new ArrayList<Point>();
		for (int i = 0; i < res.length; i++) {
			Point p = new Point();
			p.setX(res[i][0]);
			p.setY(res[i][1]);
			result.add(p);
		}
		deleteData();
		//return result;   */
        return null;
    }

    /**
     * @param rCommand - string with a command in r language
     * @param data     - data necessary for the script
     * @return json form of result (may be errors)
     * @throws Exception if failed to call r or command errored
     */
    public <T> NotParsedJsonStringResult runCommandDefault(final String rCommand,
                                                           final Map<String, List<T>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null || data.equals("")) {
            throw new IllegalArgumentException();
        }
        return new NotParsedJsonStringResult("");
    }

    /**
     * @param rCommand - string with a command in r language
     * @param data     - data necessary for the script
     * @return scalar result
     * @throws Exception if failed to call r or command errored
     */
    public <T> ScalarResult runCommandToGetScalar(final String rCommand,
                                                  final Map<String, List<T>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null || data.equals("")) {
            throw new IllegalArgumentException();
        }
        return new ScalarResult<Double>(0.);
    }

    /**
     * @param rCommand - string with a command in r language
     * @param data     - data necessary for the script
     * @return one vector
     * @throws Exception if failed to call r or command errored
     */
    public <T> ColumnResult runCommandToGetVector(final String rCommand,
                                                  final Map<String, List<T>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null || data.equals("")) {
            throw new IllegalArgumentException();
        }
        return new ColumnResult(new ArrayList());
    }

    /**
     * @param rCommand - string with a command in r language
     * @param data     - data necessary for the script
     * @return group of vectors
     * @throws Exception if failed to call r or command errored
     */
    public <T> FileResult runCommandToGetVectors(final String rCommand,
                                                 final Map<String, List<T>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null || data.equals("")) {
            throw new IllegalArgumentException();
        }
        return new FileResult(new HashMap<String, List>());
    }
}
