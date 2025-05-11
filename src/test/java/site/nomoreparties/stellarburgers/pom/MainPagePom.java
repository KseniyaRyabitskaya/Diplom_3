package site.nomoreparties.stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPagePom {
    private final WebDriver driver;

    private final By titleCreateBurger = By.xpath(".//h1[text()='Соберите бургер']");
    private final By buttonEnterToAccount = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By buttonBuns = By.xpath(".//span[text()='Булки']");
    private final By buttonSauces = By.xpath(".//span[text()='Соусы']");
    private final By buttonFilling = By.xpath(".//span[text()='Начинки']");
    private final By selectedSection = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span");
    private final By selectedButtonFilling = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]//span[text()='Начинки']");
    private final By sectionFilling = By.xpath(".//h2[text() = 'Начинки']");

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
        driver.findElement(buttonBuns).click();
    }

    @Step("Клик по кнопке Соусы на главной странице")
    public void clickOnButtonSauces() {
        driver.findElement(buttonSauces).click();
    }

    @Step("Клик по кнопке Начинки на главной странице")
    public void clickOnButtonFilling() {
        driver.findElement(buttonFilling).click();
    }

    @Step("Клик по кнопке Войти в аккаунт на главной странице")
    public void clickOnButtonEnterToAccount() {
        driver.findElement(buttonEnterToAccount).click();
    }

    @Step("Скролл до надписи Начинки на главной странице")
    public void scrollToFilling() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(sectionFilling));
    }

    @Step("Ожидание завершения скролла до надписи Начинки на главной странице")
    public void waitScrollingToFilling() {
        new WebDriverWait(driver, Duration.ofSeconds(5L)).until(
                driver -> (
                        driver.findElement(selectedButtonFilling) != null
                                && driver.findElement(selectedButtonFilling).isDisplayed()
                )
        );
    }
}
