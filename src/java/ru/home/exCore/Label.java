package ru.home.exCore;

/**
 * Created by smarkin on 24.02.2016.
 */


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Label extends HTMLElement {

    public Label(final WebDriver driver, final SearchBy elementSearchCriteria, final String elementValue) {
        super(driver, elementSearchCriteria, elementValue);
    }

    public String getText() {
        return waitUntil(ExpectedConditions::visibilityOfElementLocated).getText();
    }
}