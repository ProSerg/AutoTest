package ru.home.objects;

import org.openqa.selenium.WebDriver;
import ru.home.common.ConfigValue;
import ru.home.core.Page;
import ru.home.test.PriceTest;

/**
 * Created by smarkin on 13.03.2016.
 */
public class PricePage extends Page {
    final static String SITE = "site.price";
    public PricePage (WebDriver driver) {
        super(driver,"Price", ConfigValue.getValue(SITE));
    }
}
