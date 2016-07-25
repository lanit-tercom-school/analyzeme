package com.analyzeme.r.facade.get;

import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.r.facade.RFacade;

import java.util.List;
import java.util.Map;

public class FileFromR<T> implements IFromR<FileResult<T>> {

    /**
     * calls r using r.facade
     *
     * @param scriptName - name of the script to be called
     * @param rScript    - script to call, correct .r file as a stream
     * @param userId     - userId of a script creator
     * @param projectId  - id of the project with data for script
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public FileResult runScript(final String scriptName, final String rScript,
                                int userId, String projectId) throws Exception {
        if (rScript == null || userId == 0 ||
                projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        return RFacade.runScriptToGetVectors(scriptName, rScript, userId, projectId);
    }

    /**
     * calls r using r.facade
     *
     * @param scriptName - name of the script to be called
     * @param rScript    - string with correct r command
     * @param data       - some valid data for command to analyze
     * @return result
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public <U> FileResult runScript(final String scriptName, final String rScript, final Map<String, List<U>> data) throws Exception {
        if (rScript == null || rScript.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        return RFacade.runScriptToGetVectors(scriptName, rScript, data);
    }
}
