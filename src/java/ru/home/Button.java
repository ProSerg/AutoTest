package ru.home;

import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
