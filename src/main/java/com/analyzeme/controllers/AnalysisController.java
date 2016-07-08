package com.analyzeme.controllers;

import com.analyzeme.analyzers.AnalyzerFactory;
import com.analyzeme.analyzers.IAnalyzer;
import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.FileInRepositoryResolver;
import com.analyzeme.parsers.InvalidFileException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ольга on 16.03.2016.
 */
@RestController
public class AnalysisController {
    /**
     * @return result of function
     * 0 if file doesn't exist
     * @throws IOException
     */
    @RequestMapping(value = "/file/{user_id}/{project_id}/{reference_name}/{function_Type}",
            method = RequestMethod.GET)
    public String getResult(@PathVariable("user_id") final int userId,
                            @PathVariable("project_id") final String projectId,
                            @PathVariable("reference_name") final String referenceName,
                            @PathVariable("function_Type") final String functionType,
                            @RequestParam(value = "fields[]", required = false) String[] fields)
            throws Exception {
        try {
            if (fields == null || fields.length == 0) {
                fields = new String[]{"y"};
            }
            FileInRepositoryResolver res = new FileInRepositoryResolver();
            res.setProject(userId, projectId);
            DataSet data = res.getDataSet(referenceName);
            List<List<Double>> toAnalyze = new ArrayList<List<Double>>();
            for (String field : fields) {
                toAnalyze.add(data.getByField(field));
            }
            IAnalyzer analyzer = AnalyzerFactory.getAnalyzer(functionType);
            IResult results = analyzer.analyze(toAnalyze);
            return results.toJson();
        } catch (InvalidFileException ex) {
            return ex.toString();
        } catch (IllegalArgumentException e) {
            return e.toString();
        }

    }
}
