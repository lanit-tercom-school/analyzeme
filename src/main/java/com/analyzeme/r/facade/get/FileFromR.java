package com.analyzeme.r.facade.get;

import com.analyzeme.analyzers.result.VectorsResult;
import com.analyzeme.data.dataset.DataArray;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.r.facade.RFacade;
import com.analyzeme.scripts.Script;

import java.util.List;
import java.util.Map;

public class FileFromR implements IFromR<VectorsResult> {

    /**
     * calls r using r.facade
     *
     * @param script    - script to call
     * @param userId    - userId of a script creator
     * @param projectId - id of the project with data for script
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public VectorsResult runScript(final Script script,
                                   int userId, String projectId) throws Exception {
        if (script == null || userId == 0 ||
                projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        return RFacade.runScriptToGetVectors(script, userId, projectId);
    }

    /**
     * calls r using r.facade
     *
     * @param script - script to call
     * @param data   - some valid data for command to analyze
     * @return result
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public VectorsResult runScript(final Script script, final DataArray data) throws Exception {
        if (script == null || data == null) {
            throw new IllegalArgumentException();
        }
        return RFacade.runScriptToGetVectors(script, data);
    }
}
