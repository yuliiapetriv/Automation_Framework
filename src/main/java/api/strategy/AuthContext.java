package api.strategy;

import io.restassured.specification.RequestSpecification;

public class AuthContext {
    private AuthStrategy authStrategy;

    public AuthContext(AuthStrategy strategy) {
        this.authStrategy = strategy;
    }

    public void performAuth(RequestSpecification spec) {
        authStrategy.authenticate(spec);
    }

}
