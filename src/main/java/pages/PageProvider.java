package pages;

import org.openqa.selenium.WebDriver;

public class PageProvider {
    private WebDriver driver;

    public PageProvider(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage getHomePage() {
        return new HomePage(driver);
    }

    public RegistrationPage getRegistrationPage() {
        return new RegistrationPage(driver);
    }
}
