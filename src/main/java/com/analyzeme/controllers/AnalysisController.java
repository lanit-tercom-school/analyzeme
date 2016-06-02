package com.analyzeme.controllers;

import com.analyzeme.analyze.AnalyzeFunction;
import com.analyzeme.analyze.AnalyzeFunctionFactory;
import com.analyzeme.analyze.Point;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.FileInRepositoryResolver;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.parsers.JsonParserException;
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
    /**
     * @return result of function in double format
     * 0 if file doesn't exist
     * @throws IOException
     */
    @RequestMapping(value = "/file/{user_id}/{project_id}/{reference_name}/{function_Type}",
            method = RequestMethod.GET)
    public double getResult(@PathVariable("user_id") final int userId,
                            @PathVariable("project_id") final String projectId,
                            @PathVariable("reference_name") final String referenceName,
                            @PathVariable("function_Type") final String functionType, HttpServletResponse response)
            throws Exception {
        try {
            AnalyzeFunctionFactory factory = new AnalyzeFunctionFactory();
            AnalyzeFunction analyzeFunction = factory.getFunction(functionType);

            Point[] points;
            FileInRepositoryResolver res = new FileInRepositoryResolver();
            res.setProject(userId, projectId);
            DataSet data = res.getDataSet(referenceName);
            ByteArrayInputStream file = data.getData();
            String dataString = StreamToString.convertStream(file);
            InputStream is = new ByteArrayInputStream(dataString.getBytes());
            JsonParser jsonParser;
            jsonParser = new JsonParser();
            points = jsonParser.getPointsFromPointJson(is);

            double value = points[analyzeFunction.calc(points)].getY();
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
