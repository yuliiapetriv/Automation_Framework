package api;

import api.dto.reqres.UserDataDto;
import api.dto.reqres.UsersDto;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqResTests {

    ApiHelper apiHelper = new ApiHelper();

    @Test(description = "Check users info on page 2 using DTO")
    public void checkPageUsingDto() {
        UserDataDto[] actualUsersData = given()
            .spec(apiHelper.requestSpecification)
            .queryParam("page", "2")
        .when()
            .get("https://reqres.in/api/users")
        .then()
            .spec(apiHelper.responseSpecification)
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract().body().as(UsersDto.class).getData();

        Assert.assertTrue(actualUsersData.length >= 1);
        Assert.assertTrue(actualUsersData[0].getEmail().contains("@reqres.in"));
    }

    @Test(description = "Check users info on page 2 using body matchers")
    public void checkPageUsingBodyMatchers() {
        given()
                .spec(apiHelper.requestSpecification)
                .queryParam("page", "2")
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .spec(apiHelper.responseSpecification)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("data.size()", greaterThanOrEqualTo(1))
                .body("data[0].email", endsWith("@reqres.in"));
    }

}
