package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ConfigReader;

import java.time.Duration;

public class Driver {
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    private Driver() {}

    public static WebDriver getDriver() {
        if (driverThread.get() == null) {
            String browser = System.getProperty("browser", ConfigReader.get("browser")).toLowerCase();
            boolean headless = ConfigReader.getBoolean("headless");
            int wait = ConfigReader.getInt("implicitWait");

            Log.info("Browser: " + browser + " | Headless: " + headless);

            WebDriver driver;

            switch (browser) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions ffOptions = new FirefoxOptions();
                    if (headless) ffOptions.addArguments("--headless");
                    ffOptions.addArguments("--no-sandbox");
                    ffOptions.addArguments("--disable-dev-shm-usage");
                    driver = new FirefoxDriver(ffOptions);
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;

                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();

                    // ✅ Headless режим, якщо увімкнено
                    if (headless) {
                        chromeOptions.addArguments("--headless=new");
                        chromeOptions.addArguments("--window-size=1920,1080");
                    } else {
                        chromeOptions.addArguments("--start-maximized");
                    }

                    // ✅ Обов’язкові прапорці для CI / Linux
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--remote-allow-origins=*");

                    // ✅ Унікальний профіль користувача, щоб уникнути "directory in use"
                    chromeOptions.addArguments("--user-data-dir=/tmp/chrome-" + System.currentTimeMillis());

                    driver = new ChromeDriver(chromeOptions);
                    break;
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(wait));
            driverThread.set(driver);
        }
        return driverThread.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
            driverThread.remove();
        }
    }
}
