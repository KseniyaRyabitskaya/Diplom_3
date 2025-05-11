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

    @Before
    public void setUp() {
        user = new User("alexprahin@mail.ru", "Hfggg65JJhg", "Alex");
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
        registrationPagePOM.userRegistration(
                user.getEmail(),
                user.getName(),
                user.getPassword()
        );
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
        registrationPagePOM.userIncorrectRegistration(
                user.getEmail(),
                user.getName(),
                "lm455");

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
