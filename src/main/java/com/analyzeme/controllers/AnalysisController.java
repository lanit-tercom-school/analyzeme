package com.analyzeme.controllers;

import com.analyzeme.analyzers.AnalyzerFactory;
import com.analyzeme.analyzers.IAnalyzer;
import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.FileInRepositoryResolver;
import com.analyzeme.parsers.InvalidFileException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

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
            List<String> f = new ArrayList<String>();
            if (fields != null) {
                for (String s : fields) {
                    f.add(s);
                }
            }

            FileInRepositoryResolver res = new FileInRepositoryResolver();
            res.setProject(userId, projectId);
            DataSet data = res.getDataSet(referenceName);

            IAnalyzer analyzer = AnalyzerFactory.getAnalyzer(functionType);
            //TODO: this is a temporary if, change when js is ready
            if (f.isEmpty()) {
                Set<String> inFile = data.getFields();
                Iterator<String> iterator = inFile.iterator();
                for (int i = 0; i < analyzer.getNumberOfParams(); i++) {
                    f.add(iterator.next());
                }
                if (analyzer.getNumberOfParams() == 0) {
                    while (iterator.hasNext()) {
                        f.add(iterator.next());
                    }
                }
            }

            Map<String, List<Double>> toAnalyze = new HashMap<>();
            for (String field : f) {
                toAnalyze.put(field, data.getByField(field));
            }

            IResult results = analyzer.analyze(toAnalyze);
            return results.toJson();
        } catch (InvalidFileException ex) {
            return ex.toString();
        } catch (IllegalArgumentException e) {
            return e.toString();
        }

    }
}
