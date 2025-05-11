import net.datafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.pom.HeaderPOM;
import site.nomoreparties.stellarburgers.pom.LoginPagePOM;
import site.nomoreparties.stellarburgers.pom.RegistrationPagePOM;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class RegistrationTest {
    private WebDriver driver;
    private final String url = "https://stellarburgers.nomoreparties.site/";
    private User user;
    Faker faker;

    @Before
    public void setUp() {
        faker = new Faker();
        user = new User(faker.internet().emailAddress(), faker.internet().password(6, 20), faker.name().firstName());
        driver = WebDriverBrowser.getWebDriver(TypeBrowsers.CHROME);
        driver.get(url);
    }

    @Test
    public void registrationIsSuccessfulWithCorrectPasswordTest() {
        HeaderPOM header = new HeaderPOM(driver);
        LoginPagePOM loginPage = new LoginPagePOM(driver);
        RegistrationPagePOM registrationPagePOM = new RegistrationPagePOM(driver);

        header.waitForLoadHeader();
        header.clickOnButtonPersonalAccount();
        loginPage.waitForLoadPage();
        loginPage.clickOnLinkRegistration();
        registrationPagePOM.waitForLoadPage();
        registrationPagePOM.fillRegistrationFields(
                user.getEmail(),
                user.getName(),
                user.getPassword()
        );
        registrationPagePOM.clickOnButtonRegistration();
        loginPage.waitForLoadPage();
        assertTrue(loginPage.getVisibilityTitle());
    }

    @Test
    public void registrationErrorWithPasswordLessThanSixSymbolsTest() {
        HeaderPOM header = new HeaderPOM(driver);
        LoginPagePOM loginPage = new LoginPagePOM(driver);
        RegistrationPagePOM registrationPagePOM = new RegistrationPagePOM(driver);

        header.waitForLoadHeader();
        header.clickOnButtonPersonalAccount();
        loginPage.waitForLoadPage();
        loginPage.clickOnLinkRegistration();
        registrationPagePOM.waitForLoadPage();
        registrationPagePOM.fillRegistrationFields(
                user.getEmail(),
                user.getName(),
                faker.internet().password(1, 5)
        );
        registrationPagePOM.clickOnButtonRegistration();
        boolean actual = registrationPagePOM.isVisibleError();
        assertThat(true, equalTo(actual));
    }

    @After
    public void tearDown() {
        driver.quit();
        String accessToken = UserApi.loginUser(user)
                .then()
                .extract()
                .body()
                .path("accessToken");

        if (accessToken != null) {
            UserApi.deleteUser(accessToken)
                    .then()
                    .assertThat()
                    .statusCode(202)
                    .and()
                    .body("success", equalTo(true));
        }
    }
}
