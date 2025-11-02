package api.strategy;

import io.restassured.specification.RequestSpecification;

public class TokenAuth extends AuthStrategy {
    @Override
    void authenticate(RequestSpecification spec) {
        // spec.auth().oauth2("token");
        System.out.println("This is token auth");
    }
}
