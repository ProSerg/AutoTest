package ru.home.test;

import com.sun.xml.internal.bind.v2.model.core.ID;
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
        ItemContainer container;
        pricePage.goTo();
        tabRecommend.goTo();

        tabRecommend.findBoxElements();

        //Проверка титул пустой корзины
        assertEquals(Locators.getValue("Price.Cart.Tittle.Empty"),   priceCart.getActualTittle());
        assertTrue(priceCart.isDisplayedEmpty() );
        assertFalse(priceCart.isDisplayedFull() );

        container = priceCart.findCartElements();
        System.out.println(container.toString());
        System.out.println("Coast: " + priceCart.getTotalCoast() );
        //Проверка содержимой пустой корзины
        assertTrue(container.isEmpty());

        //проверка изменения статуса кнопки покупки "левой" до и после нажатия
        assertTrue(tabRecommend.isButtonEnabled(TabRecommend.IdButton.Left) );
        tabRecommend.multiClickBoxButton(TabRecommend.IdButton.Left, 1);
        assertFalse(tabRecommend.isButtonEnabled(TabRecommend.IdButton.Left) );
        //

        container.refresh();
        //Проверка содержимой не пустой корзины
        assertTrue(!container.isEmpty());

  //      System.out.println(tabRecommend.getBoxTittle(TabRecommend.IdButton.Left));
  //      System.out.println(container);

        // сверка название товара с титулом из корзины
        assertTrue(container.contains(tabRecommend.getBoxTittle(TabRecommend.IdButton.Left)));
        //TODO сверка цены
        //

        //проверка изменения статуса кнопки покупки "правой" до и после нажатия
        assertTrue(tabRecommend.isButtonEnabled(TabRecommend.IdButton.Right) );
        tabRecommend.multiClickBoxButton(TabRecommend.IdButton.Right, 1);
        assertFalse(tabRecommend.isButtonEnabled(TabRecommend.IdButton.Right) );
        //

        container.refresh();

//        System.out.println("RButton:"+ tabRecommend.getBoxButton(TabRecommend.IdButton.Right).isEnabled() );
//        System.out.println(container);

        // сверка название товара с титулом из корзины
        assertTrue(container.contains("Неделя доступа к базе резюме"));
        //TODO сверка цены
        //

        //проверка суммы
        System.out.println("Coast: " + priceCart.getTotalCoast());
        System.out.println("Calculate: " + priceCart.calcPrice());
        assertTrue(priceCart.isCoast());
        //Проверка титула не пустой корзины
        assertEquals(Locators.getValue("Price.Cart.Tittle.Full"), priceCart.getActualTittle());
        assertFalse(priceCart.isDisplayedEmpty() );
        assertTrue(priceCart.isDisplayedFull() );

        //Проверка колличество покупок в корзине
        assertEquals(2, container.size());

        //System.out.println(container.toString());

        //Удаление одной покупки в корзине
        container.removeItem("Неделя доступа к базе резюме");
        assertTrue(container.refresh());
        assertTrue(tabRecommend.getBoxButton(TabRecommend.IdButton.Right).isEnabled() );

        //Проверка колличество покупок в корзине
        assertEquals(1, container.size());
        System.out.println(container.toString());

        //Удаление второй покупки в корзине
        container.removeItem("Вакансия Стандарт+");
        container.refresh();
        assertTrue(tabRecommend.getBoxButton(TabRecommend.IdButton.Left).isEnabled() );


        assertTrue(container.isEmpty());

        //END
/*
        assertTrue(tabRecommend.isButtonEnabled(TabRecommend.IdButton.Left) );
        tabRecommend.multiClickBoxButton(TabRecommend.IdButton.Left, 1);
        assertFalse(tabRecommend.isButtonEnabled(TabRecommend.IdButton.Left) );

        container.refresh();
        assertTrue(!container.isEmpty());

        System.out.println(tabRecommend.getBoxTittle(TabRecommend.IdButton.Left));
        System.out.println(container);

        assertTrue(container.contains(tabRecommend.getBoxTittle(TabRecommend.IdButton.Left)));

        assertTrue(tabRecommend.isButtonEnabled(TabRecommend.IdButton.Right) );
        tabRecommend.multiClickBoxButton(TabRecommend.IdButton.Right, 1);
        assertFalse(tabRecommend.isButtonEnabled(TabRecommend.IdButton.Right) );


        container.refresh();

        System.out.println("RButton:"+ tabRecommend.getBoxButton(TabRecommend.IdButton.Right).isEnabled() );
        System.out.println(container);

        assertTrue(container.contains("Неделя доступа к базе резюме"));

        System.out.println("Coast: " + priceCart.getTotalCoast());

        assertEquals(Locators.getValue("Price.Cart.Tittle.Full"), priceCart.getActualTittle());
        assertFalse(priceCart.isDisplayedEmpty() );
        assertTrue(priceCart.isDisplayedFull() );

        assertEquals(2, container.size());

        System.out.println(container.toString());

        container.removeItem("Неделя доступа к базе резюме");
        assertTrue(container.refresh());
        //Thread.sleep(1000);
        assertTrue(tabRecommend.getBoxButton(TabRecommend.IdButton.Right).isEnabled() );

        assertEquals(1, container.size());
        System.out.println(container.toString());

        container.removeItem("Вакансия Стандарт+");
        container.refresh();
        //Thread.sleep(1000);
        assertTrue(tabRecommend.getBoxButton(TabRecommend.IdButton.Left).isEnabled() );

        assertEquals(0, container.size());
*/
    }

}