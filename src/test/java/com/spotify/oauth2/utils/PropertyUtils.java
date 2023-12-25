package com.spotify.oauth2.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
    // This class is used to get properties file content
    public static Properties propertyLoader(String filePath) {
        Properties properties = new Properties();
        BufferedReader bufferedReader;

        try{
            bufferedReader = new BufferedReader(new FileReader(filePath));
            try{
                properties.load(bufferedReader);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load the properties file: ", e);
            }
        } catch (FileNotFoundException fileExceptionMsg){
            fileExceptionMsg.printStackTrace();
            throw new RuntimeException("Properties file is not fount at: ", fileExceptionMsg);
        }
        return properties;
    }
}
