package ru.home.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.Locator;
import ru.home.common.Locators;
import ru.home.core.Button;
import ru.home.core.HTMLElement;
import ru.home.core.Page;
import ru.home.core.Title;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smarkin on 14.03.2016.
 */
public class PriceCart extends HTMLElement {
    final static private String LOCATOR = Locators.get("Price.Cart");
    final static private String BOX_LOCATOR = Locators.get("Price.Cart.Box");
    final static private String BOX_TITTLES_LOCATOR = Locators.get("Price.Cart.Tittles");
    final static private String BOX_ITEMS_LOCATOR = Locators.get("Price.Cart.Box.Items");
    final static private String BOX_TOTALCOAST = Locators.get("Price.Cart.Coast"); //FIXME найти более короткий xpath
    final static private String BOX_PAYMENT = Locators.get("Price.Cart.Payment");

    final static public String TITLE_FULL_LOCATOR = Locators.get("Price.Cart.Title.Full");
    final static public String TITLE_EMPTY_LOCATOR = Locators.get("Price.Cart.Title.Empty");

    final static public String CART_FULL = "cart-full";
    final static public String CART_EMPTY = "cart-empty";
    ItemContainer container;

    List<WebElement> BoxElements;
    Title FullTitle;
    Title EmptyTitle;
    Button buttonPayment;
    HTMLElement CartBox;
    Title TotalCoast;

    ItemPrice itemPrice;
    String locateBox = "body > div.HH-MainContent > div.g-row.m-row_content > div > div.b-price2 > div.price-content > div.g-col4 > div > div > div:nth-child(3) > div.price-cart__contents > ol.price-cart__items.HH-PriceCart-Items_specialOffer";

    Page Payment;

    public PriceCart(WebDriver driver) {
        super(driver, SearchBy.CSS_SELECTOR, LOCATOR);
        CartBox = new HTMLElement(getDriver(), SearchBy.CSS_SELECTOR, LOCATOR);
        buttonPayment = new Button(getDriver(), SearchBy.CSS_SELECTOR, BOX_PAYMENT);

        Payment = new Page(driver, "Покупка услуг", Locators.getValue("Payment.Url"));

        FullTitle = new Title(driver, SearchBy.CSS_SELECTOR, TITLE_FULL_LOCATOR);
        EmptyTitle = new Title(driver, SearchBy.CSS_SELECTOR, TITLE_EMPTY_LOCATOR);
        TotalCoast = new Title(driver, SearchBy.CSS_SELECTOR, BOX_TOTALCOAST);
        container = new ItemContainer(getDriver(),By.cssSelector(locateBox));
    }

    public ItemContainer findCartElements() throws InterruptedException {
        container.refresh();
        return container;
    }

    public ItemContainer getContainer() {
        return container;
    }

    public boolean isDisplayedFull() {
        return FullTitle.isDisplayed();
    }

    public boolean isDisplayedEmpty() {
        return EmptyTitle.isDisplayed();
    }

    public String getActualTittle() {
        if (isDisplayedFull())
            return FullTitle.getText();
        if (isDisplayedEmpty())
            return EmptyTitle.getText();
        return null;
    }

    public boolean isCoast() {
        String total = getTotalCoast().replace(" ","");
        return total.equals(calcPrice());
    }

    public String calcPrice() {
        int sum = 0;
        for (ItemPrice item:
                container.getContainer()) {
            sum += Integer.parseInt(item.getPrice().replace(" ",""));
        }
        return String.format("%d",sum);
    }

    public String getTotalCoast() {
        if( TotalCoast.isDisplayed() )
            return TotalCoast.getTextUntil();
        return null;
    }

    public Page clickPayment() {
        buttonPayment.click();
        return Payment;
    }


    public void waitForElementPresent(final By by, int timeout) {
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class);

        boolean flag = wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                WebElement element = webDriver.findElement(by);
                return element != null && element.isDisplayed();
            }
        });
    }
}
