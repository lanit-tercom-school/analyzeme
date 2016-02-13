package com.about.java.controllers;

import Repository.FileRepository;
import fileReader.FileToString;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Servlet what return data from file using fileName by header called "Data"
 * Created by Sergey on 07.02.2016.
 */

@WebServlet("/GetDataServlet")
public class GetDataServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fileName = request.getParameter("fileName");
        File file = FileRepository.repo.getFileByID(fileName);
        String Data = FileToString.convertFile(String.valueOf(file), StandardCharsets.UTF_8);
        response.setHeader("Data", Data);
    }

}