package com.analyzeme.scripts;

import com.analyzeme.r.facade.TypeOfReturnValue;
import com.analyzeme.repository.filerepository.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormattedScriptUploader {
    private static final int WRONG_VALUE = -1;
    private static final String REGEXP =
            "((#Name: ((\\w| )+))(( |\\n))+)?" +
                    "(#minN = (\\d+))?(( |\\n)?)+" +
                    "(#n = (\\d+))?(( |\\n)?)+" +
                    "(#Input: (\\w+))?(( |\\n)?)+" +
                    "(#Output: (\\w+))?(( |\\n)?)+(.*)";
    private static final Pattern PATTERN =
            Pattern.compile(REGEXP);
    private static final int NAME_GROUP = 3;
    private static final int MIN_N_GROUP = 8;
    private static final int N_GROUP = 12;
    private static final int INPUT_GROUP = 16;
    private static final int OUTPUT_GROUP = 20;
    private static final int SCRIPT_START_GROUP = 23;
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(
                "com.analyzeme.scripts.FormattedScriptUploader");
    }

    private static String getName(final Matcher m,
                                  final String name) {
        LOGGER.debug("getName(): method started");
        String res = m.group(NAME_GROUP);
        if (res == null || res.isEmpty()) {
            return name;
        } else {
            while (res.endsWith(" ")) {
                res = res.substring(0, (res.length() - 1));
            }
            return res;
        }
    }

    private static int getInt(final Matcher m,
                              final int group) {
        final String res = m.group(group);
        if (res == null || res.isEmpty()) {
            return WRONG_VALUE;
        } else {
            return Integer.parseInt(res);
        }
    }

    private static int chooseNum(final int n,
                                 final int minN) throws IllegalArgumentException {
        if (n == WRONG_VALUE && minN == WRONG_VALUE) {
            throw new IllegalArgumentException();
        } else if (n != WRONG_VALUE) {
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
        if (name.contains(".R")) {
            return name.substring(0, name.indexOf(".R"));
        } else if (name.contains(".r")) {
            return name.substring(0, name.indexOf(".r"));
        }
        return name;
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
            return TypeOfReturnValue.VECTORS;
        }
        LOGGER.info("toTypeOfReturnValue(): not supported argument");
        throw new IllegalArgumentException(
                "This type of return value is not supported");
    }

    private static InputType getInputType(final String type) {
        if (type == null || type.equals("")
                || type.equalsIgnoreCase("EMPTY")) {
            return InputType.EMPTY;
        }
        if (type.equalsIgnoreCase("VECTORS")) {
            return InputType.VECTORS;
        }
        if (type.equalsIgnoreCase("TIME_SERIES")) {
            return InputType.TIME_SERIES;
        }
        LOGGER.info("getInputType(): not supported argument");
        throw new IllegalArgumentException(
                "This type of input value is not supported");
    }

    private static String uploadScriptToRepo(final String script,
                                             final String scriptName) throws IOException {
        LOGGER.debug("uploadScriptToRepo(): method started");
        return FileRepository.getRepo().persist(script,
                scriptName);
    }

    public static IScriptFromBoxBuilder upload(final String script,
                                final String scriptName) throws Exception {
        LOGGER.debug("upload(): method started");
        String scriptTransformed = script.replaceAll(
                "\n", new String(new char[]{(char) 32}));
        scriptTransformed = scriptTransformed.replaceAll(
                "\r", new String(new char[]{(char) 32}));
        LOGGER.debug("upload(): \\n removed ");
        final Matcher m = PATTERN.matcher(scriptTransformed);
        if (m.matches()) {
            LOGGER.debug("upload(): script matches pattern");
            int minN = getInt(m, MIN_N_GROUP);
            int n = getInt(m, N_GROUP);
            int num = chooseNum(n, minN);

            String input = m.group(INPUT_GROUP);
            String output = m.group(OUTPUT_GROUP);

            String trimmedName = trimName(scriptName);
            String name = getName(m, trimmedName);

            String s = trimScript(scriptTransformed, m);
            String id = uploadScriptToRepo(s, scriptName);


            LOGGER.debug("upload(): script is uploaded and ready");
            return new ScriptBuilder()
                    .fromBox()
                    .name(name)
                    .numberOfParams(num)
                    .inputType(getInputType(input))
                    .returnValue(toTypeOfReturnValue(output))
                    .setId(id);

        }
        LOGGER.info("upload(): impossible to upload script");
        throw new IOException("Impossible to upload script");
    }
}
