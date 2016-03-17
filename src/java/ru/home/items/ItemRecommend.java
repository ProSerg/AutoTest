package ru.home.items;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.home.common.Locators;

/**
 * Created by smarkin on 16.03.2016.
 */
public class ItemRecommend extends item {
    final private String LOCATOR_PRICE  = Locators.get("Recommended.Form.Box.Money");
    final private String LOCATOR_BUTTON = Locators.get("Recommended.Form.Box.Button");

    private WebDriverWait wait;
    private String titleLocator ;

    public ItemRecommend(WebDriver driver, WebElement elementRoot, String titleLocator ) {
        super(driver,elementRoot);
        this.titleLocator = titleLocator;
        wait = (WebDriverWait) new WebDriverWait(getDriver(), DEFAULT_TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
    }

    public WebElement addToCart () {
        WebElement element = click();
        if(element == null)
            return null;
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(element)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return element;
    }


    @Override
    public String getTitle() {
        return waitUntil(By.xpath(titleLocator)).getText();
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
        return waitUntil(By.xpath(LOCATOR_BUTTON)).isEnabled() ;
    }

    @Override
    public WebElement click() {
        WebElement element = waitUntil(By.xpath(LOCATOR_BUTTON));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        }catch (TimeoutException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return element;
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
            System.out.println(e.getMessage());
            return null;
        }
    }

}
