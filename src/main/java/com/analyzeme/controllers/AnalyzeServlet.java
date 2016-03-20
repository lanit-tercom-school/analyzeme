package com.analyzeme.controllers;

import com.analyzeme.analyze.*;
import com.analyzeme.filereader.FileToString;
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
import java.io.InputStream;


/**
 * Servlet what return minimum from data using fileName by header called "minimum"
 * Created by Sergey on 05.02.2016.
 */

@WebServlet("/AnalyzeServlet")
public class AnalyzeServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //Analyze Factory
            AnalyzeFunctionFactory ServletFactory = new AnalyzeFunctionFactory();
            //Create AnalyzeFunction
            String functionType = request.getParameter("functionType");
            AnalyzeFunction ServletAnalyzeFunction = ServletFactory.getFunction(functionType);
            String fileName = request.getParameter("fileName");

            Point[] Data;

            ByteArrayInputStream file = FileRepository.getRepo().getFileByID(fileName);
            String DataString = StreamToString.ConvertStream(file);


            InputStream is = new ByteArrayInputStream(DataString.getBytes());

            JsonParser jsonParser;
            jsonParser = new JsonParser(is);

            Data = jsonParser.getPointsFromPointJson();

            double minimum = Data[ServletAnalyzeFunction.Calc(Data)].GetY();
            response.setHeader("minimum", String.valueOf(minimum));

        } catch (JsonParserException ex) {
            response.setHeader("minimum", ex.getMessage());
        } catch (IllegalArgumentException e) {
            response.setHeader("minimum", e.getMessage());
        }
    }

    static public String join(String delimiter, String[] list) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String item : list) {
            if (first)
                first = false;
            else
                sb.append(delimiter);
            sb.append(item);
        }
        return sb.toString();
    }



}


