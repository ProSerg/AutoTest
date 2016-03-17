package ru.home.items;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by smarkin on 16.03.2016.
 */
public class ItemCart extends item {

    final private String LOCATOR_TITLE="label/span/span";
    final private String LOCATOR_PRICE="label/span[@class=\"price-cart__actual-cost\"]/span";
    final private String LOCATOR_BUTTON="span/small";

    public ItemCart(WebDriver driver, WebElement elementRoot) {
        super(driver,elementRoot);
        wait = (WebDriverWait) new WebDriverWait(this.driver, DEFAULT_TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
    }

    public WebElement remove () {
        WebElement element = click();
        if(element == null)
            return null;
        WaitRemove(element);
        return element;
    }

    public boolean WaitRemove (WebElement element) {
        try {
            return wait.until(ExpectedConditions.stalenessOf(element));
        }catch (TimeoutException e) {
            return false;
        }
    }

    @Override
    public String getTitle() {
        return waitUntil(By.xpath(LOCATOR_TITLE)).getText();
    }

    @Override
    public String getPrice() {
        return waitUntil(By.xpath(LOCATOR_PRICE)).getText().replace(" ","");
    }

    @Override
    public boolean isEnabled() {
        return elementRoot.isEnabled();
    }

    @Override
    public boolean isClickable() {
        return waitUntil(By.xpath(LOCATOR_BUTTON)) != null ;
    }

    @Override
    public WebElement click() {
        WebElement e = waitUntil(By.xpath(LOCATOR_BUTTON));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(e)).click();
        }catch (TimeoutException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return e;
    }

    @Override
    public WebElement MultiClick(int count) {
        WebElement e = waitUntil(By.xpath(LOCATOR_BUTTON));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(e)).click();
            while ( ( count-1 ) > 0 ) {
                e.click();
                count--;
            }
        }catch (TimeoutException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return e;
    }

    //TODO переписать как lambda
    @Override
    public WebElement waitUntil (By by) {
        try {
            return wait.until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver webDriver) {
                    WebElement element = elementRoot.findElement(by);
                    if ( element != null && element.isDisplayed() )
                        return element;
                    return null;
                }
            });
        }catch (TimeoutException e) {
            return null;
        }
    }
}
