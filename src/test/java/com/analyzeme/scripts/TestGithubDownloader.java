package com.analyzeme.scripts;

import org.junit.Test;

/**
 * Created by lagroffe on 20.07.2016 13:34
 * not a real test (for manual testing)
 */
public class TestGithubDownloader {

    @Test
    public void test() throws Exception {
        GithubDownloader.download("r/example_anon_vector.R");
        GithubDownloader.download("rscripts_info.txt");
    }
}
