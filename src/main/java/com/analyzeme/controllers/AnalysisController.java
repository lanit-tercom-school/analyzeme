package com.analyzeme.controllers;

import com.analyzeme.R.call.Rserve;
import com.analyzeme.analyze.AnalyzeFunction;
import com.analyzeme.analyze.AnalyzeFunctionFactory;
import com.analyzeme.repository.FileRepository;
import com.analyzeme.streamreader.StreamToString;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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
	public String getData(@PathVariable("file_name") String fileName)
			throws IOException {
		try {
			ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);
		/*
		Convert ByteArrayInputStream into String
         */
			return StreamToString.ConvertStream(file);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/RserveCommand/{file_name}/{command}")
	public double RserveCommand(@PathVariable("file_name") String fileName, @PathVariable("command") String command)
			throws Exception {
		Rserve call = new Rserve();
		ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);
		String DataString = StreamToString.ConvertStream(file);
		return call.runCommandToGetNumber(command, DataString);
	}

	//todo return HttpEntity<double>

	/**
	 * @param fileName
	 * @return minimum in double format
	 * 0 if file doesn't exist
	 * @throws IOException
	 */
	@RequestMapping("/file/{file_name}/minimum")
	public double getMinimum(@PathVariable("file_name") String fileName)
			throws IOException {
		try {
			//Analyze Factory
			AnalyzeFunctionFactory ServletFactory = new AnalyzeFunctionFactory();
			//Create GlobalMinimum function
			AnalyzeFunction GlobalMinimum = ServletFactory.getFunction("GlobalMinimum");
			double minimum;

			// String fileName = request.getParameter("fileName");

			ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);

         /*
		Convert ByteArrayInputStream into String
         */
			String Data = StreamToString.ConvertStream(file);

			//DataArray = new Point[];
			//minimum=DataArray[GlobalMinimum.Calc(DataArray)].y;
			minimum = -4;
			return minimum;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}

