package com.analyzeme.analyzers.r;

import com.analyzeme.analyzers.result.*;
import com.analyzeme.r.facade.GetFromR;
import com.analyzeme.r.facade.GetFromRFactory;
import com.analyzeme.r.facade.TypeOfReturnValue;

import java.io.ByteArrayInputStream;

/**
 * Created by Ольга on 10.04.2016.
 */
public class RAnalyzer {

    public static IResult runScript(final int userId,
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
                GetFromR<IResult> rLink =
                        GetFromRFactory.getLinkToR(typeOfReturnValue);
                return rLink.runScript(scriptName, userId, projectId);
            case RUN:
                GetFromR<IResult> rLinker =
                        GetFromRFactory.getLinkToR(typeOfReturnValue);
                return rLinker.runScript(scriptName,
                        new ByteArrayInputStream(scriptText.getBytes()),
                        userId, projectId);
            default:
                return null;
        }
    }

    public static IResult runScript(final int userId,
                                    final String projectId,
                                    final String scriptName,
                                    final String scriptText,
                                    final String typeOfReturnValue,
                                    final TypeOfCall typeOfCall) throws Exception {
        if (userId <= 0 || projectId == null
                || projectId.equals("")
                || (scriptText == null ? true : scriptText.equals(""))
                || typeOfReturnValue == null || typeOfCall == null) {
            throw new IllegalArgumentException();
        }
        switch (typeOfCall) {
            case MEMORY_CALL:
                if (typeOfReturnValue.equalsIgnoreCase("Double")) {
                    GetFromR<ScalarResult<Double>> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
                    return rLink.runScript(scriptName, userId, projectId);
                } else if (typeOfReturnValue.equalsIgnoreCase("Scalar")) {
                    GetFromR<ScalarResult> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.SCALAR);
                    return rLink.runScript(scriptName, userId, projectId);
                } else if (typeOfReturnValue.equalsIgnoreCase("Vector")) {
                    GetFromR<ColumnResult> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.VECTOR);
                    return rLink.runScript(scriptName, userId, projectId);
                } else if (typeOfReturnValue.equalsIgnoreCase("File")) {
                    GetFromR<FileResult> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.FILE);
                    return rLink.runScript(scriptName, userId, projectId);
                } else if (typeOfReturnValue.equalsIgnoreCase("Default")) {
                    GetFromR<NotParsedJsonStringResult> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.JSON_STRING);
                    return rLink.runScript(scriptName, userId, projectId);
                }
            case RUN:
                if (typeOfReturnValue.equalsIgnoreCase("Double")) {
                    GetFromR<ScalarResult<Double>> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.DOUBLE);
                    return rLink.runScript(scriptName,
                            new ByteArrayInputStream(scriptText.getBytes()),
                            userId, projectId);
                } else if (typeOfReturnValue.equalsIgnoreCase("Scalar")) {
                    GetFromR<ScalarResult> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.VECTOR);
                    return rLink.runScript(scriptName,
                            new ByteArrayInputStream(scriptText.getBytes()),
                            userId, projectId);
                }else if (typeOfReturnValue.equalsIgnoreCase("Vector")) {
                    GetFromR<ColumnResult> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.VECTOR);
                    return rLink.runScript(scriptName,
                            new ByteArrayInputStream(scriptText.getBytes()),
                            userId, projectId);
                } else if (typeOfReturnValue.equalsIgnoreCase("Points")) {
                    GetFromR<FileResult> rLink =
                            GetFromRFactory.getLinkToR(TypeOfReturnValue.FILE);
                    return rLink.runScript(
                            scriptName, new ByteArrayInputStream(scriptText.getBytes()),
                            userId, projectId);

                } else if (typeOfReturnValue.equalsIgnoreCase("Default")) {
                    GetFromR<NotParsedJsonStringResult> rLink =
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

