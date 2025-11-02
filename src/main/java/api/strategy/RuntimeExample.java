package api.strategy;

import api.ApiHelper;
import org.testng.annotations.Test;

public class RuntimeExample {

    @Test
    public static void test() {

        ApiHelper apiHelper = new ApiHelper();

        AuthContext authContext = new AuthContext(new BasicAuth());
        authContext.performAuth(apiHelper.requestSpecification);


        authContext = new AuthContext(new TokenAuth());
        authContext.performAuth(apiHelper.requestSpecification);
    }

}
