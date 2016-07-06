package com.analyzeme.r.facade;


import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.ScalarResult;

import java.io.ByteArrayInputStream;

/**
 * Created by lagroffe on 25.03.2016 1:26
 */

/**
 * TODO: in the future manager should be able to:
 * TODO: - store some information about currently running scripts and maybe optimise parameters in Settings
 * TODO: - give some information about currently running scripts
 */

public class RScriptManager {

	/*----------------------------------------------------------------------------------------------------------------------------
    * Different types of script call
	*
	* Differs by:
	* - return value:
	* 		a) default - auto-generated json string
	* 		b) scalar
	* 		c) vector
	* 		d) group of vectors
	* - way to point to the script:
	* 		a) as a stream
	* 		b) as file from repository
	*----------------------------------------------------------------------------------------------------------------------------
     */


    /**
     * calls r using some logic from RFacade TODO: class should remember about it
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    static String runScriptDefault(final String rScriptName,
                                   ByteArrayInputStream rScript, final int userId,
                                   final String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        return RFacade.runScriptDefault(rScriptName, rScript, userId, projectId);
    }

    /**
     * calls r using some logic from RFacade TODO: class should remember about it
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    static String runScriptDefault(final String rScriptId, final int userId,
                                   final String projectId) throws Exception {
        if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        return RFacade.runScriptDefault(rScriptId, userId, projectId);
    }

    /**
     * calls r using some logic from RFacade TODO: class should remember about it
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return scalar result
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    static ScalarResult runScriptToGetScalar(final String rScriptName,
                                             ByteArrayInputStream rScript, final int userId,
                                             final String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        return RFacade.runScriptToGetScalar(rScriptName, rScript, userId, projectId);
    }


    /**
     * calls r using some logic from RFacade TODO: class should remember about it
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return scalar result
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    static ScalarResult runScriptToGetScalar(final String rScriptId, final int userId,
                                             final String projectId) throws Exception {
        if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        return RFacade.runScriptToGetScalar(rScriptId, userId, projectId);
    }

    /**
     * calls r using some logic from RFacade TODO: class should remember about it
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return one vector
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    static ColumnResult runScriptToGetVector(final String rScriptName,
                                             ByteArrayInputStream rScript, final int userId,
                                             final String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        return RFacade.runScriptToGetVector(rScriptName, rScript, userId, projectId);
    }

    /**
     * calls r using some logic from RFacade TODO: class should remember about it
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return one vector
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    static ColumnResult runScriptToGetVector(final String rScriptId, final int userId,
                                             final String projectId) throws Exception {
        if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        return RFacade.runScriptToGetVector(rScriptId, userId, projectId);
    }


    /**
     * calls r using some logic from RFacade TODO: class should remember about it
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return group of vectors
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    static FileResult runScriptToGetVectors(final String rScriptName,
                                            ByteArrayInputStream rScript, final int userId,
                                            final String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        return RFacade.runScriptToGetVectors(rScriptName, rScript, userId, projectId);
    }

    /**
     * calls r using some logic from RFacade TODO: class should remember about it
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return group of vectors
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    static FileResult runScriptToGetVectors(final String rScriptId,
                                            final int userId, final String projectId) throws Exception {
        if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        return RFacade.runScriptToGetVectors(rScriptId, userId, projectId);
    }
}
