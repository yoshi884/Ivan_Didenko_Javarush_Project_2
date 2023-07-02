package com.javarush.island.didenko;

import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings extends Properties {
    @Getter
    private Properties properties;


    public Settings() {
        File appPropertiesFile = new File("DB");
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(appPropertiesFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Properties fromFile = new Properties();

        try {
            fromFile.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.properties = fromFile;

    }

    public int fetchIntProperties(String property) {
        return Integer.parseInt(properties.getProperty(property));
    }


    public double fetchDoubleProperties(String property) {
        return Double.parseDouble(properties.getProperty(property));
    }
}
