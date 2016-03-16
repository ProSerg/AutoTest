package ru.home.objects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.home.core.Button;
import ru.home.core.HTMLElement;
import ru.home.core.Title;

/**
 * Created by smarkin on 15.03.2016.
 */
public class ItemPrice {
    private Title title;
    private Button delete;
    private WebDriver driver;

    private String Name;
    private WebElement elementRoot;
    private By locatorRoot;

    final private String LOCATOR_TITLE="label/span/span";
    final private String LOCATOR_PRICE="label/span[@class=\"price-cart__actual-cost\"]/span";
    final private String LOCATOR_BUTTON="span/small";
    final private int TIMEOUT=5;

    private WebDriverWait wait;

    ItemPrice (WebDriver driver,WebElement elementRoot, String Name) {
        this.driver = driver;
        this.elementRoot = elementRoot;
        this.Name = Name;

        wait = (WebDriverWait) new WebDriverWait(this.driver, TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
    }

    ItemPrice (WebDriver driver,WebElement elementRoot) {
        this.driver = driver;
        this.elementRoot = elementRoot;
        this.Name = "item";

        wait = (WebDriverWait) new WebDriverWait(this.driver, TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
    }

    public void refresh() {
        elementRoot = driver.findElement(locatorRoot);
    }

    public String getTitle() {
        if ( WaitUntil (By.xpath(LOCATOR_TITLE)) )
            return elementRoot.findElement(By.xpath(LOCATOR_TITLE)).getText();
        return null;
    }

    public String getPrice() {
        if ( WaitUntil (By.xpath(LOCATOR_PRICE)) )
            return elementRoot.findElement(By.xpath(LOCATOR_PRICE)).getText();
        return null;
    }

    public boolean WaitUntil (By by) {
        boolean flag;
        try {
            flag = wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver webDriver) {
                    WebElement element = elementRoot.findElement(By.xpath(LOCATOR_BUTTON));
                    return element != null && element.isDisplayed();
                }
            });
            return true;
        }catch (TimeoutException e) {
            return false;
        }
    }

    public boolean WaitRemove (WebElement element) {
        boolean flag;
        try {
            return wait.until(ExpectedConditions.stalenessOf(element));
        }catch (TimeoutException e) {
            return false;
        }
    }

    public void remove() {
        WebElement element;
        if ( WaitUntil (By.xpath(LOCATOR_BUTTON)) ) {
            element = elementRoot.findElement(By.xpath(LOCATOR_BUTTON));
            element.click();
            WaitRemove(element);
        }
    }

    @Override
    public String toString() {
        return String.format( "%s : %s", getTitle(), getPrice());
    }

}
