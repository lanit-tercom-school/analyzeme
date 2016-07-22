package com.analyzeme.r.facade;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by lagroffe on 13.04.2016 0:18
 */
public interface GetFromR<T> {

    /**
     * calls r using r.facade
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return result
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    T runScript(final String rScriptName, ByteArrayInputStream rScript,
                final int userId, final String projectId) throws Exception;


    /**
     * calls r using r.facade
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return result
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    T runScript(final String rScriptId, final int userId,
                final String projectId) throws Exception;

    /**
     * calls r using r.facade
     *
     * @param rCommand  - string with correct r command
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return result
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    T runCommand(final String rCommand, final int userId,
                 final String projectId) throws Exception;

    /**
     * calls r using r.facade
     *
     * @param rCommand - string with correct r command
     * @param data     - some valid data for command to analyze
     * @return result
     * @throws Exception if r was impossible to call or there was in error in command
     */
    <U> T runCommand(final String rCommand, final Map<String, List<U>> data) throws Exception;
}
