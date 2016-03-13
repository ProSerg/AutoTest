package ru.home.test;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.home.common.ConfigValue;
import ru.home.common.Locators;
import ru.home.common.TestParameters;
import ru.home.exCore.*;
import ru.home.objects.LogInForm;;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class ChromeDriverTest extends Assert {
    final static String chromdriver = ConfigValue.getValue("chromdriver");
    private static ChromeDriverService service;
    public static WebDriver driver;
    static File chrome;
    private static ConfigValue config;
    private static TestParameters parameters;
    private String inputLogin,inputPass,expectedResult;
    Button Enter;
    LogInForm loginForm;

    public  ChromeDriverTest(String name,String pass, String expected) {
        this.inputLogin = name;
        this.inputPass = pass;
        this.expectedResult = expected;
    }

    // {input, result }
   @Parameterized.Parameters
    public static Collection primeNumbers() throws IOException {
        parameters = new TestParameters("/parameters.properties");
        return parameters.getValue("Enter.testLight"); //testMain
    }



    @BeforeClass
    public static void AndStartSecreatervice() throws Exception {
        System.out.println("BeforeClass");
        chrome= new File(chromdriver);
        System.out.println("File:" + chrome);
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(chrome)
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @Before
    public void setUp() throws Exception {
        System.out.println();
        System.out.println("BeforeClass SetUp!!!");
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());

        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

        Enter = new Button(driver,HTMLElement.SearchBy.CSS_SELECTOR,Locators.get("LoginPage.loginButton"));
        loginForm = new LogInForm(driver,HTMLElement.SearchBy.CSS_SELECTOR,Locators.get("LoginPage.Form"));
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

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @AfterClass
    public static void createAndStopService() {
        service.stop();
    }


    @Test
    public void testBase() throws InterruptedException {
        driver.get( ConfigValue.getValue("site") );
        System.out.println("Bool:"+Enter.isEnabled());
        Enter.click();
        loginForm.fill(inputLogin,inputPass);
        loginForm.Submit();
        assertEquals(expectedResult, loginForm.getLog());
    }

}