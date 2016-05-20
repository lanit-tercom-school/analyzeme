package com.analyzeme.controllers;

import com.analyzeme.R.facade.RFacade;
import com.analyzeme.analyze.AnalyzeFunction;
import com.analyzeme.analyze.AnalyzeFunctionFactory;
import com.analyzeme.analyze.Point;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.parsers.JsonParserException;
import com.analyzeme.parsers.PointToJson;
import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.filerepository.FileRepository;
import com.analyzeme.streamreader.StreamToString;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Ольга on 16.03.2016.
 */
@RestController
public class AnalysisController {

	/**
	 * @param fileName
	 * @return file data in string format
	 * null if file doesn't exist
	 * @throws IOException
	 */
	@RequestMapping(value = "/file/{file_name}/data", method = RequestMethod.GET)
	public String getData(@PathVariable("file_name") String fileName, HttpServletResponse response)
			throws IOException {
		try {
			ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);
		/*
		Convert ByteArrayInputStream into String
         */
			String Data = StreamToString.ConvertStream(file);

			return Data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	//todo return HttpEntity<double>

	/**
	 * @param fileName
	 * @return minimum in double format
	 * 0 if file doesn't exist
	 * @throws IOException
	 */
	@RequestMapping(value = "/file/{file_name}/{function_Type}", method = RequestMethod.GET)
	public double getMinimum(@PathVariable("file_name") String fileName, @PathVariable("function_Type") String functionType, HttpServletResponse response)
			throws IOException {
		try {
			//Analyze Factory
			AnalyzeFunctionFactory ServletFactory = new AnalyzeFunctionFactory();
			//Create GlobalMinimum function
			AnalyzeFunction ServletAnalyzeFunction = ServletFactory.getFunction(functionType);

			Point[] Data;

			ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);
			String DataString = StreamToString.ConvertStream(file);


			InputStream is = new ByteArrayInputStream(DataString.getBytes());

			JsonParser jsonParser;
			jsonParser = new JsonParser();

			Data = jsonParser.getPointsFromPointJson(is);

			double value = Data[ServletAnalyzeFunction.Calc(Data)].GetY();
			return value;

		} catch (JsonParserException ex) {
			ex.printStackTrace();
			return 0;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return 0;
		}

	}

	//temporary API

