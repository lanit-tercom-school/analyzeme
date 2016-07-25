package com.analyzeme.r.facade;

import com.analyzeme.analyzers.result.ColumnResult;
import com.analyzeme.analyzers.result.FileResult;
import com.analyzeme.analyzers.result.NotParsedJsonStringResult;
import com.analyzeme.analyzers.result.ScalarResult;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.dataWithType.DataEntry;
import com.analyzeme.data.resolvers.FileInRepositoryResolver;
import com.analyzeme.r.call.FakeR;
import com.analyzeme.r.call.IRCaller;
import com.analyzeme.r.call.Renjin;
import com.analyzeme.r.call.Rserve;

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
     * @param scriptName - name of the script to be called
     * @param rScript    - string with correct r command
     * @param data       - some valid data for command to analyze
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public static NotParsedJsonStringResult runScriptDefault(final String scriptName,
                                                             final String rScript,
                                                             final Map<String, List<DataEntry>> data) throws Exception {
        if (rScript == null || rScript.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        return caller.runScriptDefault(scriptName, rScript, data);
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param scriptName - name of the script to be called
     * @param rScript    - string with correct r command
     * @param data       - some valid data in json format for command to analyze
     * @return scalar result
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static ScalarResult runScriptToGetScalar(final String scriptName,
                                                    final String rScript, final Map<String, List<DataEntry>> data)
            throws Exception {
        if (rScript == null || rScript.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        ScalarResult result = caller.runScriptToGetScalar(scriptName, rScript, data);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param scriptName - name of the script to be called
     * @param rScript    - string with correct r command
     * @param data       - some valid data for command to analyze
     * @return one vector
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static ColumnResult runScriptToGetVector(final String scriptName,
                                                    final String rScript,
                                                    final Map<String, List<DataEntry>> data) throws Exception {
        if (rScript == null || rScript.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        ColumnResult result = caller.runScriptToGetVector(scriptName, rScript, data);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param scriptName - name of the script to be called
     * @param rScript    - string with correct r command
     * @param data       - some valid data for command to analyze
     * @return group of vectors
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static FileResult runScriptToGetVectors(final String scriptName,
                                                   final String rScript,
                                                   final Map<String, List<DataEntry>> data)
            throws Exception {
        if (rScript == null || rScript.equals("") ||
                data == null) {
            throw new IllegalArgumentException();
        }
        FileResult result = caller.runScriptToGetVectors(scriptName, rScript, data);
        return result;
    }

    private static List<DataSet> getSets(final String script, final int userId, final String projectId) throws Exception {
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        return RFileLinker.parse(script, resolver);
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param scriptName - name of the script to be called
     * @param rScript    - string with correct r command
     * @param userId     - userId of a command caller
     * @param projectId  - id of the project with data for command
     * @return auto-generated json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static NotParsedJsonStringResult runScriptDefault(final String scriptName, final String rScript,
                                                             final int userId, final String projectId)
            throws Exception {
        if (rScript == null || rScript.equals("") ||
                userId == 0 || projectId == null ||
                projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        List<DataSet> files = getSets(rScript, userId, projectId);
        return caller.runScriptDefault(scriptName, rScript, files);
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param scriptName - name of the script to be called
     * @param rScript    - string with correct r command
     * @param userId     - userId of a command caller
     * @param projectId  - id of the project with data for command
     * @return scalar result
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static ScalarResult runScriptToGetScalar(final String scriptName,
                                                    final String rScript,
                                                    final int userId, final String projectId) throws Exception {
        if (rScript == null || rScript.equals("") ||
                userId == 0 || projectId == null ||
                projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        List<DataSet> files = getSets(rScript, userId, projectId);
        ScalarResult result = caller.runScriptToGetScalar(scriptName, rScript, files);
        return result;
    }


    /**
     * calls r using some logic from r.call package
     *
     * @param scriptName - name of the script to be called
     * @param rScript    - string with correct r command
     * @param userId     - userId of a command caller
     * @param projectId  - id of the project with data for command
     * @return one vector
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static ColumnResult runScriptToGetVector(final String scriptName,
                                                    final String rScript,
                                                    final int userId, final String projectId)
            throws Exception {
        if (rScript == null || rScript.equals("") ||
                userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        List<DataSet> files = getSets(rScript, userId, projectId);
        ColumnResult result = caller.runScriptToGetVector(
                scriptName, rScript, files);
        return result;
    }


    /**
     * calls r using some logic from r.call package
     *
     * @param scriptName - name of the script to be called
     * @param rScript    - string with correct r command
     * @param userId     - userId of a command caller
     * @param projectId  - id of the project with data for command
     * @return List<Point>
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static FileResult runScriptToGetVectors(final String scriptName,
                                                   final String rScript,
                                                   final int userId,
                                                   final String projectId) throws Exception {
        if (rScript == null || rScript.equals("") ||
                userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        List<DataSet> files = getSets(rScript, userId, projectId);
        FileResult result = caller.runScriptToGetVectors(
                scriptName, rScript, files);
        return result;
    }
}
