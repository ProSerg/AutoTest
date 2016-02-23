package ru.home.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by smarkin on 17.02.2016.
 */
public class Button extends BaseElement {
    String text;
    public Button (WebDriver driver, By locator) {
        super(driver,locator);
    }

    public String Click () {
        this.element= getElement(locator);
        text = this.element.getText();
        this.element.click();
        return text;
    }
}