	/**
	 * @param fileName - unique name of file with points to use in command
	 * @param engine   - Rserve, Renjin, or Fake
	 * @param command  - correct R command (like x[1], mean(y) etc.)
	 * @return result of command
	 * @throws Exception
	 */
	@RequestMapping(value = "/NumberFromR/{engine}/{file_name}/{command}")
	public double RCommandToGetNumber(@PathVariable("file_name") String fileName, @PathVariable("engine") String engine, @PathVariable("command") String command)
			throws Exception {
		if (command == null || command.equals("") || fileName == null || fileName.equals("") || engine == null || engine.equals(""))
			throw new IllegalArgumentException();
		ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);
		String DataString = StreamToString.ConvertStream(file);
		return (new RFacade(engine)).runCommandToGetNumber(command, DataString);
	}


	//temporary API

	/**
	 * @param fileName - unique name of file with points to use in command
	 * @param command  - correct R command (like x[1], mean(y) etc.)
	 * @return result of command
	 * @throws Exception
	 */
	@RequestMapping(value = "/NumberFromRFile/{file_name}/{command}")
	public double RCommandToGetNumberFile(@PathVariable("file_name") String fileName, @PathVariable("command") String command)
			throws Exception {
		if (command == null || command.equals("") || fileName == null || fileName.equals(""))
			throw new IllegalArgumentException();
		return (new RFacade("Renjin")).runCommandToGetNumber(command, 1, "project");
	}


	//temporary API

	/**
	 * @param fileName - unique name of file with points to use in command
	 * @param engine   - Rserve, Renjin, or Fake
	 * @param command  - correct R command (like c(x[1], mean(y)) - you'll get Point(x[1], mean(y)))
	 * @return result of command
	 * @throws Exception
	 */
	@RequestMapping("/PointFromR/{engine}/{file_name}/{command}")
	public String RCommandToGetPoint(@PathVariable("file_name") String fileName, @PathVariable("engine") String engine, @PathVariable("command") String command)
			throws Exception {
		if (command == null || command.equals("") || fileName == null || fileName.equals("") || engine == null || engine.equals(""))
			throw new IllegalArgumentException();
		ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);
		String DataString = StreamToString.ConvertStream(file);
		Point result = (new RFacade(engine)).runCommandToGetPoint(command, DataString);
		return PointToJson.convertPoint(result);
	}


	//temporary API

	/**
	 * @param fileName - unique name of file with points to use in command
	 * @param command  - correct R command (like x[1], mean(y) etc.)
	 * @return result of command
	 * @throws Exception
	 */
	@RequestMapping(value = "/PointFromRFile/{file_name}/{command}")
	public String RCommandToGetPointFile(@PathVariable("file_name") String fileName, @PathVariable("command") String command)
			throws Exception {
		if (command == null || command.equals("") || fileName == null || fileName.equals(""))
			throw new IllegalArgumentException();
		return PointToJson.convertPoint((new RFacade("Renjin")).runCommandToGetPoint(command, 1, "project"));
	}

	//temporary API

	/**
	 * @param fileName - unique name of file with points to use in command
	 * @param engine   - Rserve, Renjin, or Fake
	 * @param command  - correct R command (not designed yet)
	 * @return result of command
	 * @throws Exception
	 */
	@RequestMapping("/PointsFromR/{engine}/{file_name}/{command}")
	public String RCommandToGetPoints(@PathVariable("file_name") String fileName, @PathVariable("engine") String engine, @PathVariable("command") String command)
			throws Exception {
		if (command == null || command.equals("") || fileName == null || fileName.equals("") || engine == null || engine.equals(""))
			throw new IllegalArgumentException();
		ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);
		String DataString = StreamToString.ConvertStream(file);
		List<Point> result = (new RFacade(engine)).runCommandToGetPoints(command, DataString);
		return PointToJson.convertPoints(result);
	}

	//temporary API

	/**
	 * @param fileName - unique name of file with points to use in command
	 * @param command  - correct R command (like x[1], mean(y) etc.)
	 * @return result of command
	 * @throws Exception
	 */
	@RequestMapping(value = "/PointsFromRFile/{file_name}/{command}")
	public String RCommandToGetPointsFile(@PathVariable("file_name") String fileName, @PathVariable("command") String command)
			throws Exception {
		if (command == null || command.equals("") || fileName == null || fileName.equals(""))
			throw new IllegalArgumentException();
		List<Point> result = (new RFacade("Renjin")).runCommandToGetPoints(command, 1, "project");
		return PointToJson.convertPoints(result);
	}

	//temporary API

	/**
	 * @param fileName - unique name of file with points to use in command
	 * @param command  - correct R command (like x[1], mean(y) etc.)
	 * @return result of command
	 * @throws Exception
	 */
	@RequestMapping(value = "/StringFromRFile/{file_name}/{command}")
	public String RCommandToGetStringFile(@PathVariable("file_name") String fileName, @PathVariable("command") String command)
			throws Exception {
		if (command == null || command.equals("") || fileName == null || fileName.equals(""))
			throw new IllegalArgumentException();
		String result = (new RFacade("Renjin")).runCommand(command, 1, "project");
		return result;
	}

	//-------------
	//scripts
	//-------------

	//temporary API

	/**
	 * @param scriptId - id of r file script
	 * @return result of command
	 * @throws Exception
	 */
	@RequestMapping(value = "/NumberFromRScript/{scriptId}")
	public double RScriptToGetNumber(@PathVariable("scriptId") String scriptId)
			throws Exception {
		if (scriptId == null || scriptId.equals(""))
			throw new IllegalArgumentException();
		FileInfo script = FileRepository.getRepo().findFileById(scriptId);
		if (script == null)
			throw new IllegalArgumentException();
		return (new RFacade("Renjin")).runScriptToGetNumber(script.getNameForUser(), script.getData(), 1, "project");
	}

	//temporary API

	/**
	 * @param scriptId - id of r file script
	 * @return result of command
	 * @throws Exception
	 */
	@RequestMapping(value = "/PointFromRScript/{scriptId}")
	public String RScriptToGetPoint(@PathVariable("scriptId") String scriptId)
			throws Exception {
		if (scriptId == null || scriptId.equals(""))
			throw new IllegalArgumentException();
		FileInfo script = FileRepository.getRepo().findFileById(scriptId);
		if (script == null)
			throw new IllegalArgumentException();
		return PointToJson.convertPoint((new RFacade("Renjin")).runScriptToGetPoint(script.getNameForUser(), script.getData(), 1, "project"));
	}

	//temporary API

	/**
	 * @param scriptId - id of r file script
	 * @return result of command
	 * @throws Exception
	 */
	@RequestMapping(value = "/PointsFromRScript/{scriptId}")
	public String RScriptToGetPoints(@PathVariable("scriptId") String scriptId)
			throws Exception {
		if (scriptId == null || scriptId.equals(""))
			throw new IllegalArgumentException();
		FileInfo script = FileRepository.getRepo().findFileById(scriptId);
		if (script == null)
			throw new IllegalArgumentException();
		return PointToJson.convertPoints((new RFacade("Renjin")).runScriptToGetPoints(script.getNameForUser(), script.getData(), 1, "project"));
	}

	//temporary API

	/**
	 * @param scriptId - id of r file script
	 * @return result of command
	 * @throws Exception
	 */
	@RequestMapping(value = "/StringFromRScript/{scriptId}")
	public String RScriptToGetString(@PathVariable("scriptId") String scriptId)
			throws Exception {
		if (scriptId == null || scriptId.equals(""))
			throw new IllegalArgumentException();
		FileInfo script = FileRepository.getRepo().findFileById(scriptId);
		if (script == null)
			throw new IllegalArgumentException();
		return (new RFacade("Renjin")).runScript(script.getNameForUser(), script.getData(), 1, "project");
	}
}
