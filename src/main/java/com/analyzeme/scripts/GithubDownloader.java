package com.analyzeme.scripts;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class GithubDownloader {
    private static final String LINK = "https://raw.githubusercontent.com/lagroffe/rscriptsForAnalyzeme/master/";

    private static boolean isRedirected(Map<String, List<String>> header) {
        for (String hv : header.get(null)) {
            if (hv.contains(" 301 ")
                    || hv.contains(" 302 ")) return true;
        }
        return false;
    }

    public static InputStream download(final String fileName) throws Exception {
        String link = LINK + fileName;
        URL url = new URL(link);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        Map<String, List<String>> header = http.getHeaderFields();
        while (isRedirected(header)) {
            link = header.get("Location").get(0);
            url = new URL(link);
            http = (HttpURLConnection) url.openConnection();
            header = http.getHeaderFields();
        }
        InputStream input = http.getInputStream();
        //System.out.println(IOUtils.toString(input));
        return input;
    }
}
