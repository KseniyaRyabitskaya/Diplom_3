import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.pom.HeaderPOM;
import site.nomoreparties.stellarburgers.pom.LoginPagePOM;
import site.nomoreparties.stellarburgers.pom.MainPagePom;
import site.nomoreparties.stellarburgers.pom.PersonalAccountPOM;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class PassToConstructorFromPersonalAccountTest {

    private WebDriver driver;
    private final String url = "https://stellarburgers.nomoreparties.site/";
    private User user;
    boolean isClickOnConstructor;

    public PassToConstructorFromPersonalAccountTest(boolean isClickOnConstructor) {
        this.isClickOnConstructor = isClickOnConstructor;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {
                        true
                },
                {
                        false
                }
        };
    }

    @Before
    public void setUp() {
        user = new User("alexprahin@mail.ru", "Hfggg65JJhg", "Alex");
        UserApi.createUser(user);
        driver = WebDriverBrowser.getWebDriver(TypeBrowsers.CHROME);
        driver.get(url);
        MainPagePom mainPagePom = new MainPagePom(driver);
        LoginPagePOM loginPagePOM = new LoginPagePOM(driver);
        mainPagePom.waitForLoadPage();
        mainPagePom.clickOnButtonEnterToAccount();
        loginPagePOM.userLogin(user.getEmail(), user.getPassword());
    }


    @Test
    public void passToConstructorFromPersonalAccountTest() {
        HeaderPOM headerPOM = new HeaderPOM(driver);
        PersonalAccountPOM personalAccountPOM = new PersonalAccountPOM(driver);
        MainPagePom mainPagePom = new MainPagePom(driver);

        headerPOM.waitForLoadHeader();
        headerPOM.clickOnButtonPersonalAccount();
        personalAccountPOM.waitForLoadPage();
        if (isClickOnConstructor) {
            headerPOM.clickOnButtonConstructor();
        } else {
            headerPOM.clickOnLogo();
        }
        mainPagePom.waitForLoadPage();
        assertTrue(mainPagePom.titleCreateBurgerIsVisible());
    }

    @After
    public void tearDown() {
        driver.quit();
        String accessToken = UserApi.loginUser(user)
                .then()
                .extract()
                .body()
                .path("accessToken");

        UserApi.deleteUser(accessToken)
                .then()
                .assertThat()
                .statusCode(202)
                .and()
                .body("success", equalTo(true));
    }
}
