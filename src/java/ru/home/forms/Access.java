package ru.home.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.home.common.Locators;
import ru.home.core.Button;
import ru.home.core.HTMLElement;

/**
 * Created by smarkin on 17.03.2016.
 */
public class Access extends HTMLElement {
    final static private String NAME="Access";
    final static private String LOCATOR= Locators.get("Price.Access");
    final static private String LOCATOR_BUTTON= "body > div.HH-MainContent > div.g-row.m-row_content > div > div.b-price2 > div.price-content > div.flat-tabs > div.g-col1.m-colspan3 > ul > li.flat-tabs__content.HH-PriceCart-Tab.g-expand > div > div > div.price-resume-access__summary > div.price-spoffers__button > button";
    Button button;

    public Access(WebDriver driver) {
        super(driver, SearchBy.CSS_SELECTOR, LOCATOR);
        button = new Button(driver,SearchBy.CSS_SELECTOR,LOCATOR_BUTTON);
    }

    public void goTo () {
        waitUntil(ExpectedConditions::elementToBeClickable).click();
    }

    public String getTitle () {
        return getElement().findElements(By.tagName("span")).get(0).getText();
    }

    public void addToCart() {
        button.click();
    }
}