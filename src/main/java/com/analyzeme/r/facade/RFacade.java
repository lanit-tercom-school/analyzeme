package com.analyzeme.r.facade;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.NotParsedJsonStringResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.r.call.FakeR;
import com.analyzeme.r.call.IRCaller;
import com.analyzeme.r.call.Renjin;
import com.analyzeme.r.call.Rserve;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.FileInRepositoryResolver;
import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.filerepository.FileRepository;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lagroffe on 15.03.2016 21:45
 */

//TODO: when IAnalyzers & ScriptManager are ready, make all public functions here accessible in the package only
//TODO: deprecate jsonData
public class RFacade {
    private static IRCaller caller;
    //temporary until rconf is finished
    private static final String RSERVE = "Rserve";
    private static final String RENJIN = "Renjin";
    private static final String FAKE = "Fake";


    static {
        caller = new Renjin();
        //caller = new FakeR();
    }

    public RFacade(final String type) throws IllegalArgumentException {
        if (type.equals(RSERVE)) {
            caller = new Rserve();
        } else if (type.equals(RENJIN)) {
            caller = new Renjin();
        } else if (type.equals(FAKE)) {
            caller = new FakeR();
        } else {
            throw new IllegalArgumentException();
        }
    }


	/*----------------------------------------------------------------------------------------------------------------------------
    * Different types of command call
	*
	* Differs by:
	* - return value:
	* 		a) default - auto-generated json string
	* 		b) scalar
	* 		c) vector
	* 		d) group of vectors
	* - way to give data:
	* 		a) as json string
	* 		b) as files from repository
	* 	    c) Map<String, List<Double>
	*----------------------------------------------------------------------------------------------------------------------------
     */

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand  - string with correct r command
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public static NotParsedJsonStringResult runCommandDefault(final String rCommand,
                                                              final int userId,
                                                              final String projectId) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                userId == 0 || projectId == null ||
                projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rCommand, resolver);
        return caller.runCommandDefault(rCommand, files);
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand - string with correct r command
     * @param jsonData - some valid data in json format for command to analyze
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public static NotParsedJsonStringResult runCommandDefault(final String rCommand,
                                                              final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        return caller.runCommandDefault(rCommand, jsonData);
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand - string with correct r command
     * @param data     - some valid data for command to analyze
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public static <T> NotParsedJsonStringResult runCommandDefault(final String rCommand,
                                                                  final Map<String, List<T>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        return caller.runCommandDefault(rCommand, data);
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand  - string with correct r command
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return scalar result
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public static ScalarResult runCommandToGetScalar(
            final String rCommand, final int userId,
            final String projectId) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rCommand, resolver);
        ScalarResult result = caller.runCommandToGetScalar(rCommand, files);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand - string with correct r command
     * @param data     - some valid data in json format for command to analyze
     * @return scalar result
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static <T> ScalarResult runCommandToGetScalar(
            final String rCommand, final Map<String, List<T>> data)
            throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        ScalarResult result = caller.runCommandToGetScalar(rCommand, data);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand - string with correct r command
     * @param jsonData - some valid data for command to analyze
     * @return scalar result
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static ScalarResult runCommandToGetScalar(
            final String rCommand, final String jsonData)
            throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        ScalarResult result = caller.runCommandToGetScalar(rCommand, jsonData);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand  - string with correct r command
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return one vector
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public static ColumnResult runCommandToGetVector(final String rCommand,
                                                     final int userId, final String projectId)
            throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rCommand, resolver);
        ColumnResult result = caller.runCommandToGetVector(rCommand, files);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand - string with correct r command
     * @param jsonData - some valid data in json format for command to analyze
     * @return one vector
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static ColumnResult runCommandToGetVector(final String rCommand,
                                                     final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        ColumnResult result = caller.runCommandToGetVector(rCommand, jsonData);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand - string with correct r command
     * @param data     - some valid data for command to analyze
     * @return one vector
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static <T> ColumnResult runCommandToGetVector(final String rCommand,
                                                         final Map<String, List<T>> data) throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        ColumnResult result = caller.runCommandToGetVector(rCommand, data);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand  - string with correct r command
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return group of vectors
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public static FileResult runCommandToGetVectors(final String rCommand,
                                                    final int userId, final String projectId)
            throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rCommand, resolver);
        FileResult result = caller.runCommandToGetVectors(rCommand, files);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand - string with correct r command
     * @param jsonData - some valid data in json format for command to analyze
     * @return group of vectors
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static FileResult runCommandToGetVectors(
            final String rCommand, final String jsonData)
            throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        FileResult result = caller.runCommandToGetVectors(rCommand, jsonData);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand - string with correct r command
     * @param data     - some valid data for command to analyze
     * @return group of vectors
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static <T> FileResult runCommandToGetVectors(
            final String rCommand, final Map<String, List<T>> data)
            throws Exception {
        if (rCommand == null || rCommand.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        FileResult result = caller.runCommandToGetVectors(rCommand, data);
        return result;
    }


	/*----------------------------------------------------------------------------------------------------------------------------
    * Different types of script call
	*
	* Differs by:
	* - return value:
	* 		a) default - json string
	* 		b) scalar
	* 		c) vector
	* 		d) group of vectors
	* - way to point to the script:
	* 		a) as a stream
	* 		b) as file from repository
	*----------------------------------------------------------------------------------------------------------------------------
     */


    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static NotParsedJsonStringResult runScriptDefault(final String rScriptName,
                                                             ByteArrayInputStream rScript, final int userId,
                                                             final String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") ||
                rScript == null || userId == 0 ||
                projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rScript, resolver);
        return caller.runScriptDefault(rScriptName, rScript, files);
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static NotParsedJsonStringResult runScriptDefault(final String rScriptId,
                                                             final int userId, final String projectId)
            throws Exception {
        if (rScriptId == null || rScriptId.equals("") ||
                userId == 0 || projectId == null ||
                projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(script.getData(), resolver);
        return caller.runScriptDefault(script.getUniqueName(), script.getData(), files);
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return scalar result
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static ScalarResult runScriptToGetScalar(String rScriptName,
                                                    ByteArrayInputStream rScript, int userId,
                                                    String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") ||
                rScript == null || userId == 0 ||
                projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rScript, resolver);
        ScalarResult result = caller.runScriptToGetScalar(rScriptName, rScript, files);
        return result;
    }


    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return scalar result
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static ScalarResult runScriptToGetScalar(final String rScriptId,
                                                    final int userId, final String projectId) throws Exception {
        if (rScriptId == null || rScriptId.equals("") ||
                userId == 0 || projectId == null ||
                projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(script.getData(), resolver);
        ScalarResult result = caller.runScriptToGetScalar(script.getUniqueName(), script.getData(), files);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return one vector
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static ColumnResult runScriptToGetVector(final String rScriptName,
                                                    ByteArrayInputStream rScript, final int userId,
                                                    final String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") ||
                rScript == null || userId == 0 ||
                projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rScript, resolver);
        ColumnResult result = caller.runScriptToGetVector(rScriptName, rScript, files);
        return result;
    }


    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return one vector
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static ColumnResult runScriptToGetVector(final String rScriptId,
                                                    final int userId, final String projectId)
            throws Exception {
        if (rScriptId == null || rScriptId.equals("") ||
                userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(script.getData(), resolver);
        ColumnResult result = caller.runScriptToGetVector(script.getUniqueName(), script.getData(), files);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return group of vectors
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static FileResult runScriptToGetVectors(final String rScriptName,
                                                   ByteArrayInputStream rScript, final int userId,
                                                   final String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") ||
                rScript == null || userId == 0 ||
                projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rScript, resolver);
        FileResult result = caller.runScriptToGetVectors(rScriptName, rScript, files);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return List<Point>
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static FileResult runScriptToGetVectors(final String rScriptId,
                                                   final int userId,
                                                   final String projectId) throws Exception {
        if (rScriptId == null || rScriptId.equals("") ||
                userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(script.getData(), resolver);
        FileResult result = caller.runScriptToGetVectors(script.getUniqueName(), script.getData(), files);
        return result;
    }
}
