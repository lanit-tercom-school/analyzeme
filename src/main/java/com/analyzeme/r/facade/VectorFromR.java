package com.analyzeme.r.facade;

import com.analyzeme.analyzers.result.ColumnResult;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by lagroffe on 19.03.2016 21:20
 */

public class VectorFromR<T> implements GetFromR<ColumnResult<T>> {

    /**
     * calls r using r.facade
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return vector of values
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public ColumnResult<T> runScript(String rScriptName,
                                     ByteArrayInputStream rScript, int userId,
                                     String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") ||
                rScript == null || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        ColumnResult<T> result = RScriptManager.runScriptToGetVector(
                rScriptName, rScript, userId, projectId);
        return result;
    }


    /**
     * calls r using r.facade
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return vector of values
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public ColumnResult<T> runScript(String rScriptId, int userId,
                                     String projectId) throws Exception {
        if (rScriptId == null || rScriptId.equals("") ||
                userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        ColumnResult<T> result = RScriptManager.runScriptToGetVector(
                rScriptId, userId, projectId);
        return result;
    }


    /**
     * calls r using r.facade
     *
     * @param rCommand  - string with correct r command
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return vector of values
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public ColumnResult<T> runCommand(String rCommand, int userId,
                                      String projectId) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        ColumnResult<T> result = RFacade.runCommandToGetVector(
                rCommand, userId, projectId);
        return result;
    }

    /**
     * calls r using r.facade
     *
     * @param rCommand - string with correct r command
     * @param jsonData - some valid data in json format for command to analyze
     * @return vector of values
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public ColumnResult<T> runCommand(String rCommand, String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        ColumnResult<T> result = RFacade.runCommandToGetVector(rCommand, jsonData);
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
    public <U> ColumnResult<T> runCommand(final String rCommand, final Map<String, List<U>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        ColumnResult<T> result = RFacade.runCommandToGetVector(rCommand, data);
        return result;
    }
}
