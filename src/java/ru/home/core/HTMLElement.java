package ru.home.core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by smarkin on 24.02.2016.
 *
 */

/*

        driver.wait(function() {
        return driver.findElement(target).click().then(
            function(click) { return true },
            function(err) {
                if (err = StaleElementReferenceError) {
                    return false;
                } else {
                    throw err;
                }
            }
        );
    }, 10000);

 */

public class HTMLElement extends By {

    public enum SearchBy {
        ID,
        XPATH,
        LINK_TEXT,
        CLASS_NAME,
        CSS_SELECTOR,
        TAG_NAME,
        NAME,
        PARTIAL_LINK_TEXT
    }

    private By locator;
    private WebDriverWait wait;

    private String elementValue;
    private SearchBy elementSearchCriteria;
    private WebDriver driver;
    private WebElement element;

    private static final long DEFAULT_TIMEOUT = 5;


    public HTMLElement(final WebDriver driver, SearchBy elementSearchCriteria, String elementValue) {
        super();
        this.elementSearchCriteria = elementSearchCriteria;
        this.elementValue = elementValue;
        this.driver = driver;
        if (driver != null) {
            this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        }

        if (getElementSearchCriteria() != null && getElementValue() != null) {
            initElement(getElementSearchCriteria(), getElementValue());
        }
    }

    public By getLocator() {
        return locator;
    }

    public void refreshElement () {
        if(element == null )
            element = driver.findElement(locator);
    }
    public WebElement getElement () {
        refreshElement();
        return element;
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public String getElementValue() {
        return elementValue;
    }

    public SearchBy getElementSearchCriteria() {
        return elementSearchCriteria;
    }

    public boolean isEnabled() {
        return getElement().isEnabled();
    }

    public boolean isDisplayed() {
        return getElement().isDisplayed();
    }

    public String getText() {
        return getElement().getText();
    }

    public boolean isEnabledUntil() {
        return waitUntil(ExpectedConditions::visibilityOfElementLocated).isEnabled();
    }
/*
    public boolean isDisplayed() {
        return waitUntil(ExpectedConditions::).isDisplayed();
    }
*/
    public boolean isRefreshed (String text) {
        try {
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOfElementWithText(getLocator(),text)));
            return true;
        }catch (TimeoutException e) {
            return false;
        }
    }

    public Boolean isWaitUntil(Function<By, ExpectedCondition<Boolean>> condition) {
        return isWaitUntil(condition, Optional.<Long>empty());
    }

    public Boolean isWaitUntil(Function<By, ExpectedCondition<Boolean>> condition, final Optional<Long> timeout) {
        try {
            return getWait(timeout).until(condition.apply(getLocator()));
        } catch (Exception e) {
            throw new AssertionError("Unable to find element by " + getElementSearchCriteria() + " = \"" + getElementValue() + "\"", e);
        }
    }


    public WebElement waitUntil(final Function<By, ExpectedCondition<WebElement>> condition) {
        return waitUntil(condition, Optional.<Long>empty());
    }

    public WebElement waitUntil(final Function<By, ExpectedCondition<WebElement>> condition, final Optional<Long> timeout) {
        try {
            return getWait(timeout).until(condition.apply(getLocator()));
        } catch (Exception e) {
            //throw new AssertionError("Unable to find element by " + getElementSearchCriteria() + " = \"" + getElementValue() + "\"", e);
            return null;
        }
    }

    private FluentWait<WebDriver> getWait(final Optional<Long> timeout) {
        return timeout.map(sec -> wait.withTimeout(sec, TimeUnit.SECONDS))
                .orElse(wait.withTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS));
    }

    private void initElement(final SearchBy searchBy, final String searchString) {
        switch (searchBy) {
            case ID:
                locator = By.id(searchString);
                break;
            case CSS_SELECTOR:
                locator = By.cssSelector(searchString);
                break;
            case CLASS_NAME:
                locator = By.className(searchString);
                break;
            case XPATH:
                locator = By.xpath(searchString);
                break;
            case LINK_TEXT:
                locator = By.linkText(searchString);
                break;
            case TAG_NAME:
                locator = By.tagName(searchString);
                break;
            case PARTIAL_LINK_TEXT:
                locator = By.partialLinkText(searchString);
                break;
            case NAME:
                locator = By.name(searchString);
                break;
        }
    }

    //TODO выправить эту функцию, чтобы получить на выходе List<HTMLElement>
    @Override
    public List<WebElement> findElements(final SearchContext searchContext) {
        return new ArrayList<>();
    }

}