package com.analyzeme.controllers;

import com.analyzeme.analyzers.AnalyzerFactory;
import com.analyzeme.analyzers.IAnalyzer;
import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.FileInRepositoryResolver;
import com.analyzeme.parsers.InvalidFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;


@RestController
public class AnalysisController {
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.controllers.AnalysisController");
    }

    /**
     * @return result of function
     * exception trace if file doesn't exist
     * @throws IOException
     */
    @RequestMapping(value = "/file/{user_id}/{project_id}/{reference_name}/{function_Type}",
            method = RequestMethod.GET)
    public String getResult(@PathVariable("user_id") final int userId,
                            @PathVariable("project_id") final String projectId,
                            @PathVariable("reference_name") final String referenceName,
                            @PathVariable("function_Type") final String functionType,
                            @RequestParam(value = "fields[]",
                                    required = false) String[] fields)
            throws Exception {
        LOGGER.debug("getResult called");
        try {
            List<String> f = new ArrayList<String>();
            if (fields != null) {
                for (String s : fields) {
                    f.add(s);
                }
            }

            LOGGER.trace("Attempt to find file to analyze "
                    + referenceName + " for project "
                    + projectId + " for user " + userId);
            FileInRepositoryResolver res = new FileInRepositoryResolver();
            res.setProject(userId, projectId);
            DataSet data = res.getDataSet(referenceName);
            LOGGER.trace("Got DataSet for " + referenceName);

            IAnalyzer analyzer = AnalyzerFactory.getAnalyzer(functionType);
            LOGGER.trace("Got Analyzer " + functionType);

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
            LOGGER.trace("Keys for data is ready (from "
                    + referenceName + ")");

            Map<String, List<Double>> toAnalyze = new HashMap<>();
            for (String field : f) {
                toAnalyze.put(field, data.getByField(field));
            }
            LOGGER.trace("Data to analyze is ready (from "
                    + referenceName + ")");

            IResult results = analyzer.analyze(toAnalyze);
            LOGGER.debug("Got result of analysis: analyzer "
                    + functionType + " for "
                    + referenceName + " for project "
                    + projectId + " for user " + userId);
            return results.toJson();

        } catch (Exception ex) {
            LOGGER.info(ex.toString());
            return ex.toString();
        }
    }
}
