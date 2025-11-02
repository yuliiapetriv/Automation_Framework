package api.factory;

public class LoginPageFactory {
    public static LoginPage createLoginPage(String platform) {
        if (platform.equalsIgnoreCase("mobile")) return new LoginPageMobile();
        else return new LoginPageWeb();
    }
}
