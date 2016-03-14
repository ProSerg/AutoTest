package ru.home.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.home.common.Locators;
import ru.home.core.HTMLElement;

import java.util.List;

/**
 * Created by smarkin on 14.03.2016.
 */
public class PriceCart extends HTMLElement {

    final static private String LOCATOR= Locators.get("Price.Cart");
    final static private String BOX_LOCATOR= Locators.get("Price.Cart.Box");
    final static private String BOX_TITTLES_LOCATOR= Locators.get("Price.Cart.Tittles");
    final static private String BOX_ITEMS_LOCATOR= Locators.get("Price.Cart.Box.Items");
    List<WebElement> BoxElements;
    List<WebElement> Tittles;
    HTMLElement CartBox;

    public PriceCart (WebDriver driver) {
        super(driver, SearchBy.CSS_SELECTOR,LOCATOR);
        CartBox = new HTMLElement(getDriver(),SearchBy.CSS_SELECTOR,LOCATOR);
    }

    public void findCartElements () {
       // BoxElements = CartBox.getElement().findElements(By.xpath(BOX_ITEMS_LOCATOR));
        Tittles = CartBox.getElement().findElements(By.xpath(BOX_TITTLES_LOCATOR));
    }

    public int getTittlesInfo() {
        // List<WebElement> els = FormBox.getElement().findElements(By.xpath("div[@data-qa]"));
        int size = Tittles.size();
        System.out.println("Boxs Info size:" + size);
        for (WebElement e:
                Tittles) {
            System.out.println(e);
            System.out.println("isDisplayed: " + e.isDisplayed());
            System.out.println("####################");
        }
        return size;
    }

    public String getActualTittle() {
        for (WebElement e:
                Tittles) {
            if (e.isDisplayed()) {
                return e.getText();
            }
        }
        return null;
    }


}
