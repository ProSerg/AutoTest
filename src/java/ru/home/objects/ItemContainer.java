package ru.home.objects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smarkin on 15.03.2016.
 */
public class ItemContainer {
    private List<ItemPrice> container;
    private By locateBox;
    private WebDriver driver;
    WebElement specialOffer;
    List<WebElement> listSpecialOffer;
    WebDriverWait wait ;
    boolean refreshed;

    final private String specialOfferLi = "li[@class=\"price-cart__item\"]";
    final private int TIMEOUT=5;

    public ItemContainer(WebDriver driver, By by) {
        container= new ArrayList<>();
        this.driver = driver;
        locateBox = by;
        wait = (WebDriverWait) new WebDriverWait(getDriver(), TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
        refreshed = false;
    }

    public void putItem(ItemPrice item) {
        container.add(item);
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public boolean refresh () throws InterruptedException {
        if (! container.isEmpty() )
            container.clear();
        //SpecialOffer
        refreshed = waitForElementPresent(locateBox,TIMEOUT);
        if (refreshed == false) {
            Thread.sleep(2000);
            refreshed = waitForElementPresent(locateBox,TIMEOUT);
            if (refreshed == false)
                return refreshed;
        }
        specialOffer = driver.findElement( locateBox );
        listSpecialOffer = specialOffer.findElements(By.xpath(specialOfferLi) );
        //
        for (WebElement el : listSpecialOffer) {
            putItem(new ItemPrice(driver, el ));
        }
        return refreshed;
    }

    public ItemPrice getItem (String title) {
        if(container.isEmpty())
            return null;
        for (ItemPrice item:
                container ) {
            if ( item.getTitle().equals(title) ) {
                return item;
            }
        }
        return null;
    }

    public ItemPrice getItem (int index) {
        if(container.isEmpty())
            return null;
        return container.get(index);
    }

    public List<ItemPrice> getContainer () {
        return container;
    }

    public int size () {
        return container.size();
    }

    public boolean isEmpty() {
        return container.isEmpty();
    }

    public boolean isRefreshed() {
        return refreshed;
    }


    public boolean contains ( String title ) {
        for (ItemPrice item:
                container ) {
            if ( item.getTitle().equals(title) ) {

                return true;
            }
        }
        return false;
    }

    public ItemPrice removeItem (String title) {
        ItemPrice item = getItem (title);
        if(item != null) {
            item.remove();
            return item;
        }
        return null;
    }

    public ItemPrice removeItem (int index) {
        ItemPrice item = getItem (index);
        if(item != null) {
            item.remove();
            return item;
        }
        return null;
    }

    public boolean waitForElementPresent(final By by, int timeout) {
        boolean flag;
        try {
            flag = wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver webDriver) {
                    WebElement element = webDriver.findElement(by);
                    return element != null && element.isDisplayed();
                }
            });
            return true;
        }catch (TimeoutException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        int index = 0;
        if(size() == 0) {
            return null;
        }
        for (ItemPrice item:
                container ) {
            if (index > 0) {
                buffer.append(String.format(","));
            }
            buffer.append(String.format("[%s]",item));
            index++;
        }
        return buffer.toString();
    }
}
