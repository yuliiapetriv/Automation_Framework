package api.strategy;

import io.restassured.specification.RequestSpecification;

public class BasicAuth extends AuthStrategy {
    @Override
    void authenticate(RequestSpecification spec) {
        // spec.auth().preemptive().basic("user", "pass");
        System.out.println("This is basic auth");
    }
}
