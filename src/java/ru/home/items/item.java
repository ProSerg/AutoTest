package ru.home.items;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by smarkin on 16.03.2016.
 */
abstract  public class item {

    final public int DEFAULT_TIMEOUT=5;

    public WebDriverWait wait;
    public WebDriver driver;
    public WebElement elementRoot;

    abstract public String getTitle();
    abstract public String getPrice();

    abstract public boolean isEnabled();
    abstract public boolean isClickable();
    abstract public WebElement waitUntil(By by);
    abstract public WebElement click();
    abstract public WebElement MultiClick(int count);

    item(WebDriver driver, WebElement elementRoot) {
        this.driver = driver;
        this.elementRoot = elementRoot;
    }

    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return String.format( "%s : %s", getTitle(), getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        item that = (item) o;

        if (!getTitle().equals(that.getTitle())) return false;
        if (!getPrice().equals(that.getPrice())) return false;

        return true;
    }
}
