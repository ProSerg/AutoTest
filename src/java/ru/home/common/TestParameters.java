package ru.home.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

/**
 * Created by smarkin on 12.03.2016.
 */
public class TestParameters {
    private Properties properties;
    private String resource ;
    private InputStream is;
    InputStreamReader reader = null;

    public TestParameters(String resource) throws IOException {
        properties = new Properties();
        this.setResource(resource);
    }

    public void  setResource(String resource) throws IOException {
        this.resource = resource;
        is = getClass().getResourceAsStream(resource);
        reader = new InputStreamReader(is,"CP1251");
        try {
            properties.load(reader);
        } catch (Exception e) {
            System.out.println("ConfigValue : " + e.getMessage());
        }
    }

    public String getResource() {
        return resource;
    }

    public Collection getValue(String name) {
        String[] a = properties.getProperty(name).split(";");
        String[][] array = new String[a.length][a.length];
        for(int i = 0;i < a.length;i++) {
            array[i] = a[i].split(",");
        }
        return  Arrays.asList(array);
    }


}
