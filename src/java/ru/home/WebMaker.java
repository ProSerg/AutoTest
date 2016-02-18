package ru.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by smarkin on 17.02.2016.
 */
public class WebMaker {
    private WebDriver driver;

    WebMaker( WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    Button getButton ( String locator) {
        return new Button(this.driver, By.cssSelector(locator));
    }

    Page getPage (String address) {
        return new Page(this.driver,address);
    }

    Link getLink (String locator) {
        return new Link(this.driver, By.cssSelector(locator));
    }

    BaseElement getElement (String locator) {
        return new BaseElement(this.driver, By.cssSelector(locator));
    }
}
