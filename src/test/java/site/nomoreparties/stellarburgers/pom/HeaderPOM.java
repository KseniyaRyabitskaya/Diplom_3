package site.nomoreparties.stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HeaderPOM {
    private final WebDriver driver;

    private final By buttonPersonalAccount = By.xpath(".//p[text()='Личный Кабинет']");
    private final By buttonConstructor = By.xpath(".//p[text()='Конструктор']");
    private final By headerLogo = By.xpath(".//div[@class='AppHeader_header__logo__2D0X2']");

    public HeaderPOM(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание загрузки хэдера")
    public void waitForLoadHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(3L)).until(
                driver -> (
                        driver.findElement(buttonPersonalAccount) != null
                                && driver.findElement(buttonPersonalAccount).isDisplayed()
                )
        );
    }

    @Step("Кликт по лого Stellar Burgers в хэдере")
    public void clickOnLogo() {
        driver.findElement(headerLogo).click();
    }

    @Step("Клик по кнопке Личный Кабинет в хэдере")
    public void clickOnButtonPersonalAccount() {
        driver.findElement(buttonPersonalAccount).click();
    }

    @Step("Клик по кнопке Конструктор в хэдере")
    public void clickOnButtonConstructor() {
        driver.findElement(buttonConstructor).click();
    }
}
