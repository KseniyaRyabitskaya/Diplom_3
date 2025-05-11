package site.nomoreparties.stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPagePOM {
    private final WebDriver driver;

    private final By titleLogIn = By.xpath(".//h2[text()='Вход']");
    private final By linkRegistration = By.xpath(".//a[text()='Зарегистрироваться']");
    private final By linkPasswordRecovery = By.xpath(".//a[text()='Восстановить пароль']");
    private final By fieldEmail = By.xpath(".//label[text()='Email']/parent::*/input");
    private final By fieldPassword = By.xpath(".//label[text()='Пароль']/parent::*/input");
    private final By buttonLogIn = By.xpath(".//button[text()='Войти']");

    public LoginPagePOM(WebDriver driver) {
        this.driver = driver;
    }

    public void userLogin(String email, String password) {
        waitForLoadPage();
        setTextFieldEmail(email);
        setTextFieldPassword(password);
        clickOnLogInButton();
    }

    @Step("Ожидание загрузки страницы входа")
    public void waitForLoadPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3L)).until(
                driver -> (
                        driver.findElement(titleLogIn) != null
                                && driver.findElement(titleLogIn).isDisplayed()
                )
        );
    }

    @Step("Отображение надписи Вход на странице входа")
    public boolean getVisibilityTitle() {
        return driver.findElement(titleLogIn).isDisplayed();
    }

    @Step("Кликнуть по ссылке Восстановление пароля на странице входа")
    public void clickOnLinkPasswordRecovery() {
        driver.findElement(linkPasswordRecovery).click();
    }

    @Step("Кликнуть по кнопке Зарегистрироваться на странице входа")
    public void clickOnLinkRegistration() {
        driver.findElement(linkRegistration).click();
    }

    @Step("Кликнуть по кнопке Войти на странице входа")
    public void clickOnLogInButton() {
        driver.findElement(buttonLogIn).click();
    }

    @Step("Заполнить поле Email на странице входа")
    public void setTextFieldEmail(String email) {
        driver.findElement(fieldEmail).sendKeys(email);
    }

    @Step("Заполнить поле Пароль на странице входа")
    public void setTextFieldPassword(String password) {
        driver.findElement(fieldPassword).sendKeys(password);
    }
}
