package ru.home.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.home.common.Locators;
import ru.home.core.HTMLElement;

/**
 * Created by smarkin on 13.03.2016.
 */

// контейнер HTML объектов

public class TabPublications extends HTMLElement {
    final static private String NAME="Publications";
    final static private String LOCATOR=Locators.get("Price.Publications");

    public TabPublications(WebDriver driver) {
        super(driver, SearchBy.CSS_SELECTOR, LOCATOR);
    }

    public void goTo () {
        waitUntil(ExpectedConditions::elementToBeClickable).click();
    }

    public String getTitle () {
        return getElement().findElements(By.tagName("span")).get(0).getText();
    }
}
