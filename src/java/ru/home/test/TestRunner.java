package ru.home.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;

/**
 * Created by smarkin on 22.02.2016.
 */
public class TestRunner {
    public static void main (String arg[]) {
        Result result = JUnitCore.runClasses(JUnit4TestSuite.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
