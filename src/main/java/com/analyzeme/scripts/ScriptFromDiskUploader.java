package com.analyzeme.scripts;

import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.repository.filerepository.FileRepository;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptFromDiskUploader {
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

    private static String trimScript(final String script, final Matcher m) {
        int index = m.start(SCRIPT_START_GROUP);
        return script.substring(index);
    }

    private static String uploadScriptToRepo(final String script,
                                             final String scriptName) throws IOException {
        return FileRepository.getRepo().persist(script,
                scriptName);
    }

    private static TypeOfReturnValue toTypeOfReturnValue(
            final String type) throws IllegalArgumentException {
        if (type.equalsIgnoreCase("DOUBLE")) {
            return TypeOfReturnValue.DOUBLE;
        }
        if (type.equalsIgnoreCase("JSON_STRING")) {
            return TypeOfReturnValue.JSON_STRING;
        }
        if (type.equalsIgnoreCase("SCALAR")) {
            return TypeOfReturnValue.SCALAR;
        }
        if (type.equalsIgnoreCase("VECTOR")) {
            return TypeOfReturnValue.VECTOR;
        }
        if (type.equalsIgnoreCase("FILE")) {
            return TypeOfReturnValue.FILE;
        }
        throw new IllegalArgumentException(
                "This type of return value is not supported");
    }

    public static Script upload(final String script,
                                final String scriptName) throws IOException {
        Matcher m = PATTERN.matcher(script);
        if (m.matches()) {
            int minN = getInt(m, MIN_N_GROUP);
            int n = getInt(m, N_GROUP);
            int num = chooseNum(n, minN);

            //String input = m.group(INPUT_GROUP);
            String output = m.group(OUTPUT_GROUP);

            String s = trimScript(script, m);
            String id = uploadScriptToRepo(s, scriptName);

            Script result = new Script(scriptName,
                    id, num, toTypeOfReturnValue(output),
                    ScriptSource.DISK_DEFAULT);
            return result;
        }
        throw new IOException("Impossible to upload script");
    }
}
