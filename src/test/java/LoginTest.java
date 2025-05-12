import net.datafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.pom.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class LoginTest {

    private WebDriver driver;
    private final String url = "https://stellarburgers.nomoreparties.site/";
    private User user;

    private final WayOfEntrance wayOfEntrance;

    public LoginTest(WayOfEntrance wayOfEntrance) {
        this.wayOfEntrance = wayOfEntrance;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {
                        WayOfEntrance.SIGN_IN_ACCOUNT_ON_MAIN_PAGE
                },
                {
                        WayOfEntrance.SIGN_IN_ACCOUNT_ON_HEADER
                },
                {
                        WayOfEntrance.SIGN_IN_ACCOUNT_ON_REGISTRATION_PAGE
                },
                {
                        WayOfEntrance.SIGN_IN_ACCOUNT_ON_RECOVERY_PASSWORD_PAGE
                }
        };
    }

    @Before
    public void setUp() {
        Faker faker = new Faker();
        user = new User(faker.internet().emailAddress(), faker.internet().password(6, 20), faker.name().firstName());
        UserApi.createUser(user);
        driver = WebDriverBrowser.getWebDriver(TypeBrowsers.CHROME);
        driver.get(url);
    }

    @Test
    public void LoginByButtonSignInDifferentPlacesTest() {
        MainPagePom mainPagePom = new MainPagePom(driver);
        LoginPagePOM loginPagePOM = new LoginPagePOM(driver);
        HeaderPOM headerPOM = new HeaderPOM(driver);
        PersonalAccountPOM personalAccountPOM = new PersonalAccountPOM(driver);
        RegistrationPagePOM registrationPagePOM = new RegistrationPagePOM(driver);
        RecoveryPasswordPagePOM recoveryPasswordPagePOM = new RecoveryPasswordPagePOM(driver);

        mainPagePom.waitForLoadPage();

        switch (wayOfEntrance) {
            case SIGN_IN_ACCOUNT_ON_MAIN_PAGE:
                mainPagePom.clickOnButtonEnterToAccount();
            case SIGN_IN_ACCOUNT_ON_HEADER:
                headerPOM.clickOnButtonPersonalAccount();
            case SIGN_IN_ACCOUNT_ON_REGISTRATION_PAGE:
                headerPOM.clickOnButtonPersonalAccount();
                loginPagePOM.waitForLoadPage();
                loginPagePOM.clickOnLinkRegistration();
                registrationPagePOM.waitForLoadPage();
                registrationPagePOM.clickOnLinkSignIn();
            case SIGN_IN_ACCOUNT_ON_RECOVERY_PASSWORD_PAGE:
                headerPOM.clickOnButtonPersonalAccount();
                loginPagePOM.waitForLoadPage();
                loginPagePOM.clickOnLinkPasswordRecovery();
                recoveryPasswordPagePOM.waitForLoadPage();
                recoveryPasswordPagePOM.clickOnLinkSignIn();
        }

        loginPagePOM.waitForLoadPage();
        loginPagePOM.fillLogInFields(user.getEmail(), user.getPassword());
        loginPagePOM.clickOnLogInButton();
        headerPOM.waitForLoadHeader();
        headerPOM.clickOnButtonPersonalAccount();
        personalAccountPOM.waitForLoadPage();
        String actual = personalAccountPOM.getTextFromFieldEmail();
        assertThat(user.getEmail(), equalTo(actual));
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
