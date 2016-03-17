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
public class Assesments extends HTMLElement {
    final static private String LOCATOR= Locators.get("Price.Assessments");
    Button button;

    public Assesments(WebDriver driver) {
        super(driver, SearchBy.CSS_SELECTOR, LOCATOR);
    }

    public void goTo () {
        waitUntil(ExpectedConditions::elementToBeClickable).click();
    }

    public String getTitle () throws InterruptedException {
        return getElement().findElements(By.tagName("span")).get(0).getText();
    }

    public void addToCart() {
        button.click();
    }
}
