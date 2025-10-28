package pages;

import base.BaseTest;
import org.testng.annotations.Test;

public class HomePageTests extends BaseTest {

    @Test
    public void checkPage() {
        pageProvider.getHomePage().openPage();
        pageProvider.getHomePage().isInputVisible();
    }

}
