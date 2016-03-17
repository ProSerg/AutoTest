package ru.home.objects;

import org.openqa.selenium.WebDriver;
import ru.home.common.ConfigValue;
import ru.home.core.Page;

/**
 * Created by markin on 17.03.2016.
 */

public class EmployerPage extends Page {
    final static String SITE = "site.employer";
    public EmployerPage (WebDriver driver) {
        super(driver,"Price", ConfigValue.getValue(SITE));
    }
}
