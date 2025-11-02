package api.strategy;

import io.restassured.specification.RequestSpecification;

public abstract class AuthStrategy {
    abstract void authenticate(RequestSpecification spec);
}
