package com.analyzeme.controllers;

import com.analyzeme.analyze.AnalyzeFunction;
import com.analyzeme.analyze.AnalyzeFunctionFactory;
import com.analyzeme.analyze.Point;
import com.analyzeme.parsers.JsonParser;
import com.analyzeme.parsers.JsonParserException;
import com.analyzeme.repository.FileRepository;
import com.analyzeme.streamreader.StreamToString;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Servlet what return minimum from data using fileName by header called "minimum"
 * Created by Sergey on 05.02.2016.
 */

@WebServlet("/GlobalMinServlet")
public class GlobalMinServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Analyze Factory
        AnalyzeFunctionFactory ServletFactory = new AnalyzeFunctionFactory();
        //Create GlobalMinimum function
        AnalyzeFunction GlobalMinimum = ServletFactory.getFunction("GlobalMinimum");
        double minimum;
        Point[] Data = new Point[0];
        String fileName = request.getParameter("fileName");
        ByteArrayInputStream file = FileRepository.repo.getFileByID(fileName);

        try {
            Data = getData(file);
        } catch (JsonParserException e) {
            e.printStackTrace();
        }
        minimum = -4;
        minimum = Data[GlobalMinimum.Calc(Data)].GetY();
        response.setHeader("minimum", String.valueOf(minimum));

    }

    public Point[] getData(ByteArrayInputStream file) throws JsonParserException {
        JsonParser jsonParser;
        jsonParser = new JsonParser(file);
        return jsonParser.getPoints();

    }
}


