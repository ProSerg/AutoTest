package ru.home.forms;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.home.common.Locators;
import ru.home.core.Button;
import ru.home.core.HTMLElement;
import ru.home.core.Page;
import ru.home.core.Title;
import ru.home.items.ItemCart;
import ru.home.items.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smarkin on 15.03.2016.
 */
public class CartContainer {
    private List<ItemCart> cart;
    private By specialBox;
    private By accessBox;
    private By countableBox;
    private WebDriver driver;
    List<WebElement> listElements;

    Title TotalCoast;
    WebDriverWait wait ;
    boolean refreshed;

    Button buttonPayment;
    private Page Payment;
    final private String LOCATOR_PAYMENT = Locators.get("Price.Cart.Payment");
    final private String itemsLi = "li[@class=\"price-cart__item\"]";
    final static private String BOX_TOTALCOAST = Locators.get("Price.Cart.Coast");


    final private int DEFAULT_TIMEOUT = 3;
    final private String LOCATE_SPECIALOFFER = Locators.get("Price.Cart.SpecialOffer");
    final private String LOCATE_RESUMEACCESS = Locators.get("Price.Cart.ResumeAcces");
    final private String LOCATE_COUNTABLE = Locators.get("Price.Cart.CounTable");

    public CartContainer(WebDriver driver) {
        cart= new ArrayList<>();
        this.driver = driver;
        specialBox = By.cssSelector( LOCATE_SPECIALOFFER );
        accessBox = By.cssSelector( LOCATE_RESUMEACCESS );
        countableBox = By.cssSelector( LOCATE_COUNTABLE );
        wait = (WebDriverWait) new WebDriverWait(getDriver(), DEFAULT_TIMEOUT)
                .ignoring(StaleElementReferenceException.class);
        refreshed = false;
        TotalCoast = new Title(driver, HTMLElement.SearchBy.CSS_SELECTOR, BOX_TOTALCOAST);

        Payment = new Page(driver, "Покупка услуг", Locators.getValue("Payment.Url"));
        buttonPayment = new Button(getDriver(), HTMLElement.SearchBy.CSS_SELECTOR, LOCATOR_PAYMENT);
    }

    public void putItem(ItemCart item) {
        cart.add(item);
    }

    public String calcPrice() {
        int sum = 0;
        for (ItemCart item:
                cart) {
            sum += Integer.parseInt(item.getPrice());
        }
        return String.format("%d",sum);
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public boolean refresh (By by) throws InterruptedException {
        refreshed = waitForElementPresent(by,DEFAULT_TIMEOUT);
        if (refreshed == false) {
            //Thread.sleep(2000); FIXME
            //refreshed = waitForElementPresent(by,DEFAULT_TIMEOUT);
            //if (refreshed == false)
                return refreshed;
        }

        listElements = driver.findElement( by ).findElements(By.xpath(itemsLi) );
        //
        for (WebElement el : listElements) {
            putItem(new ItemCart(driver, el ));
        }
        return refreshed;
    }

    public void refresh () throws InterruptedException {
        if (! cart.isEmpty() )
            cart.clear();
        refreshed = refresh ( specialBox );
        refreshed = refresh ( accessBox );
        refreshed = refresh ( countableBox );

        return ;
    }

    public ItemCart getItem (String title) {
        if(cart.isEmpty())
            return null;
        for (ItemCart item:
                cart ) {
            if ( item.getTitle().equals(title) ) {
                return item;
            }
        }
        return null;
    }

    public ItemCart getItem (int index) {
        if(cart.isEmpty())
            return null;
        return cart.get(index);
    }

    public String getPrice (String title) {
        if(cart.isEmpty())
            return null;
        for (ItemCart item:
                cart ) {
            if ( item.getTitle().equals(title) ) {
                return item.getPrice();
            }
        }
        return null;
    }

    public String getPrice (int index) {
        if(cart.isEmpty())
            return null;
        return cart.get(index).getPrice();
    }

    public List<ItemCart> getContainer () {
        return cart;
    }

    public int size () {
        return cart.size();
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }

    public boolean isRefreshed() {
        return refreshed;
    }


    public boolean contains ( String title ) {
        for (ItemCart item:
                cart ) {
            if ( item.getTitle().equals(title) ) {

                return true;
            }
        }
        return false;
    }

    public boolean contains ( item it ) {
        for (ItemCart item: cart )
            if ( item.equals(it) ) return true;
        return false;
    }

    public ItemCart removeItem (String title) {
        ItemCart item = getItem (title);
        if(item != null) {
            item.remove();
            return item;
        }
        return null;
    }

    public ItemCart removeItem (int index) {
        ItemCart item = getItem (index);
        if(item != null) {
            item.remove();
            return item;
        }
        return null;
    }

    public boolean waitForElementPresent(By by, int timeout) {
        boolean flag;
        try {
            flag = wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver webDriver) {
                    WebElement element = webDriver.findElement(by);
                    return element != null && element.isDisplayed();
                }
            });
            return true;
        }catch (TimeoutException e) {
            return false;
        }
    }

    public String  getTotalCost() {
        String str = TotalCoast.getText();
        if(str == null)
            return String.format("0");
        return str.replace(" ","");
    }

    public Page clickPayment() {
        buttonPayment.click();
        return Payment;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        int index = 0;
        if(size() == 0) {
            return null;
        }
        for (ItemCart item:
                cart ) {
            if (index > 0) {
                buffer.append(String.format(","));
            }
            buffer.append(String.format("[%s]",item));
            index++;
        }
        return buffer.toString();
    }
}
