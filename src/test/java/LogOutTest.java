import net.datafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.pom.HeaderPOM;
import site.nomoreparties.stellarburgers.pom.LoginPagePOM;
import site.nomoreparties.stellarburgers.pom.MainPagePom;
import site.nomoreparties.stellarburgers.pom.PersonalAccountPOM;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertTrue;

public class LogOutTest {

    private WebDriver driver;
    private final String url = "https://stellarburgers.nomoreparties.site/";
    private User user;

    @Before
    public void setUp() {
        Faker faker = new Faker();
        user = new User(faker.internet().emailAddress(), faker.internet().password(6, 20), faker.name().firstName());
        UserApi.createUser(user);
        driver = WebDriverBrowser.getWebDriver(TypeBrowsers.CHROME);
        driver.get(url);
        MainPagePom mainPagePom = new MainPagePom(driver);
        LoginPagePOM loginPagePOM = new LoginPagePOM(driver);
        HeaderPOM headerPOM = new HeaderPOM(driver);
        mainPagePom.waitForLoadPage();
        mainPagePom.clickOnButtonEnterToAccount();
        loginPagePOM.waitForLoadPage();
        loginPagePOM.fillLogInFields(user.getEmail(), user.getPassword());
        loginPagePOM.clickOnLogInButton();
        headerPOM.waitForLoadHeader();
        headerPOM.clickOnButtonPersonalAccount();
    }


    @Test
    public void logOutFromPersonalAccountTest() {
        PersonalAccountPOM personalAccountPOM = new PersonalAccountPOM(driver);
        HeaderPOM headerPOM = new HeaderPOM(driver);
        LoginPagePOM loginPagePOM = new LoginPagePOM(driver);

        personalAccountPOM.waitForLoadPage();
        personalAccountPOM.clickOnLinkLogOut();
        headerPOM.waitForLoadHeader();
        headerPOM.clickOnButtonPersonalAccount();
        loginPagePOM.waitForLoadPage();
        boolean actual = loginPagePOM.getVisibilityTitle();
        assertTrue(actual);
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
