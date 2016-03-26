package com.analyzeme.controllers;

import com.analyzeme.R.facade.RFacade;
import com.analyzeme.analyze.AnalyzeFunction;
import com.analyzeme.analyze.AnalyzeFunctionFactory;
import com.analyzeme.analyze.Point;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.parsers.JsonParserException;
import com.analyzeme.parsers.PointToJson;
import com.analyzeme.repository.FileRepository;
import com.analyzeme.streamreader.StreamToString;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@RequestMapping("/file/{file_name}/data")
	public String doPost(@PathVariable("file_name") String fileName, HttpServletResponse response)
			throws IOException {
		try {
			ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);
		/*
		Convert ByteArrayInputStream into String
         */
			String Data = StreamToString.ConvertStream(file);
			response.setHeader("Data", Data);

			return StreamToString.ConvertStream(file);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @param fileName
	 * @return minimum in double format
	 * 0 if file doesn't exist
	 * @throws IOException
	 */
	@RequestMapping("/file/{file_name}/{function_Type}")
	public int doPost(@PathVariable("file_name") String fileName, @PathVariable("function_Type") String functionType, HttpServletResponse response)
			throws IOException {
		try {
			//Analyze Factory
			AnalyzeFunctionFactory ServletFactory = new AnalyzeFunctionFactory();
			AnalyzeFunction ServletAnalyzeFunction = ServletFactory.getFunction(functionType);

			Point[] Data;

			ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);
			String DataString = StreamToString.ConvertStream(file);


			InputStream is = new ByteArrayInputStream(DataString.getBytes());

			JsonParser jsonParser;
			jsonParser = new JsonParser(is);

			Data = jsonParser.getPointsFromPointJson();

			double value = Data[ServletAnalyzeFunction.Calc(Data)].GetY();
			response.setHeader("value", String.valueOf(value));
			return 1;

		} catch (JsonParserException ex) {
			ex.printStackTrace();
			return 0;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * @param fileName - unique name of file with points to use in command
	 * @param engine   - Rserve, Renjin, or Fake
	 * @param command  - correct R command (like x[1], mean(y) etc.)
	 * @return result of command
	 * @throws Exception
	 */
	@RequestMapping("/NumberFromR/{engine}/{file_name}/{command}")
	public double RCommandToGetNumber(@PathVariable("file_name") String fileName, @PathVariable("engine") String engine, @PathVariable("command") String command)
			throws Exception {
		ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);
		String DataString = StreamToString.ConvertStream(file);
		return (new RFacade(engine)).runCommandToGetNumber(command, DataString);
	}

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
		ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);
		String DataString = StreamToString.ConvertStream(file);
		Point result = (new RFacade(engine)).runCommandToGetPoint(command, DataString);
		return PointToJson.convertPoint(result);
	}

	/**
	 * not working yet
	 *
	 * @param fileName - unique name of file with points to use in command
	 * @param engine   - Rserve, Renjin, or Fake
	 * @param command  - correct R command (not designed yet)
	 * @return result of command
	 * @throws Exception
	 */
	@RequestMapping("/PointsFromR/{engine}/{file_name}/{command}")
	public String RCommandToGetPoints(@PathVariable("file_name") String fileName, @PathVariable("engine") String engine, @PathVariable("command") String command)
			throws Exception {
		ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);
		String DataString = StreamToString.ConvertStream(file);
		List<Point> result = (new RFacade(engine)).runCommandToGetPoints(command, DataString);
		return PointToJson.convertPoints(result);
	}
}

