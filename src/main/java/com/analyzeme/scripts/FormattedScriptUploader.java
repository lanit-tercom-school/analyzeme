package com.analyzeme.scripts;

import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.repository.filerepository.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormattedScriptUploader {
    private final static String REGEXP =
            "(#minN = (\\d+))?(( |\\n)?)+" +
                    "(#n = (\\d+))?(( |\\n)?)+" +
                    "(#Input: (\\w+))?(( |\\n)?)+" +
                    "(#Output: (\\w+))?(( |\\n)?)+(.*)";
    private static final Pattern PATTERN =
            Pattern.compile(REGEXP);
    private static final int MIN_N_GROUP = 2;
    private static final int N_GROUP = 6;
    //private static final int INPUT_GROUP = 10;
    private static final int OUTPUT_GROUP = 14;
    private static final int SCRIPT_START_GROUP = 17;
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.scripts.FormattedScriptUploader");
    }

    private static int getInt(final Matcher m,
                              final int group) {
        String res = m.group(group);
        if (res == null || res.isEmpty()) {
            return -1;
        } else {
            return Integer.parseInt(res);
        }
    }

    private static int chooseNum(final int n,
                                 final int minN) throws IllegalArgumentException {
        if (n == -1 && minN == -1) {
            throw new IllegalArgumentException();
        } else if (n != -1) {
            return n;
        } else {
            return minN;
        }
    }

    private static String trimScript(final String script,
                                     final Matcher m) {
        LOGGER.debug("trimScript(): method started");
        int index = m.start(SCRIPT_START_GROUP);
        return script.substring(index);
    }

    private static String trimName(final String name) {
        LOGGER.debug("trimName(): method started");
        if(name.contains(".R")) {
            return name.substring(0, name.indexOf(".R"));
        } else if(name.contains(".r")) {
            return name.substring(0, name.indexOf(".r"));
        }
        return name;
    }

    private static String uploadScriptToRepo(final String script,
                                             final String scriptName) throws IOException {
        LOGGER.debug("uploadScriptToRepo(): method started");
        return FileRepository.getRepo().persist(script,
                scriptName);
    }

    private static TypeOfReturnValue toTypeOfReturnValue(
            final String type) throws IllegalArgumentException {
        LOGGER.debug("toTypeOfReturnValue(): method started");
        if (type.equalsIgnoreCase("DOUBLE")) {
            return TypeOfReturnValue.DOUBLE;
        }
        if (type.equalsIgnoreCase("JSON_STRING")) {
            return TypeOfReturnValue.JSON_STRING;
        }
        if (type.equalsIgnoreCase("SCALAR_DOUBLE")) {
            return TypeOfReturnValue.SCALAR;
        }
        if (type.equalsIgnoreCase("VECTOR_DOUBLE")) {
            return TypeOfReturnValue.VECTOR;
        }
        if (type.equalsIgnoreCase("VECTORS_DOUBLE")) {
            return TypeOfReturnValue.FILE;
        }
        LOGGER.info("toTypeOfReturnValue(): not supported argument");
        throw new IllegalArgumentException(
                "This type of return value is not supported");
    }

    public static Script upload(final String script,
                                final String scriptName) throws IOException {
        LOGGER.debug("upload(): method started");
        String scriptTransformed = script.replaceAll(
                "\n", new String(new char[]{(char)32}));
        scriptTransformed = scriptTransformed.replaceAll(
                "\r", new String(new char[]{(char)32}));
        LOGGER.debug("upload(): \\n removed ");
        Matcher m = PATTERN.matcher(scriptTransformed);
        if (m.matches()) {
            LOGGER.debug("upload(): script matches pattern");
            int minN = getInt(m, MIN_N_GROUP);
            int n = getInt(m, N_GROUP);
            int num = chooseNum(n, minN);

            //String input = m.group(INPUT_GROUP);
            String output = m.group(OUTPUT_GROUP);

            String s = trimScript(scriptTransformed, m);
            String id = uploadScriptToRepo(s, scriptName);
            Script result = new Script(trimName(scriptName),
                    id, num, toTypeOfReturnValue(output),
                    ScriptSource.DISK_DEFAULT);
            LOGGER.debug("upload(): script is uploaded and ready");
            return result;
        }
        LOGGER.info("upload(): impossible to upload script");
        throw new IOException("Impossible to upload script");
    }
}
