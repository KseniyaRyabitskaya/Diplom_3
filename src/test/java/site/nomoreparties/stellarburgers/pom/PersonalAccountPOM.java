package site.nomoreparties.stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalAccountPOM {
    private final WebDriver driver;

    private final By fieldEmail = By.xpath(".//label[text()='Логин']/parent::*/input");
    private final By linkProfile = By.xpath(".//a[text() = 'Профиль']");
    private final By linkLogOut = By.xpath(".//button[text() = 'Выход']");

    public PersonalAccountPOM(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание появления ссылки Профиль в Личном Кабинете")
    public void waitForLoadPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3L)).until(
                driver -> (
                        driver.findElement(linkProfile) != null
                                && driver.findElement(linkProfile).isDisplayed()
                )
        );
    }

    @Step("Проверка видимости ссылки Профиль в Личном Кабинете")
    public boolean linkProfileIsVisible() {
        return driver.findElement(linkProfile).isDisplayed();
    }

    @Step("Получить Email из поля Логин в Личном Кабинете")
    public String getTextFromFieldEmail() {
        return driver.findElement(fieldEmail).getAttribute("value");
    }

    @Step("Клик по ссылке Выход в Личном Кабинете")
    public void clickOnLinkLogOut() {
        driver.findElement(linkLogOut).click();
    }
}
