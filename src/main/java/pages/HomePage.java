package pages;

import common.Log;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.*;

public class HomePage extends ParentPage {

    @FindBy(css = "textarea[jsname='1yZiJbe']")
    private WebElement input;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Open page")
    public void openPage() {
        try{
            driver.get("https://www.google.com/");
            Log.info("Home page is opened");
        } catch (Exception e) {
            Log.error("Can't open Home page: " + e.getMessage());
            Assert.fail("Can't open Home page");
        }
    }

    @Step("Check input is visible")
    public void isInputVisible() {
        try{
            Assert.assertTrue(input.isDisplayed());
        } catch (Exception e) {
            Log.error("Input is not displayed: " + e.getMessage());
            Assert.fail("Input is not displayed");
        }
    }
}
