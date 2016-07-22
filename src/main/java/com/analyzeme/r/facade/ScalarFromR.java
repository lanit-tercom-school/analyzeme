package com.analyzeme.r.facade;

import com.analyzeme.analyzers.result.ScalarResult;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by lagroffe on 19.03.2016 21:19
 */

public class ScalarFromR<T> implements GetFromR<ScalarResult<T>> {

    /**
     * calls r using r.facade
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return scalar result
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public ScalarResult<T> runScript(String rScriptName,
                                     ByteArrayInputStream rScript, int userId,
                                     String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") ||
                rScript == null || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        ScalarResult<T> result = RScriptManager.runScriptToGetScalar(
                rScriptName, rScript, userId, projectId);
        return result;
    }


    /**
     * calls r using r.facade
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return scalar result
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public ScalarResult<T> runScript(String rScriptId, int userId, String projectId) throws Exception {
        if (rScriptId == null || rScriptId.equals("") ||
                userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        ScalarResult<T> result = RScriptManager.runScriptToGetScalar(
                rScriptId, userId, projectId);
        return result;
    }

    /**
     * calls r using r.facade
     *
     * @param rCommand  - string with correct r command
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return scalar result
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public ScalarResult<T> runCommand(String rCommand, int userId, String projectId) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        ScalarResult<T> result = RFacade.runCommandToGetScalar(rCommand, userId, projectId);
        return result;
    }

    /**
     * calls r using r.facade
     *
     * @param rCommand - string with correct r command
     * @param data     - some valid data for command to analyze
     * @return result
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public <U> ScalarResult<T> runCommand(final String rCommand, final Map<String, List<U>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        ScalarResult<T> result = RFacade.runCommandToGetScalar(rCommand, data);
        return result;
    }
}
