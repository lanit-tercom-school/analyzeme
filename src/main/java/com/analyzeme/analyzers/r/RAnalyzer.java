package com.analyzeme.analyzers.r;

import com.analyzeme.R.facade.DefaultFromR;
import com.analyzeme.R.facade.NumberFromR;
import com.analyzeme.R.facade.PointFromR;
import com.analyzeme.R.facade.PointsFromR;

import java.io.ByteArrayInputStream;

/**
 * Created by Ольга on 10.04.2016.
 */
public class RAnalyzer {

    public Object runScript(int userId, String projectName, String scriptName, String scriptText,
                            TypeOfReturnValue typeOfReturnValue, TypeOfCall typeOfCall) throws Exception {
        switch (typeOfCall) {
            case MEMORY_CALL:
                switch (typeOfReturnValue) {
                    case DOUBLE:
                        return NumberFromR.runScript(scriptName, userId, projectName);
                    case STRING:
                        return DefaultFromR.runScript(scriptName, userId, projectName);
                    case POINT:
                        return PointFromR.runScript(scriptName, userId, projectName);
                    case POINTS:
                        return PointsFromR.runScript(scriptName, userId, projectName);
                }
                break;
            case RUN:
                switch (typeOfReturnValue) {
                    case DOUBLE:
                        return NumberFromR.runScript(scriptName, new ByteArrayInputStream(scriptText.getBytes()), userId, projectName);
                    case STRING:
                        return DefaultFromR.runScript(scriptName, new ByteArrayInputStream(scriptText.getBytes()), userId, projectName);
                    case POINT:
                        return PointFromR.runScript(scriptName, new ByteArrayInputStream(scriptText.getBytes()), userId, projectName);
                    case POINTS:
                        return PointsFromR.runScript(scriptName, new ByteArrayInputStream(scriptText.getBytes()), userId, projectName);
                }
                break;
        }
        return null;
    }
}

