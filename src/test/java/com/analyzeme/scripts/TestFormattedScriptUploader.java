package com.analyzeme.scripts;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * not a real test (for manual testing)
 */
public class TestFormattedScriptUploader {
    private static final String TEST_SCRIPT =
            "#n = 2 #Input: TIME_ONE_DIM #Output: VECTOR_DOUBLE  " +
                    "a <- col_0[5];  b <- mean(col_1); r <- c(a, b)";
    private static final String TEST_SCRIPT_NAME =
            "test.r";
    private static final String TEST_SCRIPT_MULTI_LINE =
            "#minN = 2\n" +
                    "#Output: VECTOR_DOUBLE\n" +
                    "a <- col_0[5];\n" +
                    "b <- mean(col_1);\n" +
                    "r <- c(a, b)";

    @Test
    public void testUploading() throws Exception {
        Script uploaded = FormattedScriptUploader.upload(TEST_SCRIPT, TEST_SCRIPT_NAME);
        System.out.println(uploaded.getName());
        System.out.println(IOUtils.toString(uploaded.getScript()));
    }

    @Test
    public void testUploadingMultiLine() throws Exception {
        Script uploaded = FormattedScriptUploader.upload(TEST_SCRIPT_MULTI_LINE, TEST_SCRIPT_NAME);
        System.out.println(uploaded.getName());
        System.out.println(IOUtils.toString(uploaded.getScript()));
    }
}
