package com.about.java.controllers;

import Repository.FileRepository;
import StreamReader.StreamToString;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * TODO: line 33
 */

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
        ByteArrayInputStream file = FileRepository.repo.getFileByID(fileName);
        /*
		Convert ByteArrayInputStream into String
         */
        String Data = StreamToString.ConvertStream(file);
        response.setHeader("Data", Data);
    }
}
