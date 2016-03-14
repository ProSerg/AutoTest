package ru.home.core;

import org.openqa.selenium.WebDriver;

/**
 * Created by smarkin on 13.03.2016.
 */

public class Page {
    private String name;
    private String url;
    private WebDriver driver;
    public Page(WebDriver driver) {
        this.driver = driver;
    }

    public Page(WebDriver driver,String url ) {
        this.name = this.url = url;
        this.driver = driver;
    }

    public Page(WebDriver driver,String name, String url) {
        this.driver = driver;
        this.name = name;
        this.url = url;
    }

    public void goTo () {
        this.driver.get(url);
    }

    public String getName () {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

}
