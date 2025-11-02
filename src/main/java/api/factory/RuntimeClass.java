package api.factory;

import org.testng.annotations.Test;

public class RuntimeClass {
    @Test
    public void test() {
        LoginPage loginPage = LoginPageFactory.createLoginPage("mobile");

        loginPage.open();
    }

}
