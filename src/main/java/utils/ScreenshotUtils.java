package utils;

import common.Driver;
import common.Log;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {

    @Attachment(value = "Screenshot on Failure {methodName}", type = "image/png")
    public static byte[] takeScreenshot() {
        try{
            WebDriver driver = Driver.getDriver();
            if(driver == null) {
                Log.warn("WebDriver is null — cannot take screenshot");
                return new byte[0];
            }
            Log.info("Taking screenshot...");
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            Log.error("Failed to take screenshot: " + e.getMessage());
            return new byte[0];
        }
    }
}
