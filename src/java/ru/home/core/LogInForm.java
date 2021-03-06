package ru.home.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by smarkin on 18.02.2016.
 */
public class LogInForm extends Form {
    private static final String USERNAME_SELECTOR = "#login_username";
    private static final String PASSWORD_SELECTOR = "#login_password";
    private static final String ERROR_SELECTOR = "#login_error";
    String username;
    String password;
    Input InputUsername;
    Input InputPassword;
    BaseElement logError;
    Button Submit;

    public LogInForm(WebDriver driver, By locatorSubmit) {
        super(driver);
        Submit = new Button(driver,locatorSubmit);
        logError = new BaseElement(driver,By.cssSelector(ERROR_SELECTOR));
        InputUsername = new Input(driver,By.cssSelector(USERNAME_SELECTOR));
        InputPassword = new Input(driver,By.cssSelector(PASSWORD_SELECTOR));
    }

    public Page Submit() {
        Submit.Click();
        return new Page(this.driver,this.driver.getTitle());
    }

    public String getLogError() {
        logError.getElement();
        Sleep(1);
        // решить проблему с таймоутом.
        return logError.getText();
    }

    public void fill() {
        InputUsername.SendKeys(this.username);
        InputPassword.SendKeys(this.password);
    }

    //TODO добавить проверку длины
    void fill(String ... args) {
        if (args.length > 1) {
            InputUsername.SendKeys(args[0]);
            InputPassword.SendKeys(args[1]);
        }
    }

    Button getSubmitButton() {
        return Submit;
    }

    Page getPageAfterSubmit() {
        return new Page(this.driver,"");
    }

    public void setUsername(String username) {
        this.username=username;
    }

    public void setPassword(String password) {
        this.password=password;
    }
}
