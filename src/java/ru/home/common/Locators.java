package ru.home.common;

import org.openqa.selenium.By;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by smarkin on 12.03.2016.
 */
public class Locators  {
    private static final Properties locators;
    private static final String resource = "/locators.properties";

    private enum LocatorType  {
        id, name, css, xpath, tag, text, partText;
    }

    static {
      locators = new Properties();
        InputStream is = Locators.class.getResourceAsStream(resource);
        try {
            locators.load(new InputStreamReader(is,"CP1251"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getValue(String Name) {
        return locators.getProperty(Name);
    }

    public static String get(String locatorName) {
        String propertyValue;
        propertyValue = locators.getProperty(locatorName);
        return getLocator(propertyValue);
    }

    public static String  get(String locatorName, String parameter) {
        String propertyValue = locators.getProperty(locatorName);
        return getLocator(String.format(propertyValue, parameter));
    }

    private static String getLocator(String locator) {
        String[] locatorItems = locator.split("=", 2);
        LocatorType locatorType = LocatorType.valueOf(locatorItems[0]);

        switch (locatorType) {

            case id: {
                return locatorItems[1];
            }

            case name: {
                return locatorItems[1];
            }

            case css: {
                return locatorItems[1];
            }

            case xpath: {
                return locatorItems[1];
            }

            case tag: {
                return locatorItems[1];
            }

            case text: {
                return locatorItems[1];
            }

            case partText: {
                return locatorItems[1];
            }

            default: {
                throw new IllegalArgumentException("No such locator type: " + locatorItems[0]);
            }
        }
    }
}
