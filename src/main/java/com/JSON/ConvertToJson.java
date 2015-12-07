package com.JSON;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Sergey on 06.12.2015.
 */
public class ConvertToJson {
    public static void main( String[] args ) {
        ArrayJson Example = new ArrayJson();

        for (int i = 0; i < 1001; i++) {
            Example.X_array[i] = 4 - i * 0.008;
            Example.Y_array[i] = Example.X_array[i];
        }
        // конвертируем наш JAVA объект в JSON
        Gson gson = new Gson();
        String json = gson.toJson(Example);

        FileWriter writeFile = null;
        try {
            File jsonFile = new File("lineal.json");
            writeFile = new FileWriter(jsonFile);
            writeFile.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writeFile != null) {
                try {
                    writeFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
