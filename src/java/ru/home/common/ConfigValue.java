package ru.home.common;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by smarkin on 12.03.2016.
 */
public class ConfigValue {
    private Properties properties;
    private String resource ;
    private InputStream is;

    public  ConfigValue (String resource) {
        properties = new Properties();
        this.setResource(resource);
    }

    public void  setResource(String resource) {
        this.resource = resource;
        is = Locators.class.getResourceAsStream(resource);
        try {
            properties.load(is);
        } catch (Exception e) {
            System.out.println("ConfigValue : " + e.getMessage());
        }
    }

    public String getResource() {
        return resource;
    }

    public String getValue(String name) {
        return properties.getProperty(name);
    }


}
