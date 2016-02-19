package ru.home;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ChromeDriverTest extends Assert {
    final static String chromdriver = "C:\\Users\\markin\\IdeaProjects\\AutoTest\\exec\\chromedriver.exe"; //FIXME
    private static ChromeDriverService service;
    public static WebDriver driver;
    static File chrome;

    static Page MainPage;
    static Button buttonLogIn;
    static BaseElement enterActive;
    static LogInForm logInForm;
    public ChromeDriverTest () {}

    @BeforeClass
    public static void AndStartSecreatervice() throws IOException {
        chrome= new File(chromdriver);
        System.out.println("File:" + chrome);
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(chrome)
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @BeforeClass
    public static void setUp(){
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());

        WebMaker webMaker = new WebMaker(driver);
        MainPage = webMaker.getPage("http://socialkey.ru");
        buttonLogIn = webMaker.getButton("body > header > div > div.menu.pull-right > a.btn.btn-success.log-in");
        enterActive = webMaker.getElement("#enter");
        logInForm = new LogInForm(driver, By.cssSelector("#do-login"));
    }


    public static void simpleTest() {


        driver.get("http://internetka.in.ua");
        WebElement enterActive = driver.findElement(By.cssSelector("#enter") );
        assertEquals("Вход", enterActive.findElement(By.tagName("h2")).getText() );
        List<WebElement> list = enterActive.findElements(By.cssSelector("div#enter * "));
        for(WebElement el : list ) {
            System.out.println(el.getTagName() + " " + el.getTagName());
        }
    }

    @AfterClass
    public static void tearDown(){
        driver.quit();
    }


    public static void createAndStopService() {
        service.stop();
    }

    @Test
    public void testBase() throws InterruptedException {

        MainPage.Goto();
        assertEquals("Вход", buttonLogIn.Click());
        BaseElement.Sleep(1);

    }

    @Test
    public void testForm() throws InterruptedException {

        logInForm.setUsername("test");
        logInForm.setPassword("1q2w3e4r");
        logInForm.fill();
        logInForm.Submit();
        assertEquals("Неправильный логин или пароль.", logInForm.getLogError());
        BaseElement.Sleep(1);

    }

}