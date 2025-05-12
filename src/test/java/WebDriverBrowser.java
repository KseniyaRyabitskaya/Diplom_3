import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverBrowser {

    public static WebDriver getWebDriver(TypeBrowsers browser) {
        switch (browser) {
            case CHROME:
                return new ChromeDriver();
            case YANDEX:
                System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\yandexdriver.exe");
                return new ChromeDriver();
            default:
                throw new RuntimeException("Incorrect TypeBrowsers");
        }
    }
}
