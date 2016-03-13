package ru.home.common;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by smarkin on 12.03.2016.
 */
public class ConfigValue {
    private static Properties properties;
    private static String resource = "/config.properties";
    private static InputStream is;

    static {
        properties = new Properties();
        is = Locators.class.getResourceAsStream(resource);
        try {
            properties.load(is);
        } catch (Exception e) {
            System.out.println("ConfigValue : " + e.getMessage());
        }
    }

    public static String getResource() {
        return resource;
    }

    public static String getValue(String name) {
        return properties.getProperty(name);
    }


}
