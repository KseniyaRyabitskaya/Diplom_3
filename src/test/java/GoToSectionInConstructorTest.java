import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.pom.MainPagePom;

import static org.junit.Assert.*;

public class GoToSectionInConstructorTest {

    private WebDriver driver;
    private final String url = "https://stellarburgers.nomoreparties.site/";

    @Before
    public void setUp() {
        driver = WebDriverBrowser.getWebDriver(TypeBrowsers.CHROME);
        driver.get(url);
    }

    @Test
    public void goToBunSectionTest() {
        MainPagePom mainPagePom = new MainPagePom(driver);
        mainPagePom.waitForLoadPage();
        mainPagePom.scrollToFilling();
        mainPagePom.waitScrollingToFilling();
        mainPagePom.clickOnButtonBuns();
        assertEquals("Булки", mainPagePom.getSelectedSection());
    }

    @Test
    public void goToSauceSectionTest() {
        MainPagePom mainPagePom = new MainPagePom(driver);
        mainPagePom.waitForLoadPage();
        mainPagePom.clickOnButtonSauces();
        assertEquals("Соусы", mainPagePom.getSelectedSection());
    }

    @Test
    public void goToFillingSectionTest() {
        MainPagePom mainPagePom = new MainPagePom(driver);
        mainPagePom.waitForLoadPage();
        mainPagePom.clickOnButtonFilling();
        assertEquals("Начинки", mainPagePom.getSelectedSection());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
