package base;

import common.Driver;
import common.Log;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.PageProvider;
import utils.ScreenshotUtils;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    WebDriver driver;
    protected PageProvider pageProvider;

    @BeforeMethod
    public void setUp() {
        driver = Driver.getDriver();
        pageProvider = new PageProvider(driver);
        Log.info("WebDriver started (" + driver.getClass().getSimpleName() + ")");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            Log.error("Test failed: " + result.getName());
            ScreenshotUtils.takeScreenshot();
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            Log.info("Test passed: " + result.getName());
        }
        Driver.quitDriver();
        Log.info("WebDriver closed");
    }
}
