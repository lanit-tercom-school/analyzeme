package com.analyzeme.analyzers.r;

import com.analyzeme.analyzers.result.IResult;
import com.analyzeme.r.facade.get.IFromR;
import com.analyzeme.r.facade.GetFromRFactory;
import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.scripts.InputType;
import com.analyzeme.scripts.Script;
import com.analyzeme.scripts.ScriptSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                || scriptText == null || scriptText.equals("")
                || typeOfReturnValue == null || typeOfCall == null) {
            LOGGER.info("runScript(): null argument");
            throw new IllegalArgumentException();
        }
        switch (typeOfCall) {
            case MEMORY_CALL:
                LOGGER.debug("runScript(): memory call");
                IFromR<IResult> rLink =
                        GetFromRFactory.getLinkToR(typeOfReturnValue);
                LOGGER.debug("runScript(): IFromR<IResult> is found",
                        typeOfReturnValue);
                //here script should be extracted from repo
                Script script = new Script(scriptName, null, 0,
                        typeOfReturnValue,
                        ScriptSource.LIBRARY,
                        scriptText, InputType.VECTORS);
                return rLink.runScript(script, userId, projectId);
            case RUN:
                LOGGER.debug("runScript(): run call");
                IFromR<IResult> rLinker =
                        GetFromRFactory.getLinkToR(typeOfReturnValue);
                LOGGER.debug("runScript(): IFromR<IResult> is found",
                        typeOfReturnValue);
                Script scr = new Script(scriptName, null, 0,
                        typeOfReturnValue,
                        ScriptSource.LIBRARY,
                        scriptText, InputType.VECTORS);
                return rLinker.runScript(scr, userId, projectId);
            default:
                return null;
        }
    }
}

