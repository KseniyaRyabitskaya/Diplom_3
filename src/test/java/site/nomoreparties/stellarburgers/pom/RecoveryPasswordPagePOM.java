package site.nomoreparties.stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RecoveryPasswordPagePOM {
    private final WebDriver driver;

    private final By linkSignIn = By.xpath(".//a[text()='Войти']");

    public RecoveryPasswordPagePOM(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание появления ссылки Войти на странице Восстановление пароля")
    public void waitForLoadPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3L)).until(
                driver -> (
                        driver.findElement(linkSignIn) != null
                                && driver.findElement(linkSignIn).isDisplayed()
                )
        );
    }

    @Step("Клик по ссылке Войти на странице Восстановление пароля")
    public void clickOnLinkSignIn() {
        driver.findElement(linkSignIn).click();
    }
}
