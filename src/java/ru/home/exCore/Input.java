package ru.home.exCore;

/**
 * Created by smarkin on 24.02.2016.
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Input extends HTMLElement {
    private WebElement element;
    public Input(final WebDriver driver, final SearchBy elementSearchCriteria, final String elementValue) {
        super(driver, elementSearchCriteria, elementValue);
    }

    public void type(final CharSequence text) {
        type(text, true);
    }

    public void clearInput() {
        waitUntil(ExpectedConditions::elementToBeClickable).clear();
    }

    public void type(final CharSequence text, final boolean clear) {
        if (clear) {
            clearInput();
        }
        element = waitUntil(ExpectedConditions::elementToBeClickable);
        element.sendKeys(text);
    }

    public void submit() {
        element.submit();
    }
}