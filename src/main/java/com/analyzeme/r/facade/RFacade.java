package com.analyzeme.r.facade;

import com.analyzeme.r.call.FakeR;
import com.analyzeme.r.call.IRCaller;
import com.analyzeme.r.call.Renjin;
import com.analyzeme.r.call.Rserve;
import com.analyzeme.analyze.Point;
import com.analyzeme.data.DataSet;
import com.analyzeme.data.resolvers.FileInRepositoryResolver;
import com.analyzeme.repository.filerepository.FileInfo;
import com.analyzeme.repository.filerepository.FileRepository;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagroffe on 15.03.2016 21:45
 */

//TODO: when IAnalyzers are ready, make all public functions here accessible in the package only

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


	/*----------------------------------------------------------------------------------------------------------------------------
    * Different types of command call
	*
	* Differs by:
	* - return value:
	* 		a) default - json string
	* 		b) double
	* 		c) Point
	* 		d) List<Point>
	* - way to give data:
	* 		a) as json string
	* 		b) as files from repository
	*----------------------------------------------------------------------------------------------------------------------------
     */

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand  - string with correct r command
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public static String runCommand(final String rCommand, final int userId, final String projectId) throws Exception {
        if (rCommand == null || rCommand.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rCommand, resolver);
        String result = caller.runCommand(rCommand, files);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand - string with correct r command
     * @param jsonData - some valid data in json format for command to analyze
     * @return json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public static String runCommand(final String rCommand, final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        String result = caller.runCommand(rCommand, jsonData);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand  - string with correct r command
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return double result
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public static double runCommandToGetNumber(final String rCommand, final int userId, final String projectId) throws Exception {
        if (rCommand == null || rCommand.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rCommand, resolver);
        double result = caller.runCommandToGetNumber(rCommand, files);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand - string with correct r command
     * @param jsonData - some valid data in json format for command to analyze
     * @return double result
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static double runCommandToGetNumber(final String rCommand, final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        double result = caller.runCommandToGetNumber(rCommand, jsonData);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand  - string with correct r command
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return one point
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public static Point runCommandToGetPoint(final String rCommand, final int userId, final String projectId) throws Exception {
        if (rCommand == null || rCommand.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rCommand, resolver);
        Point result = caller.runCommandToGetPoint(rCommand, files);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand - string with correct r command
     * @param jsonData - some valid data in json format for command to analyze
     * @return one point
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static Point runCommandToGetPoint(final String rCommand, final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        Point result = caller.runCommandToGetPoint(rCommand, jsonData);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand  - string with correct r command
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return List<Point>
     * @throws Exception if files not found, r was impossible to call or there was in error in command
     */
    public static List<Point> runCommandToGetPoints(final String rCommand, final int userId, final String projectId) throws Exception {
        if (rCommand == null || rCommand.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rCommand, resolver);
        List<Point> result = caller.runCommandToGetPoints(rCommand, files);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rCommand - string with correct r command
     * @param jsonData - some valid data in json format for command to analyze
     * @return List<Point>
     * @throws Exception if r was impossible to call or there was in error in command
     */
    public static List<Point> runCommandToGetPoints(final String rCommand, final String jsonData) throws Exception {
        if (rCommand == null || rCommand.equals("") || jsonData == null || jsonData.equals("")) {
            throw new IllegalArgumentException();
        }
        List<Point> result = caller.runCommandToGetPoints(rCommand, jsonData);
        return result;
    }


	/*----------------------------------------------------------------------------------------------------------------------------
	* Different types of script call
	*
	* Differs by:
	* - return value:
	* 		a) default - json string
	* 		b) double
	* 		c) Point
	* 		d) List<Point>
	* - way to point to the script:
	* 		a) as a stream
	* 		b) as file from repository
	* - way to give data:
	* 		a)  as files from repository
	*----------------------------------------------------------------------------------------------------------------------------
     */


    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static String runScript(final String rScriptName, ByteArrayInputStream rScript, final int userId, final String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rScript, resolver);
        String result = caller.runScript(rScriptName, rScript, files);
        return result;
    }

    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return json result (mistakes are possible)
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static String runScript(final String rScriptId, final int userId, final String projectId) throws Exception {
        if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(script.getData(), resolver);
        String result = caller.runScript(script.getUniqueName(), script.getData(), files);
        return result;
    }


    /*******************************
     * To get number
     * *****************************
     */

    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return double result
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static double runScriptToGetNumber(String rScriptName, ByteArrayInputStream rScript, int userId, String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rScript, resolver);
        double result = caller.runScriptToGetNumber(rScriptName, rScript, files);
        return result;
    }


    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return double result
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static double runScriptToGetNumber(final String rScriptId, final int userId, final String projectId) throws Exception {
        if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(script.getData(), resolver);
        double result = caller.runScriptToGetNumber(script.getUniqueName(), script.getData(), files);
        return result;
    }

    /*******************************
     * To get Point
     * *****************************
     */

    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return one point
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static Point runScriptToGetPoint(final String rScriptName, ByteArrayInputStream rScript, final int userId, final String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rScript, resolver);
        Point result = caller.runScriptToGetPoint(rScriptName, rScript, files);
        return result;
    }


    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptId - id in repository of file with the script to call, correct .r file as a stream  (RScriptName is stored in FileInfo)
     * @param userId    - userId of a command caller
     * @param projectId - id of the project with data for command
     * @return one point
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static Point runScriptToGetPoint(final String rScriptId, final int userId, final String projectId) throws Exception {
        if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(script.getData(), resolver);
        Point result = caller.runScriptToGetPoint(script.getUniqueName(), script.getData(), files);
        return result;
    }


    /*******************************
     * To get List<Point>
     * *****************************
     */

    /**
     * calls r using some logic from r.call package
     *
     * @param rScriptName - name of the script to be called
     * @param rScript     - script to call, correct .r file as a stream
     * @param userId      - userId of a script creator
     * @param projectId   - id of the project with data for script
     * @return List<Point>
     * @throws Exception if files not found, r was impossible to call or there was in error in script
     */
    public static List<Point> runScriptToGetPoints(final String rScriptName, ByteArrayInputStream rScript, final int userId, final String projectId) throws Exception {
        if (rScriptName == null || rScriptName.equals("") || rScript == null || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(rScript, resolver);
        List<Point> result = caller.runScriptToGetPoints(rScriptName, rScript, files);
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
    public static List<Point> runScriptToGetPoints(final String rScriptId, final int userId, final String projectId) throws Exception {
        if (rScriptId == null || rScriptId.equals("") || userId == 0 || projectId == null || projectId.equals("")) {
            throw new IllegalArgumentException();
        }
        FileInfo script = FileRepository.getRepo().findFileById(rScriptId);
        FileInRepositoryResolver resolver = new FileInRepositoryResolver();
        resolver.setProject(userId, projectId);
        ArrayList<DataSet> files = RFileLinker.parse(script.getData(), resolver);
        List<Point> result = caller.runScriptToGetPoints(script.getUniqueName(), script.getData(), files);
        return result;
    }
}
