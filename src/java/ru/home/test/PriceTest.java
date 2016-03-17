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
import ru.home.forms.*;
import ru.home.objects.*;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class PriceTest extends Assert {
    final static String CHROMDRIVER = ConfigValue.getValue("chromdriver");
    private static ChromeDriverService service;
    public static WebDriver driver;
    static File chrome;
    private String inputLogin,inputPass,expectedResult;


    PricePage pricePage;

    Publications tabPublications;
    Recommend tabRecommend;
    Access tabAccess;
    Additional tabAdditional;
    Assesments tabAssessments;
    CartContainer cart;


    public  PriceTest(String name,String pass, String expected) {
        this.inputLogin = name;
        this.inputPass = pass;
        this.expectedResult = expected;
    }

    public static int randInt(int min, int max) {


        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
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


        tabPublications = new Publications(driver);
        tabRecommend = new Recommend(driver);
        tabAccess = new Access(driver);
        tabAdditional = new Additional(driver);
        tabAssessments = new Assesments(driver);
        cart = new CartContainer(driver);
        pricePage = new PricePage(driver);

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

    @Test
    public void testPayment() throws InterruptedException {
        pricePage.goTo();
        System.out.print("* Проверка перехода на страницу оплаты без регистрации  = > ");
        tabRecommend.goTo();
        assertEquals(Locators.getValue("Price.Recommended.Name"), tabRecommend.getTitle());
        tabRecommend.addToCard(tabRecommend.OneStdPlus);
        cart.clickPayment();
        System.out.println("Ok");
    }

    @Ignore @Test
    public void testBase() throws InterruptedException {
        pricePage.goTo();
        System.out.print("* Проверка перехода по вкладкам  = > ");
        tabRecommend.goTo();
        assertEquals(Locators.getValue("Price.Recommended.Name"), tabRecommend.getTitle());
        tabAdditional.goTo();
        assertEquals(Locators.getValue("Price.Additional.Name"),  tabAdditional.getTitle());
        tabAssessments.goTo();
        assertEquals(Locators.getValue("Price.Assessments.Name"),   tabAssessments.getTitle());
        tabAccess.goTo();
        assertEquals(Locators.getValue("Price.Access.Name"), tabAccess.getTitle());
        tabPublications.goTo();
        assertEquals(Locators.getValue("Price.Publications.Name"),   tabPublications.getTitle());
        System.out.println("Ok");
    }

    @Ignore @Test
    public void testPublications() throws InterruptedException {
        pricePage.goTo();
        tabPublications.goTo();
        System.out.println(tabPublications.getTitle());

        tabPublications.initContent();
        System.out.print("* Проверка добавления контента   = > ");

        tabPublications.addOrRefresh(Publications.standard);
        tabPublications.addOrRefresh(Publications.stdPlus);
        tabPublications.addOrRefresh(Publications.premium);
        tabPublications.addOrRefresh(Publications.anonymous);
        cart.refresh();
        assertEquals(4,cart.size());
        System.out.println("OK");

        System.out.print("* Проверка удаление контента и контроль суммы   = > ");
        while (! cart.isEmpty() ) {
            cart.removeItem(0);
            cart.refresh();
            if(!cart.isEmpty())
                assertEquals(cart.getTotalCost(), cart.calcPrice()); //FIXME Expected : "" , Actual   :0
        }
        assertTrue(cart.isEmpty());

        System.out.println("OK");

        System.out.print("* Проверка изменения контента и контроль суммы   = > ");
        String coast;

        tabPublications.addOrRefresh(Publications.standard);
        coast = tabPublications.getPriceContent(Publications.standard);
        tabPublications.setCountContent(Publications.standard,String.format("%d",randInt(-100, 100)) );
        tabPublications.addOrRefresh(Publications.standard);
        assertNotEquals(coast,tabPublications.getPriceContent(Publications.standard));

        tabPublications.addOrRefresh(Publications.stdPlus);
        coast = tabPublications.getPriceContent(Publications.stdPlus);
        tabPublications.setCountContent(Publications.stdPlus,String.format("%d",randInt(-100, 100)) );
        tabPublications.addOrRefresh(Publications.stdPlus);
        assertNotEquals(coast,tabPublications.getPriceContent(Publications.stdPlus));

        tabPublications.addOrRefresh(Publications.premium);
        coast = tabPublications.getPriceContent(Publications.premium);
        tabPublications.setCountContent(Publications.premium,String.format("%d",randInt(-100, 100)) );
        tabPublications.addOrRefresh(Publications.premium);
        assertNotEquals(coast,tabPublications.getPriceContent(Publications.premium));

        tabPublications.addOrRefresh(Publications.anonymous);
        coast = tabPublications.getPriceContent(Publications.anonymous);
        tabPublications.setCountContent(Publications.anonymous,String.format("%d",randInt(-100, 100)) );
        tabPublications.addOrRefresh(Publications.anonymous);
        assertNotEquals(coast,tabPublications.getPriceContent(Publications.anonymous));

        System.out.println("OK");


    }

    @Ignore @Test
    public void testAccess() throws InterruptedException {
        pricePage.goTo();
        tabAccess.goTo();
        System.out.println(tabAccess.getTitle());

        System.out.print("* Проверка добавления покупки   = > ");
        tabAccess.addToCart();
        cart.refresh();
        assertEquals(1,cart.size());
        System.out.println("OK");

        System.out.print("* Проверка суммы покупки  = > ");
        assertEquals(cart.getTotalCost(), cart.calcPrice());
        System.out.println("OK");

        System.out.print("* Проверка удаление покупки  = > ");
        cart.removeItem(0);
        cart.refresh();
        assertEquals(0,cart.size());
        System.out.println("OK");

    }

    @Ignore @Test
    public void testRecommend() throws InterruptedException {

        pricePage.goTo();
        tabRecommend.goTo();
        System.out.println(tabRecommend);

        System.out.print("* Проверка в разделе Recommend функционал покупки \"Вакансия Стандарт+\"  = > ");
        //System.out.println();
        assertTrue(tabRecommend.isEnabled(tabRecommend.OneStdPlus));
        tabRecommend.addToCard( tabRecommend.OneStdPlus );
        assertFalse(tabRecommend.isEnabled(tabRecommend.OneStdPlus));

        cart.refresh();
        assertEquals(1,cart.size());
        assertEquals(tabRecommend.getItemPrice(tabRecommend.OneStdPlus),cart.getPrice(0));
        System.out.println("OK");


        System.out.print("* Проверка в разделе Recommend функционал покупки \"Неделя доступа к резюме в регионе\"  = > ");
        assertTrue(tabRecommend.isEnabled(tabRecommend.SevenDay));
        tabRecommend.addToCard( tabRecommend.SevenDay );
        assertFalse(tabRecommend.isEnabled(tabRecommend.SevenDay));

        cart.refresh();
        assertEquals(2,cart.size());
        assertEquals(tabRecommend.getItemPrice(tabRecommend.SevenDay),cart.getPrice(1));

        System.out.println("OK");

        System.out.print("* Проверка суммы покупки  = > ");
        assertEquals(cart.getTotalCost(), cart.calcPrice());
        System.out.println("OK");

        System.out.print("* Проверка удаление покупки \"Неделя доступа к резюме в регионе\"  = > ");
        cart.removeItem(1);
        cart.refresh();
        assertEquals(1,cart.size());
        assertFalse(cart.contains("Неделя доступа к базе резюме"));
        System.out.println("OK");

        System.out.print("* Проверка удаление покупки \"Вакансия Стандарт+\"  = > ");
        cart.removeItem(0);
        cart.refresh();
        assertEquals(0,cart.size());
        assertFalse(cart.contains("Вакансия Стандарт+"));
        System.out.println("OK");

        Thread.sleep(3000);
    }

    /*
    Иногда тест не проходит.
     */

    @Ignore @Test
    public void testStrange() throws InterruptedException {
        pricePage.goTo();
        tabRecommend.goTo();
        System.out.println(tabRecommend);

        tabRecommend.getItem(tabRecommend.OneStdPlus).MultiClick(10);
        tabRecommend.getItem(tabRecommend.SevenDay).MultiClick(10);
        System.out.print("* Проверка многократного клика  = > ");
        cart.refresh();
        assertEquals(2, cart.size());
        System.out.println("OK");
        Thread.sleep(3000);
    }

    @Ignore @Test
    public void testSpamAttack() throws InterruptedException {
        pricePage.goTo();
        tabAccess.goTo();
        System.out.println(tabAccess.getTitle());

        System.out.print("* Проверка добавления покупки   = > ");
        int count = randInt(0,1000);
        while(count-- > 0)
            tabAccess.addToCart();
        System.out.println("OK");
    }
}