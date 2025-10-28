package pages;

import common.Log;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class RegistrationPage extends ParentPage {

    @FindBy(css = "input[id='firstname']")
    private WebElement firstNameInput;

    @FindBy(css = "input[id='lastname']")
    private WebElement lastNameInput;

    @FindBy(css = "input[id='userName']")
    private WebElement userNameInput;

    @FindBy(css = "input[id='password']")
    private WebElement userPasswordInput;

    @FindBy(css = "button[id='register']")
    private WebElement registerButton;

    @FindBy(css = ".recaptcha-checkbox-border")
    private WebElement captchaCheckbox;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open page")
    public RegistrationPage openPage() {
        try{
            driver.get("https://demoqa.com/register");
            Log.info("Registration page is opened");
        } catch (Exception e) {
            Log.error("Can't open Registration page: " + e.getMessage());
            Assert.fail("Can't open Registration page");
        }
        return this;
    }

    @Step("Enter user data and click 'Register' button")
    public void registerUser() {
        try{
            Assert.assertTrue(firstNameInput.isDisplayed());
            firstNameInput.clear();
            firstNameInput.sendKeys("Yuliia");
            Assert.assertTrue(lastNameInput.isDisplayed());
            lastNameInput.clear();
            lastNameInput.sendKeys("Tester");
            Assert.assertTrue(userNameInput.isDisplayed());
            userNameInput.clear();
            userNameInput.sendKeys("yuliiatest");
            Assert.assertTrue(userPasswordInput.isDisplayed());
            userPasswordInput.clear();
            userPasswordInput.sendKeys("!Qwerty12345");
            Assert.assertTrue(captchaCheckbox.isDisplayed());
            captchaCheckbox.click();
            Assert.assertTrue(captchaCheckbox.isSelected());
            Assert.assertTrue(registerButton.isDisplayed());
            Assert.assertTrue(registerButton.isEnabled());
            registerButton.click();
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }
}
