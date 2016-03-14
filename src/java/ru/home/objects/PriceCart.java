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

    final static private String LOCATOR = Locators.get("Price.Cart");
    final static private String BOX_LOCATOR= Locators.get("Price.Cart.Box");
    final static private String BOX_TITTLES_LOCATOR= Locators.get("Price.Cart.Tittles");
    final static private String BOX_ITEMS_LOCATOR= Locators.get("Price.Cart.Box.Items");
    final static private String BOX_TOTALCOAST = Locators.get("Price.Cart.Coast"); //FIXME найти более короткий xpath

    final static public String CART_FULL = "cart-full";
    final static public String CART_EMPTY = "cart-empty";

    List<WebElement> BoxElements;
    List<WebElement> Tittles;
    HTMLElement CartBox;
    WebElement TotalCoast;

    public PriceCart (WebDriver driver) {
        super(driver, SearchBy.CSS_SELECTOR,LOCATOR);
        CartBox = new HTMLElement(getDriver(),SearchBy.CSS_SELECTOR,LOCATOR);
    }

    public void findCartElements () {
        //BoxElements = getElement().findElements(By.xpath(BOX_ITEMS_LOCATOR));
        Tittles = CartBox.getElement().findElements(By.xpath(BOX_TITTLES_LOCATOR));
        TotalCoast = CartBox.getElement().findElement(By.xpath(BOX_TOTALCOAST));
    }



    public int getTittlesInfo() {
        int size = Tittles.size();
        System.out.println();
        System.out.println("Boxs Info size:" + size);
        for (WebElement e:
                Tittles) {
            System.out.println("####################");
            //System.out.println(e);
            System.out.println("Text: " + e.getText());
            System.out.println("isDisplayed: " + e.isDisplayed());
            System.out.println("Group: " + e.getAttribute( "data-change-group" ) );
        }
        return size;
    }


    public boolean isActualTittle(String group ) {
        return  group.equals( getActualTittle().getAttribute("data-change-group" ) ) ;
    }

    public boolean isHiddenTittle(String group ) {
        return  group.equals( getHiddenTittle().getAttribute("data-change-group" ) ) ;
    }

    public WebElement getActualTittle() {
        for (WebElement e:
                Tittles) {
            if (e.isDisplayed()) {
                return e;
            }
        }
        return null;
    }

    public WebElement getHiddenTittle() {
        for (WebElement e:
                Tittles) {
            if (! e.isDisplayed() ) {
                return e;
            }
        }
        return null;
    }

    public String getTotalCoast() {
        return TotalCoast.getText();
    }


}
