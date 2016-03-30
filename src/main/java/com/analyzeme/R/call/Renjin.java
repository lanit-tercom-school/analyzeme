package com.analyzeme.R.call;

import com.analyzeme.analyze.Point;
import com.analyzeme.data.DataSet;
import com.analyzeme.parsers.JsonParser;
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

	private static void Initialize() {
		if (engine == null) {
			manager = new ScriptEngineManager();
			engine = manager.getEngineByName("Renjin");
		}
	}
	//------------------
	//default for scripts
	//return - json
	//may be errors
	//------------------

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return json form of result (may be errors)
	 * @throws Exception if failed to call R or script errored
	 */
	public String runScript(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception {
		if (scriptName.equals("") || scriptName == null || rScript == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		String result = null;
		//TODO: implement when parsers are ready
		return result;
	}

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param jsonData   - data necessary for the script
	 * @return json form of result (may be errors)
	 * @throws Exception if failed to call R or script errored
	 */
	public String runScript(String scriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		if (scriptName.equals("") || scriptName == null || rScript == null || jsonData.equals("") || jsonData == null)
			throw new IllegalArgumentException();
		String result = null;
		//TODO: implement when parsers are ready
		return result;
	}


	//------------------
	//script for files
	//------------------

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return double result
	 * @throws Exception if failed to call R or script errored
	 */
	public double runScriptToGetNumber(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception {
		if (scriptName.equals("") || scriptName == null || rScript == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		double result = 0;
		//TODO: implement when parsers are ready
		return result;
	}

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return one point
	 * @throws Exception if failed to call R or script errored
	 */
	public Point runScriptToGetPoint(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception {
		if (scriptName.equals("") || scriptName == null || rScript == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		Point result = null;
		//TODO: implement when parsers are ready
		return result;
	}

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param dataFiles  - data necessary for the script
	 * @return List<Point>
	 * @throws Exception if failed to call R or script errored
	 */
	public List<Point> runScriptToGetPoints(String scriptName, ByteArrayInputStream rScript, ArrayList<DataSet> dataFiles) throws Exception {
		if (scriptName.equals("") || scriptName == null || rScript == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		List<Point> result = new ArrayList<Point>();
		//TODO: implement when parsers are ready
		return result;
	}

	//------------------
	//script for data
	//------------------

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param jsonData   - data necessary for the script
	 * @return double result
	 * @throws Exception if failed to call R or script errored
	 */
	public double runScriptToGetNumber(String scriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		if (scriptName.equals("") || scriptName == null || rScript == null || jsonData.equals("") || jsonData == null)
			throw new IllegalArgumentException();
		double result = 0;
		//TODO: implement when parsers are ready
		return result;
	}

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param jsonData   - data necessary for the script
	 * @return one point
	 * @throws Exception if failed to call R or script errored
	 */
	public Point runScriptToGetPoint(String scriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		if (scriptName.equals("") || scriptName == null || rScript == null || jsonData.equals("") || jsonData == null)
			throw new IllegalArgumentException();
		Point result = null;
		//TODO: implement when parsers are ready
		return result;
	}

	/**
	 * @param scriptName - name of the script to be called
	 * @param rScript    - script to call, correct .R file as a stream
	 * @param jsonData   - data necessary for the script
	 * @return List<Point>
	 * @throws Exception if failed to call R or script errored
	 */
	public List<Point> runScriptToGetPoints(String scriptName, ByteArrayInputStream rScript, String jsonData) throws Exception {
		if (scriptName.equals("") || scriptName == null || rScript == null || jsonData.equals("") || jsonData == null)
			throw new IllegalArgumentException();
		List<Point> result = new ArrayList<Point>();
		//TODO: implement when parsers are ready
		return result;
	}

	//------------------
	//default for commands
	//return - json
	//may be errors
	//------------------

	/**
	 * @param rCommand  - string with a command in R language
	 * @param dataFiles - data necessary for the script
	 * @return json form of result (may be errors)
	 * @throws Exception if failed to call R or command errored
	 */
	public String runCommand(String rCommand, ArrayList<DataSet> dataFiles) throws Exception {
		if (rCommand.equals("") || rCommand == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		String result = null;
		//TODO: implement when parsers are ready
		return result;
	}

	/**
	 * @param rCommand - string with a command in R language
	 * @param jsonData - data necessary for the script
	 * @return json form of result (may be errors)
	 * @throws Exception if failed to call R or command errored
	 */
	public String runCommand(String rCommand, String jsonData) throws Exception {
		if (rCommand.equals("") || rCommand == null || jsonData == null || jsonData.isEmpty())
			throw new IllegalArgumentException();
		String result = null;
		//TODO: implement on Sprint 16.3
		return result;
	}


	//------------------
	//command for files
	//------------------

	/**
	 * @param rCommand  - string with a command in R language
	 * @param dataFiles - data necessary for the script
	 * @return double result
	 * @throws Exception if failed to call R or command errored
	 */
	public double runCommandToGetNumber(String rCommand, ArrayList<DataSet> dataFiles) throws Exception {
		if (rCommand.equals("") || rCommand == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		double result = 0;
		//TODO: implement when parsers are ready
		return result;
	}

	/**
	 * @param rCommand  - string with a command in R language
	 * @param dataFiles - data necessary for the script
	 * @return one point
	 * @throws Exception if failed to call R or command errored
	 */
	public Point runCommandToGetPoint(String rCommand, ArrayList<DataSet> dataFiles) throws Exception {
		if (rCommand.equals("") || rCommand == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		Point result = null;
		//TODO: implement when parsers are ready
		return result;
	}

	/**
	 * @param rCommand  - string with a command in R language
	 * @param dataFiles - data necessary for the script
	 * @return List<Point>
	 * @throws Exception if failed to call R or command errored
	 */
	public List<Point> runCommandToGetPoints(String rCommand, ArrayList<DataSet> dataFiles) throws Exception {
		if (rCommand.equals("") || rCommand == null || dataFiles == null || dataFiles.isEmpty())
			throw new IllegalArgumentException();
		List<Point> result = new ArrayList<Point>();
		//TODO: implement when parsers are ready
		return result;
	}

	//------------------
	//command for data
	//------------------

	/**
	 * @param rCommand - string with a command in R language
	 * @param jsonData - data necessary for the script
	 * @return double result
	 * @throws Exception if failed to call R or command errored
	 */
	public double runCommandToGetNumber(String rCommand, String jsonData) throws Exception {
		if (rCommand.equals("") || rCommand == null || jsonData == null || jsonData.isEmpty())
			throw new IllegalArgumentException();
		Initialize();

		InputStream is = new ByteArrayInputStream(jsonData.getBytes());
		JsonParser jsonParser;
		jsonParser = new JsonParser();
		Point[] data = jsonParser.getPointsFromPointJson(is);

		double[] x = new double[data.length];
		double[] y = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			x[i] = data[i].GetX();
			y[i] = data[i].GetY();
		}
		engine.put("x", x);
		engine.put("y", y);
		engine.put("result", 0);
		double result = ((SEXP) engine.eval("result <-" + rCommand)).asReal();
		return result;
	}

	/**
	 * @param rCommand - string with a command in R language
	 * @param jsonData - data necessary for the script
	 * @return one point
	 * @throws Exception if failed to call R or command errored
	 */
	public Point runCommandToGetPoint(String rCommand, String jsonData) throws Exception {
		if (rCommand.equals("") || rCommand == null || jsonData == null || jsonData.isEmpty())
			throw new IllegalArgumentException();
		Initialize();

		InputStream is = new ByteArrayInputStream(jsonData.getBytes());
		JsonParser jsonParser;
		jsonParser = new JsonParser();
		Point[] data = jsonParser.getPointsFromPointJson(is);

		double[] x = new double[data.length];
		double[] y = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			x[i] = data[i].GetX();
			y[i] = data[i].GetY();
		}
		engine.put("x", x);
		engine.put("y", y);
		engine.put("result", 0);
		Vector res = ((Vector) engine.eval("result <-" + rCommand));
		Point result = new Point();
		result.SetX(res.getElementAsDouble(0));
		result.SetY(res.getElementAsDouble(1));
		return result;
	}

	/**
	 * @param rCommand - string with a command in R language
	 * @param jsonData - data necessary for the script
	 * @return List<Point>
	 * @throws Exception if failed to call R or command errored
	 */
	public List<Point> runCommandToGetPoints(String rCommand, String jsonData) throws Exception {
		if (rCommand.equals("") || rCommand == null || jsonData == null || jsonData.isEmpty())
			throw new IllegalArgumentException();

		Initialize();

		InputStream is = new ByteArrayInputStream(jsonData.getBytes());
		JsonParser jsonParser;
		jsonParser = new JsonParser();
		Point[] data = jsonParser.getPointsFromPointJson(is);

		double[] x = new double[data.length];
		double[] y = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			x[i] = data[i].GetX();
			y[i] = data[i].GetY();
		}
		engine.put("x", x);
		engine.put("y", y);
		engine.put("result", 0);
		Vector res = ((Vector) engine.eval("result <-" + rCommand));
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
					p.SetX(m.getElementAsDouble(i, 0));
					p.SetY(m.getElementAsDouble(i, 1));
					result.add(p);
				}
			} else {
				throw new IllegalArgumentException("Wrong type of command");
			}
		}
		return result;
	}
}
