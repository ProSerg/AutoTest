package ru.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by smarkin on 17.02.2016.
 */
public class Link extends BaseElement {

    public Link (WebDriver driver, By locator ) {
        super(driver,locator);
    }

    Page Click ( ) {
        this.element=getElement(locator);
        this.element.click();
        //TODO возможно здесь стоит поставить ожидание.
        return new Page(this.driver,this.driver.getTitle());
    }
}