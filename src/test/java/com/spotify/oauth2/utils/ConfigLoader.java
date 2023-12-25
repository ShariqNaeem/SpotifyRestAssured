package com.spotify.oauth2.utils;

import java.io.FileNotFoundException;
import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        this.properties = PropertyUtils.propertyLoader("src/test/resources/config.properties");
    }

    // This will create new OBJECT of this class only once.
    // Also, will not check the File path again and again.
    public static ConfigLoader getConfigLoaderInstance() {
        if (configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getPropertyValue(String propertyKey){
        String value = properties.getProperty(propertyKey);
        if ( value!= null) return value;
        else throw new RuntimeException("Property {propertyKey} is not defined in the .properties file");
    }
}
