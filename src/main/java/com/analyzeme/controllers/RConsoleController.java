package com.analyzeme.controllers;

import com.analyzeme.analyzers.r.RAnalyzer;
import com.analyzeme.analyzers.r.TypeOfCall;
import com.analyzeme.R.facade.TypeOfReturnValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ольга on 12.04.2016.
 */
@RestController
public class RConsoleController {

    RAnalyzer rAnalyzer = new RAnalyzer();

    /**
     * @param userId
     * @param projectId
     * @param typeOfCall
     * @param typeOfResult
     * @param scriptName
     * @param scriptText
     * @return ResponseEntity<> with
     * HttpStatus.Conflict if there was an exception during running the script
     * HttpStatus.Accepted and result if ran successfully
     */

    @RequestMapping(value = "/{user_id}/{project_id}/run/script", method = RequestMethod.POST)
    public ResponseEntity<Object> runRForNumber(@PathVariable("user_id") int userId,
                                                @PathVariable("project_id") String projectId,
                                                @RequestHeader("type_of_call") TypeOfCall typeOfCall,
                                                @RequestHeader("type_of_result") TypeOfReturnValue typeOfResult,
                                                @RequestHeader("name") String scriptName,
                                                @RequestBody String scriptText) {
        Object result;
        try {
            result = rAnalyzer.runScript(userId, projectId, scriptName, scriptText, typeOfResult, typeOfCall);
        } catch (Exception e) {
            return new ResponseEntity<Object>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Object>(result, HttpStatus.ACCEPTED);
    }
}
