package com.analyzeme.analyzers;

import com.analyzeme.R.facade.DefaultFromR;
import com.analyzeme.R.facade.NumberFromR;
import com.analyzeme.R.facade.PointFromR;
import com.analyzeme.R.facade.PointsFromR;
import com.analyzeme.analyze.Point;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Ольга on 10.04.2016.
 */
public class RAnalyzer {

    public double runScriptForNumber(int userId, String projectName, String scriptName, String scriptText) throws Exception {
        return NumberFromR.runScript(scriptName, new ByteArrayInputStream(scriptText.getBytes()), userId, projectName);
    }

    public double runScriptForNumber(int userId, String projectName, String scriptName) throws Exception {
        return NumberFromR.runScript(scriptName, userId, projectName);
    }

    public String runScriptForDefault(int userId, String projectName, String scriptName, String scriptText) throws Exception {
        return DefaultFromR.runScript(scriptName, new ByteArrayInputStream(scriptText.getBytes()), userId, projectName);
    }

    public String runScriptForDefault(int userId, String projectName, String scriptName) throws Exception {
        return DefaultFromR.runScript(scriptName, userId, projectName);
    }

    public Point runScriptForPoint(int userId, String projectName, String scriptName, String scriptText) throws Exception {
        return PointFromR.runScript(scriptName, new ByteArrayInputStream(scriptText.getBytes()), userId, projectName);
    }

    public Point runScriptForPoint(int userId, String projectName, String scriptName) throws Exception {
        return PointFromR.runScript(scriptName, userId, projectName);
    }

    public List<Point> runScriptForPoints(int userId, String projectName, String scriptName, String scriptText) throws Exception {
        return PointsFromR.runScript(scriptName, new ByteArrayInputStream(scriptText.getBytes()), userId, projectName);
    }

    public List<Point> runScriptForPoints(int userId, String projectName, String scriptName) throws Exception {
        return PointsFromR.runScript(scriptName, userId, projectName);
    }
}
