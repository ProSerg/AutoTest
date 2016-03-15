package ru.home.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    private WebElement elementTitle;
    private WebElement elementPrice;
    private By locatorRoot;

    final private String LOCATOR_TITLE="label/span/span";
    final private String LOCATOR_PRICE="label/span[@class=\"price-cart__actual-cost\"]/span";
    final private String LOCATOR_BUTTON="span/small";

    ItemPrice (WebDriver driver,WebElement elementRoot, String Name) {
        this.driver = driver;
        this.elementRoot = elementRoot;
        this.Name = Name;
    }

    ItemPrice (WebDriver driver,By locatorRoot, String Name) {
        this.driver = driver;
        this.locatorRoot = locatorRoot;
        this.Name = Name;
        refresh();
    }

    public void refresh() {
        elementRoot = driver.findElement(locatorRoot);
    }

    public String getTitle() {
        try {
            if(elementTitle == null)
                elementTitle = elementRoot.findElement(By.xpath(LOCATOR_TITLE));
            return elementTitle.getText();
        } catch (StaleElementReferenceException e) {
            elementTitle = elementRoot.findElement(By.xpath(LOCATOR_TITLE));
            return elementTitle.getText();
        }
    }

    public String getPrice() {
        try {
            if(elementPrice == null)
                elementPrice = elementRoot.findElement(By.xpath(LOCATOR_PRICE));
            return elementPrice.getText();
        } catch (StaleElementReferenceException e) {
            elementPrice = elementRoot.findElement(By.xpath(LOCATOR_TITLE));
            return  elementPrice.getText();
        }
    }

    public void remove() {
        elementRoot.findElement(By.xpath(LOCATOR_BUTTON)).click();
    }

}
