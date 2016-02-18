package ru.home;

import org.openqa.selenium.WebDriver;

/**
 * Created by smarkin on 17.02.2016.
 */
public class Page extends BaseElement {
    public String title;
    public Page (WebDriver driver, String title ) {
        super(driver,null);
        this.title=title;
    }

    String Goto () {
        driver.get(title);
        return driver.getTitle();
    }
}
