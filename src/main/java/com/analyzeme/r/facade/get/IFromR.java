package com.analyzeme.r.facade.get;

import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.scripts.Script;

import java.util.List;
import java.util.Map;

public interface IFromR<T> {

    /**
     * calls r using r.facade
     *
     * @param script    - script to call
     * @param userId    - userId of a script creator
     * @param projectId - id of the project with data for script
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    T runScript(final Script script, int userId, String projectId) throws Exception;

    /**
     * calls r using r.facade
     *
     * @param script    - script to call
     * @param data    - some valid data for command to analyze
     * @return result
     * @throws Exception if r was impossible to call or there was in error in command
     */
    T runScript(final Script script, final Map<String, List<DataEntry>> data) throws Exception;
}
