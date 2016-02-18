package ru.home;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by smarkin on 17.02.2016.
 */
public class BaseElement {
    public static final Integer DEFAULT_TIMEOUT = 10;
    public WebDriver driver;
    public By locator;
    public WebElement element;
    Wait<WebDriver> wait;

    public BaseElement(WebDriver driver) {
        this.driver = driver;
        this.locator = null;
        this.element = null;
    }

    //FIXME Заменить String на By
    public BaseElement(WebDriver driver, By locator) {
        this.driver = driver;
        this.locator = locator;
        this.element = null;
    }

    public WebElement getElement() {
        this.element = WaitForElement(locator,DEFAULT_TIMEOUT);
        return  this.element;
    }

    public WebElement getElement(By by) {
        this.element = WaitForElement(by,DEFAULT_TIMEOUT);
        return  this.element;
    }

    public WebElement getElement(By by, Integer timeout) {
        this.element = WaitForElement(by,timeout >= 0 ? timeout : 0);
        return  this.element;
    }

    public WebElement getElement(String locator, Integer timeout) {
        this.element = WaitForElement(By.cssSelector(locator),timeout >= 0 ? timeout : 0);
        return  this.element;
    }

    public WebElement getElement(String locator) {
        this.element = WaitForElement(By.cssSelector(locator),DEFAULT_TIMEOUT);
        return  this.element;
    }

    public String getText() {
        return element.getText();
    }

    static public void Sleep(Integer times) {
        try {
            TimeUnit.SECONDS.sleep(times);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebElement WaitForElement(By by, Integer timeout) {
        wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeout, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));

    }
}
