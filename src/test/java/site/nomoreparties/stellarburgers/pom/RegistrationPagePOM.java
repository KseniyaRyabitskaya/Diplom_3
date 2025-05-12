package site.nomoreparties.stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPagePOM {
    private final WebDriver driver;

    private final By fieldName = By.xpath(".//label[text()='Имя']/parent::*/input");
    private final By fieldEmail = By.xpath(".//label[text()='Email']/parent::*/input");
    private final By fieldPassword = By.xpath(".//input[@name='Пароль']");
    private final By registrationButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By titleIncorrectPassword = By.xpath(".//p[text()='Некорректный пароль']");
    private final By linkSignIn = By.xpath(".//a[text()='Войти']");

    public RegistrationPagePOM(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Заполнение полей регистрации")
    public void fillRegistrationFields(String email, String name, String password) {
        setTextFieldName(name);
        setTextFieldEmail(email);
        setTextFieldPassword(password);
    }

    @Step("Ожидание появления поля Имя на странице регистрации")
    public void waitForLoadPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3L)).until(
                driver -> (
                        driver.findElement(fieldName) != null
                                && driver.findElement(fieldName).isDisplayed()
                )
        );
    }

    @Step("Клик по кнопке Зарегистрироваться на странице регистрации")
    public void clickOnButtonRegistration() {
        driver.findElement(registrationButton).click();
    }

    @Step("Клик по ссылке Войти на странице регистрации")
    public void clickOnLinkSignIn() {
        driver.findElement(linkSignIn).click();
    }

    @Step("Заполнить поле Имя на странице регистрации")
    public void setTextFieldName(String name) {
        driver.findElement(fieldName).sendKeys(name);
    }

    @Step("Заполнить поле Email на странице регистрации")
    public void setTextFieldEmail(String email) {
        driver.findElement(fieldEmail).sendKeys(email);
    }

    @Step("Заполнить поле Пароль на странице регистрации")
    public void setTextFieldPassword(String password) {
        driver.findElement(fieldPassword).sendKeys(password);
    }

    @Step("Заполнить поле Пароль некорректными данными на странице регситрации")
    public void setIncorrectPassword(String password) {
        driver.findElement(fieldPassword).sendKeys(password);
    }

    @Step("Проверка появления ошибки Некорректный пароль на странице регистрации")
    public boolean isVisibleError() {
        return driver.findElement(titleIncorrectPassword).isDisplayed();
    }
}
