package ru.home.items;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by smarkin on 17.03.2016.
 */
public class ItemContent extends item {

    final private String LOCATOR_TITLE="div/h2[@data-qa=\"cart-item__title\"]";
    final private String LOCATOR_PRICE="div/span[@data-qa=\"price__cost\"]/span";
    final private String LOCATOR_BUTTON="div/div/div[@class=\"bloko-control-group\"]/div[@class=\"bloko-control-group__minor\"]/button[@data-qa=\"cart-item__button-add\"]";
    final private String LOCATOR_INPUT="div/div/div[@class=\"bloko-control-group\"]/div[@class=\"bloko-control-group__main\"]/label/input[@data-qa=\"price__input\"]";

    public ItemContent(WebDriver driver, WebElement elementRoot ) {
        super(driver,elementRoot);
        wait = (WebDriverWait) new WebDriverWait(this.driver, DEFAULT_TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
    }

    public void SendKeys(String keys) {
        waitUntil(By.xpath(LOCATOR_INPUT)).sendKeys(" ");
        waitUntil(By.xpath(LOCATOR_INPUT)).sendKeys(keys);
    }

    public void addOrRefresh() {
        click();
    }

    public String getCount() {
        return waitUntil(By.xpath(LOCATOR_INPUT)).getText();
    }

    @Override
    public String getTitle() {
        return waitUntil(By.xpath(LOCATOR_TITLE)).getText();
    }

    @Override
    public String getPrice() {
        return waitUntil(By.xpath(LOCATOR_PRICE)).getText();
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
    public WebElement waitUntil(By by) {
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
}
