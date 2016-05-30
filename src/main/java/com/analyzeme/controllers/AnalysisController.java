package com.analyzeme.controllers;

import com.analyzeme.analyze.AnalyzeFunction;
import com.analyzeme.analyze.AnalyzeFunctionFactory;
import com.analyzeme.analyze.Point;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.parsers.JsonParserException;
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

/**
 * Created by Ольга on 16.03.2016.
 */
@RestController
public class AnalysisController {

    //todo return HttpEntity<double>
    //TODO: decide whether to use it with .csv or not
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
            String DataString = StreamToString.convertStream(file);


            InputStream is = new ByteArrayInputStream(DataString.getBytes());

            JsonParser jsonParser;
            jsonParser = new JsonParser();

            Data = jsonParser.getPointsFromPointJson(is);

            double value = Data[ServletAnalyzeFunction.calc(Data)].getY();
            return value;

        } catch (JsonParserException ex) {
            ex.printStackTrace();
            return 0;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
