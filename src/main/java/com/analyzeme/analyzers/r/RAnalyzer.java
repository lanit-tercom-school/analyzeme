package com.analyzeme.analyzers.r;

import com.analyzeme.r.facade.GetFromR;
import com.analyzeme.r.facade.GetFromRFactory;
import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.analyze.Point;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by Ольга on 10.04.2016.
 */
public class RAnalyzer {

    public static Object runScript(final int userId,
                                   final String projectId,
                                   final String scriptName,
                                   final String scriptText,
                                   final TypeOfReturnValue typeOfReturnValue,
                                   final TypeOfCall typeOfCall) throws Exception {
        //scriptText can be null for memory call
        if (userId <= 0 || projectId == null
                || projectId.equals("")
                || (scriptText == null ? true : scriptText.equals(""))
                || typeOfReturnValue == null || typeOfCall == null) {
            throw new IllegalArgumentException();
        }
        switch (typeOfCall) {
            case MEMORY_CALL:
                GetFromR<Object> rLink =
                        GetFromRFactory.getLinkToR(typeOfReturnValue);
                return rLink.runScript(scriptName, userId, projectId);
            case RUN:
                GetFromR<Object> rLinker =
                        GetFromRFactory.getLinkToR(typeOfReturnValue);
                return rLinker.runScript(scriptName,
                        new ByteArrayInputStream(scriptText.getBytes()),
                        userId, projectId);
            default:
                return null;
        }
    }

    public static Object runScript(final int userId,
                                   final String projectId,
                                   final String scriptName,
                                   final String scriptText,
                                   final String typeOfReturnValue,
                                   final TypeOfCall typeOfCall) throws Exception {
        //scriptText can be null for memory call
        if (userId <= 0 || projectId == null
                || projectId.equals("")
                || (scriptText == null ? true : scriptText.equals(""))
                || typeOfReturnValue == null || typeOfCall == null) {
            throw new IllegalArgumentException();
        }
        switch (typeOfCall) {
            case MEMORY_CALL:
                if (typeOfReturnValue.equalsIgnoreCase("Double")) {
                    GetFromR<Double> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
                    return rLink.runScript(scriptName, userId, projectId);
                } else if (typeOfReturnValue.equalsIgnoreCase("Point")) {
                    GetFromR<Point> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.VECTOR);
                    return rLink.runScript(scriptName, userId, projectId);

                } else if (typeOfReturnValue.equalsIgnoreCase("Points")) {
                    GetFromR<List<Point>> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.FILE);
                    return rLink.runScript(scriptName, userId, projectId);

                } else if (typeOfReturnValue.equalsIgnoreCase("Default")) {
                    GetFromR<String> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.JSON_STRING);
                    return rLink.runScript(scriptName, userId, projectId);
                }
            case RUN:
                if (typeOfReturnValue.equalsIgnoreCase("Double")) {
                    GetFromR<Double> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
                    return rLink.runScript(scriptName,
                            new ByteArrayInputStream(scriptText.getBytes()),
                            userId, projectId);
                } else if (typeOfReturnValue.equalsIgnoreCase("Point")) {
                    GetFromR<Point> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.VECTOR);
                    return rLink.runScript(scriptName,
                            new ByteArrayInputStream(scriptText.getBytes()),
                            userId, projectId);

                } else if (typeOfReturnValue.equalsIgnoreCase("Points")) {
                    GetFromR<List<Point>> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.FILE);
                    return rLink.runScript(
                            scriptName, new ByteArrayInputStream(scriptText.getBytes()),
                            userId, projectId);

                } else if (typeOfReturnValue.equalsIgnoreCase("Default")) {
                    GetFromR<String> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.JSON_STRING);
                    return rLink.runScript(
                            scriptName, new ByteArrayInputStream(scriptText.getBytes()),
                            userId, projectId);
                }
            default:
                return null;
        }
    }
}

