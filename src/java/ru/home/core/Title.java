package ru.home.core;

/**
 * Created by smarkin on 24.02.2016.
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Title extends HTMLElement {

    public Title(final WebDriver driver, final SearchBy elementSearchCriteria, final String elementValue) {
        super(driver, elementSearchCriteria, elementValue);
    }

    public String getTextUntil() {
        return waitUntil(ExpectedConditions::visibilityOfElementLocated).getText();
    }
}