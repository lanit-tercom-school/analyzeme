package com.analyzeme.controllers;

/**
 * Created by Кирилл Зубов on 7/8/2016.
 */

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

public class TestFileController {

    @Test
    public void testContentOfFileControllerjSon() throws Exception {
        FileController fileController = new FileController();

        InputStream is = new BufferedInputStream(new FileInputStream
                (new File(this.getClass().getResource("/test_data/x.json").getFile())));
        String mimeType = fileController.controlContentOfFile("x.json", is);
        assertTrue(mimeType.equals("application/json"));
    }

    @Test
    public void testContentOfFileControllerCSV() throws Exception {
        FileController fileController = new FileController();
        InputStream is = new BufferedInputStream(new FileInputStream
                (new File(this.getClass().getResource("/test_data/y.csv").getFile())));
        String mimeType = fileController.controlContentOfFile("y.csv", is);
        assertTrue(mimeType.equals("text/csv"));
    }

    @Test
    public void testContentOfFileControllerXls() throws Exception {
        FileController fileController = new FileController();
        InputStream is = new BufferedInputStream(new FileInputStream
                (new File(this.getClass().getResource("/test_data/z.xlsx").getFile())));
        String mime = fileController.controlContentOfFile("z.xlsx", is);
        assertTrue(mime.equals("application/vnd.ms-excel") ||
                mime.equals("application/msexcel") ||
                mime.equals("application/x-msexcel") ||
                mime.equals("application/x-ms-excel") ||
                mime.equals("application/x-excel") ||
                mime.equals("application/x-dos_ms_excel") ||
                mime.equals("application/xls") ||
                mime.equals("application/x-xlsl") ||
                mime.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

    }

    @Test
    public void testContentOfFileControllerExceXlsx() throws Exception {
        FileController fileController = new FileController();
        InputStream is = new BufferedInputStream(new FileInputStream
                (new File(this.getClass().getResource("/test_data/a.xls").getFile())));
        String mime = fileController.controlContentOfFile("a.xls", is);
        assertTrue(mime.equals("application/vnd.ms-excel") ||
                mime.equals("application/msexcel") ||
                mime.equals("application/x-msexcel") ||
                mime.equals("application/x-ms-excel") ||
                mime.equals("application/x-excel") ||
                mime.equals("application/x-dos_ms_excel") ||
                mime.equals("application/xls") ||
                mime.equals("application/x-xlsl") ||
                mime.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }
}