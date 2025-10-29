package pages;

import base.BaseTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class HomePageTests extends BaseTest {

    @Test(description = "Check default page is opened")
    @Severity(SeverityLevel.CRITICAL)
    public void checkPage() {
        pageProvider.getHomePage().openPage();
        pageProvider.getHomePage().isInputVisible();
    }

}
