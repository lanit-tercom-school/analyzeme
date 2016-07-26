package com.analyzeme.r.facade;

import com.analyzeme.analyzers.result.NotParsedResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.analyzers.result.VectorResult;
import com.analyzeme.analyzers.result.VectorsResult;
import com.analyzeme.data.dataset.DataEntry;
import com.analyzeme.data.dataset.DataSet;
import com.analyzeme.data.resolvers.FileInRepositoryResolver;
import com.analyzeme.r.call.FakeR;
import com.analyzeme.r.call.IRCaller;
import com.analyzeme.r.call.Renjin;
import com.analyzeme.r.call.Rserve;
import com.analyzeme.scripts.Script;
import org.apache.commons.io.IOUtils;

import java.util.List;
import java.util.Map;

//TODO: when IAnalyzers & ScriptManager are ready, make all public functions here accessible in the package only
public class RFacade {
    private static IRCaller caller;
    //temporary until rconf is finished
    private static final String RSERVE = "Rserve";
    private static final String RENJIN = "Renjin";
    private static final String FAKE = "Fake";

    static {
        caller = new Renjin();
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


    /**
     * calls r using some logic from r.call package
     *
     * @param script - script to call
     * @param data   - some valid data for command to analyze
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public static NotParsedResult runScriptDefault(final Script script,
                                                   final Map<String, List<DataEntry>> data) throws Exception {
        if (script == null || data == null) {
            throw new IllegalArgumentException();
        }
        return caller.runScriptDefault(script, data);
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param script - script to call
     * @param data   - some valid data in json format for command to analyze
     * @return scalar result
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static ScalarResult runScriptToGetScalar(final Script script,
                                                    final Map<String, List<DataEntry>> data)
            throws Exception {
        if (script == null || data == null) {
            throw new IllegalArgumentException();
        }
        ScalarResult result = caller.runScriptToGetScalar(
                script, data);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param script - script to call
     * @param data   - some valid data for command to analyze
     * @return one vector
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static VectorResult runScriptToGetVector(final Script script,
                                                    final Map<String, List<DataEntry>> data) throws Exception {
        if (script == null || data == null) {
            throw new IllegalArgumentException();
        }
        VectorResult result = caller.runScriptToGetVector(
                script, data);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param script - script to call
     * @param data   - some valid data for command to analyze
     * @return group of vectors
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static VectorsResult runScriptToGetVectors(final Script script,
                                                      final Map<String, List<DataEntry>> data)
            throws Exception {
        if (script == null || data == null) {
            throw new IllegalArgumentException();
        }
        VectorsResult result = caller.runScriptToGetVectors(
                script, data);
        return result;
    }

    private static List<DataSet> getSets(final String script,
                                         final int userId,
                                         final String projectId) throws Exception {
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        return RFileLinker.parse(script, resolver);
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param script    - script to call
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static NotParsedResult runScriptDefault(final Script script,
                                                   final int userId, final String projectId)
            throws Exception {
        if (script == null || userId == 0
                || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        List<DataSet> files = getSets(
                script.getScript(), userId, projectId);
        return caller.runScriptDefault(
                script, files);
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param script    - script to call
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return scalar result
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static ScalarResult runScriptToGetScalar(final Script script,
                                                    final int userId, final String projectId) throws Exception {
        if (script == null || userId == 0
                || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        List<DataSet> files = getSets(
                script.getScript(), userId, projectId);
        ScalarResult result = caller.runScriptToGetScalar(
                script, files);
        return result;
    }


    /**
     * calls r using some logic from r.call package
     *
     * @param script    - script to call
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return one vector
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static VectorResult runScriptToGetVector(final Script script,
                                                    final int userId, final String projectId)
            throws Exception {
        if (script == null || userId == 0
                || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        List<DataSet> files = getSets(
                script.getScript(), userId, projectId);
        VectorResult result = caller.runScriptToGetVector(
                script, files);
        return result;
    }


    /**
     * calls r using some logic from r.call package
     *
     * @param script    - script to call
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return List<Point>
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static VectorsResult runScriptToGetVectors(final Script script,
                                                      final int userId,
                                                      final String projectId) throws Exception {
        if (script == null || userId == 0
                || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        List<DataSet> files = getSets(
                script.getScript(), userId, projectId);
        VectorsResult result = caller.runScriptToGetVectors(
                script, files);
        return result;
    }
}
