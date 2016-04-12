package com.analyzeme.controllers;

import com.analyzeme.analyze.Point;
import com.analyzeme.analyzers.RAnalyzer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ольга on 12.04.2016.
 */
@RestController
public class RConsoleController {

    RAnalyzer rAnalyzer = new RAnalyzer();

    /**
     * general info for all controllers
     * <p>
     * in url there is return type after "/get"
     * there are two types of call :
     * "/run" - for script from console
     * "/recollect/and/run" - for script from memory
     * <p>
     * all controllers return ResponseEntity<> with
     * HttpStatus.Conflict if there was an exception during running the script
     * HttpStatus.Accepted and result if ran successfully
     */


    @RequestMapping(value = "/{user_id}/{project_name}/get/number/run", method = RequestMethod.GET)
    public ResponseEntity<Double> runRForNumber(@PathVariable("user_id") int userId,
                                                @PathVariable("project_name") String projectName,
                                                @RequestHeader("name") String scriptName,
                                                @RequestHeader("script") String scriptText) {
        Double result;
        try {
            result = rAnalyzer.runScriptForNumber(userId, projectName, scriptName, scriptText);
        } catch (Exception e) {
            return new ResponseEntity<Double>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Double>(result, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{user_id}/{project_name}/get/number/recollect/and/run", method = RequestMethod.GET)
    public ResponseEntity<Double> runRForNumber(@PathVariable("user_id") int userId,
                                                @PathVariable("project_name") String projectName,
                                                @RequestHeader("name") String scriptName) {
        Double result;
        try {
            result = rAnalyzer.runScriptForNumber(userId, projectName, scriptName);
        } catch (Exception e) {
            return new ResponseEntity<Double>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Double>(result, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{user_id}/{project_name}/get/string/run", method = RequestMethod.GET)
    public ResponseEntity<String> runRForString(@PathVariable("user_id") int userId,
                                                @PathVariable("project_name") String projectName,
                                                @RequestParam("name") String scriptName,
                                                @RequestParam("script") String scriptText) {
        String result;
        try {
            result = rAnalyzer.runScriptForDefault(userId, projectName, scriptName, scriptText);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<String>(result, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{user_id}/{project_name}/get/string/recollect/and/run", method = RequestMethod.GET)
    public ResponseEntity<String> runRForString(@PathVariable("user_id") int userId,
                                                @PathVariable("project_name") String projectName,
                                                @RequestParam("name") String scriptName) {
        String result;
        try {
            result = rAnalyzer.runScriptForDefault(userId, projectName, scriptName);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<String>(result, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{user_id}/{project_name}/get/point/run", method = RequestMethod.GET)
    public ResponseEntity<Point> runRForPoint(@PathVariable("user_id") int userId,
                                              @PathVariable("project_name") String projectName,
                                              @RequestHeader("name") String scriptName,
                                              @RequestHeader("script") String scriptText) {
        Point result;
        try {
            result = rAnalyzer.runScriptForPoint(userId, projectName, scriptName, scriptText);
        } catch (Exception e) {
            return new ResponseEntity<Point>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Point>(result, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{user_id}/{project_name}/getPoint/recollect/and/run", method = RequestMethod.GET)
    public ResponseEntity<Point> runRForPoint(@PathVariable("user_id") int userId,
                                              @PathVariable("project_name") String projectName,
                                              @RequestHeader("name") String scriptName) {
        Point result;
        try {
            result = rAnalyzer.runScriptForPoint(userId, projectName, scriptName);
        } catch (Exception e) {
            return new ResponseEntity<Point>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Point>(result, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{user_id}/{project_name}/get/points/run", method = RequestMethod.GET)
    public ResponseEntity<List<Point>> runRForPoints(@PathVariable("user_id") int userId,
                                                     @PathVariable("project_name") String projectName,
                                                     @RequestHeader("name") String scriptName,
                                                     @RequestHeader("script") String scriptText) {
        List<Point> result;
        try {
            result = rAnalyzer.runScriptForPoints(userId, projectName, scriptName, scriptText);
        } catch (Exception e) {
            return new ResponseEntity<List<Point>>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<List<Point>>(result, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{user_id}/{project_name}/get/points/recollect/and/run", method = RequestMethod.GET)
    public ResponseEntity<List<Point>> runRForPoints(@PathVariable("user_id") int userId,
                                                     @PathVariable("project_name") String projectName,
                                                     @RequestHeader("name") String scriptName) {
        List<Point> result;
        try {
            result = rAnalyzer.runScriptForPoints(userId, projectName, scriptName);
        } catch (Exception e) {
            return new ResponseEntity<List<Point>>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<List<Point>>(result, HttpStatus.ACCEPTED);
    }
}

