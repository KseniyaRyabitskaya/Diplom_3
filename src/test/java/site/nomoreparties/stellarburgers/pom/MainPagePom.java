package site.nomoreparties.stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPagePom {
    private final WebDriver driver;

    private final By titleCreateBurger = By.xpath(".//h1[text()='Соберите бургер']");
    private final By buttonEnterToAccount = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By noSelectedButtonBuns = By.xpath(".//div[not(contains(@class, 'tab_tab_type_current__2BEPc'))]//span[text()='Булки']");
    private final By noSelectedButtonSauces = By.xpath(".//div[not(contains(@class, 'tab_tab_type_current__2BEPc'))]//span[text()='Соусы']");
    private final By noSelectedButtonFilling = By.xpath(".//div[not(contains(@class, 'tab_tab_type_current__2BEPc'))]//span[text()='Начинки']");
    private final By selectedButtonBuns = By.xpath(".//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span[text()='Булки']");
    private final By selectedButtonSauces = By.xpath(".//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span[text()='Соусы']");
    private final By selectedButtonFilling = By.xpath(".//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span[text()='Начинки']");

    private final By selectedSection = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span");

    public MainPagePom(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание загрузки надписи Соберите бургер на главной странице")
    public void waitForLoadPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3L)).until(
                driver -> (
                        driver.findElement(titleCreateBurger) != null
                                && driver.findElement(titleCreateBurger).isDisplayed()
                )
        );
    }

    @Step("Проверка отображения надписи Соберите бургер на главной странице")
    public boolean titleCreateBurgerIsVisible() {
        return driver.findElement(titleCreateBurger).isDisplayed();
    }

    @Step("Получить название выбранной категории")
    public String getSelectedSection() {
        return driver.findElement(selectedSection).getText();
    }

    @Step("Клик по кнопке Булки на главной странице")
    public void clickOnButtonBuns() {
        driver.findElement(noSelectedButtonBuns).click();
    }

    @Step("Клик по кнопке Соусы на главной странице")
    public void clickOnButtonSauces() {
        driver.findElement(noSelectedButtonSauces).click();
    }

    @Step("Клик по кнопке Начинки на главной странице")
    public void clickOnButtonFilling() {
        driver.findElement(noSelectedButtonFilling).click();
    }

    @Step("Клик по кнопке Войти в аккаунт на главной странице")
    public void clickOnButtonEnterToAccount() {
        driver.findElement(buttonEnterToAccount).click();
    }

    @Step("Ожидание видимости кнопки selectedButtonBuns")
    public void isVisibleSelectedButtonBuns() {
        new WebDriverWait(driver, Duration.ofSeconds(5L)).until(
                driver -> (
                        driver.findElement(selectedButtonBuns) != null
                                && driver.findElement(selectedButtonBuns).isDisplayed()
                )
        );
    }

    @Step("Ожидание видимости кнопки selectedButtonSauces")
    public void isVisibleSelectedButtonSauces() {
        new WebDriverWait(driver, Duration.ofSeconds(5L)).until(
                driver -> (
                        driver.findElement(selectedButtonSauces) != null
                                && driver.findElement(selectedButtonSauces).isDisplayed()
                )
        );
    }

    @Step("Ожидание видимости кнопки selectedButtonFilling")
    public void isVisibleSelectedButtonFilling() {
        new WebDriverWait(driver, Duration.ofSeconds(5L)).until(
                driver -> (
                        driver.findElement(selectedButtonFilling) != null
                                && driver.findElement(selectedButtonFilling).isDisplayed()
                )
        );
    }

    @Step("Ожидание видимости кнопки noSelectedButtonBuns")
    public void isVisibleNoSelectedButtonBuns() {
        new WebDriverWait(driver, Duration.ofSeconds(5L)).until(
                driver -> (
                        driver.findElement(noSelectedButtonBuns) != null
                                && driver.findElement(noSelectedButtonBuns).isDisplayed()
                )
        );
    }

    @Step("Ожидание видимости кнопки noSelectedButtonSauces")
    public void isVisibleNoSelectedButtonSauces() {
        new WebDriverWait(driver, Duration.ofSeconds(5L)).until(
                driver -> (
                        driver.findElement(noSelectedButtonSauces) != null
                                && driver.findElement(noSelectedButtonSauces).isDisplayed()
                )
        );
    }

    @Step("Ожидание видимости кнопки noSelectedButtonFilling")
    public void isVisibleNoSelectedButtonFilling() {
        new WebDriverWait(driver, Duration.ofSeconds(5L)).until(
                driver -> (
                        driver.findElement(noSelectedButtonFilling) != null
                                && driver.findElement(noSelectedButtonFilling).isDisplayed()
                )
        );
    }
}
