import net.datafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.pom.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertTrue;

public class PassToPersonalAccountTest {

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
        mainPagePom.waitForLoadPage();
        mainPagePom.clickOnButtonEnterToAccount();
        loginPagePOM.waitForLoadPage();
        loginPagePOM.fillLogInFields(user.getEmail(), user.getPassword());
        loginPagePOM.clickOnLogInButton();
    }


    @Test
    public void passToPersonalAccountByClickButtonPersonalAccountTest() {
        HeaderPOM headerPOM = new HeaderPOM(driver);
        PersonalAccountPOM personalAccountPOM = new PersonalAccountPOM(driver);

        headerPOM.waitForLoadHeader();
        headerPOM.clickOnButtonPersonalAccount();
        personalAccountPOM.waitForLoadPage();
        boolean actual = personalAccountPOM.linkProfileIsVisible();
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


