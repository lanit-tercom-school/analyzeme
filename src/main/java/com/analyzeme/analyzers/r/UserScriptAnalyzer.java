package com.analyzeme.analyzers.r;

import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.r.facade.GetFromR;
import com.analyzeme.r.facade.GetFromRFactory;
import com.analyzeme.r.facade.TypeOfReturnValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

public class UserScriptAnalyzer {
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.analyzers.r.UserScriptAnalyzer");
    }

    public static IResult runScript(final int userId,
                                    final String projectId,
                                    final String scriptName,
                                    final String scriptText,
                                    final TypeOfReturnValue typeOfReturnValue,
                                    final TypeOfCall typeOfCall) throws Exception {
        LOGGER.debug("runScript(): method started");
        //scriptText can be null for memory call
        if (userId <= 0 || projectId == null
                || projectId.equals("")
                || (scriptText == null ? true : scriptText.equals(""))
                || typeOfReturnValue == null || typeOfCall == null) {
            LOGGER.info("runScript(): null argument");
            throw new IllegalArgumentException();
        }
        switch (typeOfCall) {
            case MEMORY_CALL:
                LOGGER.debug("runScript(): memory call");
                GetFromR<IResult> rLink =
                        GetFromRFactory.getLinkToR(typeOfReturnValue);
                LOGGER.debug("runScript(): GetFromR<IResult> is found",
                        typeOfReturnValue);
                return rLink.runScript(scriptName, userId, projectId);
            case RUN:
                LOGGER.debug("runScript(): run call");
                GetFromR<IResult> rLinker =
                        GetFromRFactory.getLinkToR(typeOfReturnValue);
                LOGGER.debug("runScript(): GetFromR<IResult> is found",
                        typeOfReturnValue);
                return rLinker.runScript(scriptName,
                        new ByteArrayInputStream(scriptText.getBytes()),
                        userId, projectId);
            default:
                return null;
        }
    }
}

