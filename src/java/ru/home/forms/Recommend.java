package ru.home.forms;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.home.common.Locators;
import ru.home.core.HTMLElement;
import ru.home.items.ItemRecommend;

import java.util.List;

/**
 * Created by smarkin on 16.03.2016.
 */
public class Recommend extends HTMLElement {

    public WebDriverWait wait;
    public WebDriver driver;

    public WebElement rootSevenDay;
    public WebElement rootOneStdPlus;

    final public String SevenDay = "Неделя доступа к резюме в регионе";
    final public String OneStdPlus = "Вакансия Стандарт+";

    final private String SevenDayTitle= Locators.get("Recommended.Form.Box.Title.Buy.SevenDay");
    final private String OneStdPlusTitle= Locators.get("Recommended.Form.Box.Title.Buy.OneStdPlus");
    final static private String ROOT_LOCATOR=Locators.get("Recommended");
    final private String FORM_LOCATOR = Locators.get("Recommended.Form");
    final private String FORM_BOX_LOCATOR = Locators.get("Recommended.Form.Box");

    final private int DEFAULT_TIMEOUT = 5;

    ItemRecommend sevenDay;
    ItemRecommend oneStdPlus;



    boolean init;



    public Recommend (WebDriver driver) {
        super(driver, HTMLElement.SearchBy.CSS_SELECTOR, ROOT_LOCATOR);
        this.driver = driver;
        wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);

    }

    public void initRecommend() {
        List<WebElement> Boxs;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(FORM_LOCATOR)));
        Boxs = getDriver().findElement(By.cssSelector(FORM_LOCATOR)).findElements(By.xpath(FORM_BOX_LOCATOR));

        rootOneStdPlus = Boxs.get(0);
        rootSevenDay = Boxs.get(1);

        sevenDay = new ItemRecommend(driver,rootSevenDay,SevenDayTitle);
        oneStdPlus = new ItemRecommend(driver,rootOneStdPlus,OneStdPlusTitle);

        init = true;

    }

    public boolean isInit(){
        return init;
    }

    public void goTo () throws InterruptedException {
        waitUntil(ExpectedConditions::elementToBeClickable).click();
        initRecommend();
    }

    public ItemRecommend getItem(String title) {
        switch (title) {
            case "Вакансия Стандарт+" :
                return oneStdPlus;
            case "Неделя доступа к резюме в регионе" :
                return sevenDay;
            default:
                throw new IllegalArgumentException( "Invalid item" );
        }
    }

    public String getTitle () {
        return getElement().findElements(By.tagName("span")).get(0).getText();
    }

    public String getItemPrice(String title) {
        switch (title) {
            case "Вакансия Стандарт+" :
                return oneStdPlus.getPrice();
            case "Неделя доступа к резюме в регионе" :
                return sevenDay.getPrice();
            default:
                throw new IllegalArgumentException( "Invalid item" );
        }
    }

    public boolean addToCard( String title ) {
        boolean res = false;
        switch (title) {
            case "Вакансия Стандарт+" :
                res = oneStdPlus.addToCart() != null;
                break;
            case "Неделя доступа к резюме в регионе" :
                res = sevenDay.addToCart() != null;
                break;
            default:
                throw new IllegalArgumentException( "Invalid item" );
        }
        return res;
    }

    public boolean isEnabled( String title ) {
        boolean res = false;
        switch (title) {
            case "Вакансия Стандарт+" :
                res = oneStdPlus.isClickable();
                break;
            case "Неделя доступа к резюме в регионе" :
                res = sevenDay.isClickable();
                break;
            default:
                throw new IllegalArgumentException( "Invalid item" );
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        if(! isInit())
            return null;
        return String.format("[%s],[%s]",oneStdPlus,sevenDay);
    }
}
