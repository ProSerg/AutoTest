package ru.home.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.home.common.Locators;
import ru.home.core.HTMLElement;
import ru.home.items.ItemContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smarkin on 17.03.2016.
 */
public class Publications extends HTMLElement {
    final static private String LOCATOR= Locators.get("Price.Publications");
    final static private String BOX_LOCATOR = Locators.get("Price.Publications.Box");

    final static public String standard = "Стандарт";
    final static public String stdPlus = "Стандарт +";
    final static public String premium= "Премиум";
    final static public String anonymous = "Анонимная";

    List<WebElement> list;
    List<ItemContent> listContent;

    public Publications(WebDriver driver) {
        super(driver, SearchBy.CSS_SELECTOR, LOCATOR);
        listContent = new ArrayList<>();
    }

    public void goTo () {
        waitUntil(ExpectedConditions::elementToBeClickable).click();
    }

    public String getTitle () {
        return getElement().findElements(By.tagName("span")).get(0).getText();
    }

    public  void initContent() throws InterruptedException {
        list = getDriver().findElement(By.cssSelector(BOX_LOCATOR)).findElements(By.xpath("./div"));
        for (WebElement element: list) {
            listContent.add(new ItemContent(getDriver(),element));
        }
    }

    private ItemContent getItem(String title) {
        for (ItemContent item: listContent) {
            if(item.getTitle().equals(title))
                return item;
        }
        return null;
    }

    public String getTitleContent(int index) {
        return listContent.get(index).getTitle();
    }

    public String getPriceContent(int index) {
        return listContent.get(index).getPrice();
    }

    public String getPriceContent(String  title) {
        return getItem(title).getPrice();
    }

    public String getCountContent(int index) {
        return listContent.get(index).getCount();
    }

    public void setCountContent(int index,String keys) {
        listContent.get(index).SendKeys(keys);
    }

    public void setCountContent(String title,String keys) {
        getItem(title).SendKeys(keys);
    }

    public void addOrRefresh(int index) {
        listContent.get(index).addOrRefresh();
    }

    public void addOrRefresh(String title) {
        getItem(title).addOrRefresh();
    }


}
