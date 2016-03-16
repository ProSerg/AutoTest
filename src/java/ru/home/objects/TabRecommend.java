package ru.home.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.home.common.Locators;
import ru.home.core.HTMLElement;

import java.util.List;

/**
 * Created by smarkin on 13.03.2016.
 */

// контейнер HTML объектов

public class TabRecommend extends HTMLElement {
    final static private String LOCATOR=Locators.get("Price.Recommended");
    final static private String FORM_LOCATOR=Locators.get("Price.Recommended.Form");
    final static private String FORM_BOX_LOCATOR=Locators.get("Price.Recommended.Form.Box");
    final static private String FORM_BOX_TITLE=Locators.get("Price.Recommended.Form.Box.Title");
    final static private String FORM_BOX_TITLE_BUY=Locators.get("Price.Recommended.Form.Box.Title.Buy");
    final static private String FORM_BOX_TITLE_BUYR=Locators.get("Price.Recommended.Form.Box.Title.Buy.Right");
    final static private String FORM_BOX_MONEY=Locators.get("Price.Recommended.Form.Box.Money");
    final static private String FORM_BOX_BUTTON=Locators.get("Price.Recommended.Form.Box.Button");
    final static private int DEFAULT_TIMEOUT = 5 ;
    WebDriverWait wait;
    HTMLElement FormBox;
    List<WebElement> BoxElements;

    public enum IdButton {
        Left,
        Right
    }

    private int convertID (IdButton ID) {
        int id = -1;
        switch (ID) {
            case Left:
                id = 0;
                break;
            case Right:
                id =1;
                break;
        }
        return id;
    }

    public TabRecommend(WebDriver driver) {
        super(driver, HTMLElement.SearchBy.CSS_SELECTOR, LOCATOR);
        FormBox = new HTMLElement(getDriver(),HTMLElement.SearchBy.CSS_SELECTOR,FORM_LOCATOR);
        wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
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
            System.out.println("####################");
            System.out.println("Tiitle: " + e.findElement(By.xpath(FORM_BOX_TITLE_BUY)).getText() );
            System.out.println("Money: " + e.findElement(By.xpath(FORM_BOX_MONEY)).getText());
            System.out.println("Button: " + e.findElement(By.xpath(FORM_BOX_BUTTON)).getText());
        }
        return size;
    }

    public boolean isButtonEnabled(IdButton ID ) {
        return this.getBoxButton(ID).isEnabled();
    }



    private WebElement getBoxElements(int index)  {
        WebElement e;
        if(index < 0 )
            return null;
        try {
            e = BoxElements.get(index);
        } catch (Exception exept ) {
            System.out.printf("getBoxElements: %s\n", exept.getMessage());
            throw exept;
        }
        return e;
    }

    public void getBoxInfo(IdButton ID) {
        WebElement e;
        try {
            e = getBoxElements(convertID(ID));
        } catch (Exception expext) {
            System.out.printf("getBoxInfo: out\n");
            return;
        }
        System.out.printf("BoxInfo(%d)", convertID(ID));
        System.out.println("Tiitle: " + e.findElement(By.xpath(FORM_BOX_TITLE_BUY)).getText() );
        System.out.println("Money: " + e.findElement(By.xpath(FORM_BOX_MONEY)).getText());
        System.out.println("Button: " + e.findElement(By.xpath(FORM_BOX_BUTTON)).getText());
        System.out.println("####################");
    }

    public String  getBoxTittle(IdButton ID) {
        WebElement e;
        try {
            e = getBoxElements(convertID(ID));
        } catch (Exception expext) {
            System.out.printf("getBoxTittle: out\n");
            return null;
        }
        if (IdButton.Left == ID )
            return e.findElement(By.xpath(FORM_BOX_TITLE_BUY)).getText();
        return e.findElement(By.xpath(FORM_BOX_TITLE_BUYR)).getText();
    }
    public String getBoxMoney(IdButton ID) {
        WebElement e;
        try {
            e = getBoxElements(convertID(ID));
        } catch (Exception expext) {
            System.out.printf("getBoxMoney: out\n");
            return null;
        }
        return e.findElement(By.xpath(FORM_BOX_MONEY)).getText();
    }

    public WebElement getBoxButton(IdButton ID) {
        WebElement e;
        try {
            e = getBoxElements(convertID(ID));
        } catch (Exception expext) {
            System.out.printf("getBoxButton: out\n");
            return null;
        }
        return e.findElement(By.xpath(FORM_BOX_BUTTON));
    }

    public void clickBoxButton(IdButton ID) {
        WebElement e = getBoxButton(ID);
        if (e != null ) {
            e.click();
            wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(e)));
        }else {
            System.out.println("clickBoxButton: Not found button");
        }
    }

    public void multiClickBoxButton(IdButton ID, int count) {
        WebElement e = getBoxButton(ID);
        if (e != null ) {
            while( count-- > 0) {
                e.click();
            }
            wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(e)));
        } else {
            System.out.println("clickBoxButton: Not found button");
        }
    }


}
