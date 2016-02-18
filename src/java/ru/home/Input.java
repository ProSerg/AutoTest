package ru.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by smarkin on 18.02.2016.
 */
public class Input extends BaseElement {

    public Input(WebDriver driver, By locator) {
        super(driver, locator);
    }

    public Integer SendKeys (String keys) {
        this.element = getElement();
        this.element.click();
        this.element.sendKeys(keys);
        return this.element.getText().length();
    }
}
