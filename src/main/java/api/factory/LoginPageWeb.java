package api.factory;

public class LoginPageWeb implements LoginPage {
    @Override
    public void open() {
        System.out.println("This is login page on WEB");
    }
}
