package ru.home.test;

import junit.framework.TestCase;
import org.jboss.netty.handler.codec.embedder.EncoderEmbedder;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.home.exCore.*;;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class ChromeDriverTest extends Assert {
    final static String chromdriver = "E:\\workspace\\libexec\\chromedriver.exe"; //FIXME
    private static ChromeDriverService service;
    public static WebDriver driver;
    static File chrome;

    Button Enter;
    Input  lineSearch;

    public ChromeDriverTest () {}

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
        System.out.println("BeforeClass SetUp");
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
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
        driver.get("http://socialkey.ru/");
        Enter = new Button(driver,HTMLElement.SearchBy.CSS_SELECTOR,"body > header > div > div.menu.pull-right > a.btn.btn-success.log-in");
        System.out.println("Bool:"+Enter.isEnabled());
        Enter.click();
    }

}