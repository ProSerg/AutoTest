package ru.home.test;

import junit.framework.TestCase;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.home.core.*;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by smarkin on 22.02.2016.
 */
@RunWith(Parameterized.class)
public class TestRunnerTest {
    final static String chromdriver = "E:\\workspace\\libexec\\chromedriver.exe"; //FIXME
    private static ChromeDriverService service;
    public static WebDriver driver;
    static File chrome;

    static Page MainPage;
    static Button buttonLogIn;
    static BaseElement enterActive;
    static LogInForm logInForm;

    private String inputLogin;
    private String inputPass;
    private String expectedResult;

    public  TestRunnerTest(String name,String pass, String expected) {
        this.inputLogin = name;
        this.inputPass = pass;
        this.expectedResult = expected;
    }

    @BeforeClass
    public static void createAndStartService() throws Exception {
        System.out.println("## createAndStartService");
        chrome= new File(chromdriver);
        System.out.println("File:" + chrome);
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(chrome)
                .usingAnyFreePort()
                .build();
        service.start();
    }

    // {input, result }
    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][] {
                { "abc", "111", "Неправильный логин или пароль." },
                { "bafdsfea", "11", "Неправильный логин или пароль." },
                { "123#@42$c", "111111111111", "Неправильный логин или пароль." },
                { "x\234\fdf", "111", "Неправильный логин или пароль." },
                { "1111", "{ printf(\"!!!\") }", "Неправильный логин или пароль." }
        });
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("## SetUp");
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());

        WebMaker webMaker = new WebMaker(driver);
        MainPage = webMaker.getPage("http://socialkey.ru");
        buttonLogIn = webMaker.getButton("body > header > div > div.menu.pull-right > a.btn.btn-success.log-in");
        enterActive = webMaker.getElement("#enter");
        logInForm = new LogInForm(driver, By.cssSelector("#do-login"));
    }

    @AfterClass
    public static void StopService () {
        System.out.println("## StopService");
        service.stop();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("## tearDown");
        driver.quit();
    }

    @Test
    public void testForm() throws InterruptedException {
        System.out.println("## testForm");
        MainPage.Goto();
        buttonLogIn.Click();
        logInForm.setUsername(inputLogin);
        logInForm.setPassword(inputPass);
        logInForm.fill();
        logInForm.Submit();
       // BaseElement.Sleep(1);
        assertEquals(expectedResult, logInForm.getLogError());
       // BaseElement.Sleep(1);
    }

}