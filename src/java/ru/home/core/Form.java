package ru.home.core;

import org.openqa.selenium.WebDriver;

/**
 * Created by smarkin on 17.02.2016.
 */
abstract public class Form extends BaseElement {
    Button submitButton;
    Form( WebDriver driver  ) {
        super(driver);
    }

    abstract Page Submit() ;

    public abstract void fill();

    abstract Button getSubmitButton();

    abstract Page getPageAfterSubmit();
}
