package com.analyzeme.controllers;

/**
 * Created by Кирилл Зубов on 7/8/2016.
 */

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

public class TestFileController {

    @Test
    public void testContentOfFileControllerjSon() throws Exception {
        FileController fileController = new FileController();
        InputStream is = new BufferedInputStream(new FileInputStream("src\\test\\java\\com\\analyzeme\\controllers\\x.json"));
        String mimeType = fileController.controlContentOfFile("x.json", is);
        assertTrue(mimeType.equals("application/json"));
    }

    @Test
    public void testContentOfFileControllerCSV() throws Exception {
        FileController fileController = new FileController();
        InputStream is = new BufferedInputStream(new FileInputStream("src\\test\\java\\com\\analyzeme\\controllers\\y.csv"));
        String mimeType = fileController.controlContentOfFile("y.csv", is);
        assertTrue(mimeType.equals("text/csv"));
    }

    @Test
    public void testContentOfFileControllerXls() throws Exception {
        FileController fileController = new FileController();
        InputStream is = new BufferedInputStream(new FileInputStream("src\\test\\java\\com\\analyzeme\\controllers\\z.xlsx"));
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
        InputStream is = new BufferedInputStream(new FileInputStream("src\\test\\java\\com\\analyzeme\\controllers\\a.xls"));
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