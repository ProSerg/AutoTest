package ru.home.core;

import org.openqa.selenium.WebDriver;

/**
 * Created by smarkin on 12.03.2016.
 */
abstract public class Form extends HTMLElement {
    Button submitButton;
    public Form(WebDriver driver, SearchBy elementSearchCriteria, String elementValue) {
        super(driver,elementSearchCriteria,elementValue);
    }

    abstract public void Submit() ;

    public abstract void fill(String ... args );

//    abstract Button getSubmitButton();

//    abstract void getPageAfterSubmit();
}