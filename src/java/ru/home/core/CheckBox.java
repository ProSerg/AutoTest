package ru.home.core;

/**
 * Created by smarkin on 24.02.2016.
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckBox extends HTMLElement {

    public CheckBox(final WebDriver driver, final SearchBy elementSearchCriteria, final String elementValue) {
        super(driver, elementSearchCriteria, elementValue);
    }

    public void check() {
        if (!isSelected()) {
            waitUntil(ExpectedConditions::elementToBeClickable).click();
        }
    }

    public void unCheck() {
        if (isSelected()) {
            waitUntil(ExpectedConditions::elementToBeClickable).click();
        }
    }

    public boolean isSelected() {
        return waitUntil(ExpectedConditions::visibilityOfElementLocated).isSelected();
    }
}
