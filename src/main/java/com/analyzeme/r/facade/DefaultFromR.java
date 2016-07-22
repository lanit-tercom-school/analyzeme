package com.analyzeme.r.facade;

import com.analyzeme.analyzers.result.NotParsedJsonStringResult;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by lagroffe on 19.03.2016 21:03
 */

public class DefaultFromR implements GetFromR<NotParsedJsonStringResult> {

    /**
     * calls r using r.facade
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public NotParsedJsonStringResult runScript(String rScriptName,
                                               ByteArrayInputStream rScript,
                                               int userId, String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") ||
                rScript == null || userId == 0 ||
                projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        return RScriptManager.runScriptDefault(rScriptName,
                rScript, userId, projectId);
    }

    /**
     * calls r using r.facade
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public NotParsedJsonStringResult runScript(String rScriptId, int userId,
                                               String projectId) throws Exception {
        if (rScriptId == null || rScriptId.equals("") ||
                userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        return RScriptManager.runScriptDefault(rScriptId,
                userId, projectId);
    }


    /**
     * calls r using r.facade
     *
     * @param rCommand  - string with correct r command
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public NotParsedJsonStringResult runCommand(String rCommand, int userId,
                                                String projectId) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        return RFacade.runCommandDefault(rCommand, userId, projectId);
    }

    /**
     * calls r using r.facade
     *
     * @param rCommand - string with correct r command
     * @param data     - some valid data for command to analyze
     * @return result
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public <U> NotParsedJsonStringResult runCommand(final String rCommand, final Map<String, List<U>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        return RFacade.runCommandDefault(rCommand, data);
    }
}
