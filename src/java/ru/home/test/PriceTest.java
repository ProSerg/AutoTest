package ru.home.test;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.home.common.ConfigValue;
import ru.home.common.Locators;
import ru.home.common.TestParameters;
import ru.home.objects.*;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class PriceTest extends Assert {
    final static String CHROMDRIVER = ConfigValue.getValue("chromdriver");
    private static ChromeDriverService service;
    public static WebDriver driver;
    static File chrome;
    private String inputLogin,inputPass,expectedResult;

    TabRecommend tabRecommend;
    TabAdditional tabAdditional;
    TabAssessments tabAssessments;
    TabDbAccess tabDbAccess;
    TabPublications tabPublications;
    PriceCart priceCart;

    PricePage pricePage;


    public  PriceTest(String name,String pass, String expected) {
        this.inputLogin = name;
        this.inputPass = pass;
        this.expectedResult = expected;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() throws IOException {
        return new TestParameters("/parameters.properties").getValue("Enter.testLight"); //testMain
    }



    @BeforeClass
    public static void StartSecreatervice() throws Exception {
        System.out.println("StartSecreatervice");
        chrome= new File(CHROMDRIVER);
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(chrome)
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("setUp");
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());

        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

        tabRecommend = new TabRecommend(driver);
        tabAdditional = new TabAdditional(driver);
        tabDbAccess = new TabDbAccess(driver);
        tabAssessments = new TabAssessments(driver);
        tabPublications = new TabPublications(driver);

        pricePage = new PricePage(driver);
        priceCart = new PriceCart(driver);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println();
        System.out.println("tearDown");
        driver.quit();
    }

    @AfterClass
    public static void StopService() {
        System.out.println();
        System.out.println("StopService");
        service.stop();
    }


    @Ignore @Test
    public void testBase() throws InterruptedException {
        pricePage.goTo();
        tabRecommend.goTo();
        assertEquals(Locators.getValue("Price.Recommended.Name"), tabRecommend.getTitle());
        Thread.sleep(1000);
        tabAdditional.goTo();
        assertEquals(Locators.getValue("Price.Additional.Name"),  tabAdditional.getTitle());
        Thread.sleep(1000);
        tabAssessments.goTo();
        assertEquals(Locators.getValue("Price.Assessments.Name"),   tabAssessments.getTitle());
        Thread.sleep(1000);
        tabDbAccess.goTo();
        assertEquals(Locators.getValue("Price.Dbaccess.Name"),   tabDbAccess.getTitle());
        Thread.sleep(1000);
        tabPublications.goTo();
        assertEquals(Locators.getValue("Price.Publications.Name"),   tabPublications.getTitle());
        Thread.sleep(1000);
    }

    @Test
    public void testTabRecommend() throws InterruptedException {
        pricePage.goTo();
        priceCart.findCartElements();

        tabRecommend.goTo();
        tabRecommend.findBoxElements();

        priceCart.getTittlesInfo();
        assertEquals(Locators.getValue("Price.Cart.Tittle.Empty"),   priceCart.getActualTittle().getText());
        assertEquals(true,    priceCart.isActualTittle(priceCart.CART_EMPTY) );
        assertEquals(true,    priceCart.isHiddenTittle(priceCart.CART_FULL) );

        tabRecommend.multiClickBoxButton(0, 10);
        tabRecommend.multiClickBoxButton(1, 10);
        tabRecommend.clickBoxButton(0);
        tabRecommend.clickBoxButton(1);
        System.out.println("Coast: " + priceCart.getTotalCoast());
        //FIXME иногда не успевает обновиться значение загаловка для корзины. Для
        //TODO Для этого нужно поправить HTMLElements.findElements
        Thread.sleep(1000);

        priceCart.getTittlesInfo();
        assertEquals(Locators.getValue("Price.Cart.Tittle.Full"), priceCart.getActualTittle().getText());
        assertEquals(true,    priceCart.isActualTittle(priceCart.CART_FULL) );
        assertEquals(true,    priceCart.isHiddenTittle(priceCart.CART_EMPTY) );

        Thread.sleep(3000);
    }

}