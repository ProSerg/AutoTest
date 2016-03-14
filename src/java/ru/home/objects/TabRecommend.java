package ru.home.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.home.common.Locators;
import ru.home.core.Button;
import ru.home.core.HTMLElement;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by smarkin on 13.03.2016.
 */

// контейнер HTML объектов

public class TabRecommend extends HTMLElement {
    final static private String LOCATOR=Locators.get("Price.Recommended");
    final static private String FORM_LOCATOR=Locators.get("Price.Recommended.Form");
    final static private String FORM_BOX_LOCATOR=Locators.get("Price.Recommended.Form.Box");
    final static private String FORM_BOX_TITTLE=Locators.get("Price.Recommended.Form.Box.Tittle");
    final static private String FORM_BOX_MONEY=Locators.get("Price.Recommended.Form.Box.Money");
    final static private String FORM_BOX_BUTTON=Locators.get("Price.Recommended.Form.Box.Button");
    HTMLElement FormBox;
    List<WebElement> BoxElements;

    public TabRecommend(WebDriver driver) {
        super(driver, HTMLElement.SearchBy.CSS_SELECTOR, LOCATOR);
        FormBox = new HTMLElement(getDriver(),HTMLElement.SearchBy.CSS_SELECTOR,FORM_LOCATOR);
    }

    public void goTo () {
        waitUntil(ExpectedConditions::elementToBeClickable).click();
    }

    public String getTitle () {
        return getElement().findElements(By.tagName("span")).get(0).getText();
    }

    public void findBoxElements () {
        BoxElements = FormBox.getElement().findElements(By.xpath(FORM_BOX_LOCATOR));
    }

    public int getBoxsInfo() {
      // List<WebElement> els = FormBox.getElement().findElements(By.xpath("div[@data-qa]"));
        int size = BoxElements.size();
        System.out.println("Boxs Info size:" + size);
        for (WebElement e:
                BoxElements) {
            System.out.println("Tiitle: " + e.findElement(By.xpath(FORM_BOX_TITTLE)).getText() );
            System.out.println("Money: " + e.findElement(By.xpath(FORM_BOX_MONEY)).getText());
            System.out.println("Button: " + e.findElement(By.xpath(FORM_BOX_BUTTON)).getText());
            System.out.println("####################");
        }
        return size;
    }

    public WebElement getBoxElements(int index)  {
        WebElement e;
        try {
            e = BoxElements.get(index);
        } catch (Exception exept ) {
            System.out.printf("getBoxElements: %s\n", exept.getMessage());
            throw exept;
        }
        return e;
    }

    public void getBoxInfo(int index) {
        WebElement e;
        try {
            e = getBoxElements(index);
        } catch (Exception expext) {
            System.out.printf("getBoxInfo: out\n");
            return;
        }
        System.out.printf("BoxInfo(%d)", index);
        System.out.println("Tiitle: " + e.findElement(By.xpath(FORM_BOX_TITTLE)).getText() );
        System.out.println("Money: " + e.findElement(By.xpath(FORM_BOX_MONEY)).getText());
        System.out.println("Button: " + e.findElement(By.xpath(FORM_BOX_BUTTON)).getText());
        System.out.println("####################");
    }

    public String  getBoxTittle(int index) {
        WebElement e;
        try {
            e = getBoxElements(index);
        } catch (Exception expext) {
            System.out.printf("getBoxTittle: out\n");
            return null;
        }
        return e.findElement(By.xpath(FORM_BOX_TITTLE)).getText();
    }

    public String getBoxMoney(int index) {
        WebElement e;
        try {
            e = getBoxElements(index);
        } catch (Exception expext) {
            System.out.printf("getBoxMoney: out\n");
            return null;
        }
        return e.findElement(By.xpath(FORM_BOX_MONEY)).getText();
    }

    public WebElement getBoxButton(int index) {
        WebElement e;
        try {
            e = getBoxElements(index);
        } catch (Exception expext) {
            System.out.printf("getBoxButton: out\n");
            return null;
        }
        return e.findElement(By.xpath(FORM_BOX_BUTTON));
    }

    public void clickBoxButton(int index) {
        WebElement e = getBoxButton(index);
        if (e != null ) {
            e.click();
            e.click();
        }else {
            System.out.println("clickBoxButton: Not found button");
        }
    }


}
