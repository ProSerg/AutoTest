package ru.home.core;

/**
 * Created by smarkin on 24.02.2016.
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Input extends HTMLElement {
    private WebElement element;
    public Input(final WebDriver driver, final SearchBy elementSearchCriteria, final String elementValue) {
        super(driver, elementSearchCriteria, elementValue);
    }

    public void SendKeys(CharSequence text) {
        SendKeys(text, true);
    }

    public void clearInput() {
        waitUntil(ExpectedConditions::elementToBeClickable).clear();
    }

    public void SendKeys(CharSequence text, final boolean clear) {
        if (clear) {
            clearInput();
        }
        element = waitUntil(ExpectedConditions::elementToBeClickable);
        try {
            element.sendKeys(text);
        } catch ( WebDriverException e ) {
            System.out.printf( "%s( %s ): %s ", this.getClass(),this.getElementValue() , "keys should be a string");
            System.out.println();
        }
    }

    public void submit() {
        element.submit();
    }
}