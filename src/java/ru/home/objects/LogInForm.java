package ru.home.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.home.common.Locators;
import ru.home.exCore.Button;
import ru.home.exCore.Form;
import ru.home.exCore.Input;
import ru.home.exCore.Label;

import java.util.Optional;

/**
 * Created by smarkin on 12.03.2016.
 */
public class LogInForm extends Form {
    private final String USERNAME_SELECTOR = Locators.get("LoginPage.loginInput");
    private final String PASSWORD_SELECTOR = Locators.get("LoginPage.PassInput");
    private final String ERROR_SELECTOR = Locators.get("LoginPage.Error");
    private final String BUTTON_SELECTOR = Locators.get("LoginPage.Button");

    Input InputUsername;
    Input InputPassword;
    Label logError;
    Button button;

    public LogInForm(WebDriver driver, SearchBy elementSearchCriteria,  String elementValue) {
        super(driver,elementSearchCriteria,elementValue);
        logError = new Label(driver,elementSearchCriteria,ERROR_SELECTOR);
        InputUsername = new Input(driver,elementSearchCriteria,USERNAME_SELECTOR);
        InputPassword = new Input(driver,elementSearchCriteria,PASSWORD_SELECTOR);
        button = new Button(driver,elementSearchCriteria,BUTTON_SELECTOR);
    }

    public void Submit() {
        button.click();
    }

    public String getLog() {
        if (! logError.isRefreshed(logError.getText()) )
            System.out.printf("%s(%s): do not refreshed", this.getClass(),logError.getElementValue());
        return logError.getText();
    }

    //TODO добавить проверку длины
    public void fill(String ... args) {
        if (args.length > 1) {
            InputUsername.SendKeys(args[0]);
            InputPassword.SendKeys(args[1]);
        }
    }

}
