package ru.home.core;

/**
 * Created by smarkin on 24.02.2016.
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Button extends HTMLElement {

    public Button(final WebDriver driver, final SearchBy elementSearchCriteria,String elementValue) {
        super(driver, elementSearchCriteria, elementValue);
    }

    public void click() {
        waitUntil(ExpectedConditions::elementToBeClickable).click();
    }
}