package api;

import api.dto.reqres.CreateUserDto;
import api.dto.reqres.UserDataDto;
import api.dto.reqres.UsersDto;
import data.ApiEndpoints;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @DataProvider(name = "userTestData")
    public Object[][] getTestData() {
        return new Object[][] {
                {"Alice", "QA Engineer"}
        };
    }

    @Test(description = "Create user", dataProvider = "userTestData")
    public void createUser(String name, String job) {
        CreateUserDto userData = CreateUserDto.builder()
                .name(name)
                .job(job)
                .build();

        given()
                .spec(apiHelper.requestSpecification)
                .body(userData)
                .when()
                .post(ApiEndpoints.CREATE_USER_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name", equalTo(name))
                .body("job", equalTo(job))
                .body("id", notNullValue());
    }

    @Test(description = "Check Get request")
    public void checkGetReq() {
        given()
                .spec(apiHelper.requestSpecification)
                .header("x-api-key", "reqres-free-v1")
                .queryParam("page", 2)
                .when()
                .get(ApiEndpoints.CREATE_USER_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("data.size()", equalTo(6))
                .body("data[0].id", greaterThanOrEqualTo(0));
    }

    @Test(description = "Check POST request")
    public void checkPostReq() {
        Map<String, String> userData = new HashMap<>(Map.of(
                "name", "morpheus",
                "job", "leader"
        ));

        given()
                .spec(apiHelper.requestSpecification)
                .header("x-api-key", "reqres-free-v1")
                .body(userData)
                .when()
                .post(ApiEndpoints.CREATE_USER_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", notNullValue())
                .body("createdAt", containsString(LocalDate.now().toString()));
    }

    @Test(description = "Check DELETE request")
    public void checkDeleteReq() {
        String res = given()
                .spec(apiHelper.requestSpecification)
                .header("x-api-key", "reqres-free-v1")
                .when()
                .delete(ApiEndpoints.CREATE_USER_ENDPOINT + "/2")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().response().asString();

        Assert.assertTrue(res.isEmpty());
    }

    String userId;
    @Test(description = "Check get request")
    public void checkJason() {
        Response res = given()
                .spec(apiHelper.requestSpecification)
                .header("x-api-key", "reqres-free-v1")
                .queryParam("page", 2)
                .when()
                .get(ApiEndpoints.CREATE_USER_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        List<String> emails = res.jsonPath().getList("data.email");
        emails.forEach(email -> Assert.assertFalse(email.endsWith(".uk")));
        userId = res.jsonPath().getString("data.id");
    }

    @DataProvider(name = "parameters")
    public Object[][] testParams() {
        return new Object[][] {
                {1}, {2}
        };
    }

    @Test(description = "Check get request", dataProvider = "parameters")
    public void checkParameters(int param) {
        Response res = given()
                .spec(apiHelper.requestSpecification)
                .header("x-api-key", "reqres-free-v1")
                .queryParam("page", param)
                .when()
                .get(ApiEndpoints.CREATE_USER_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        Assert.assertEquals(res.jsonPath().getInt("per_page"), res.jsonPath().getList("data").size());
    }

    @Test(description = "Check get request")
    public void checkStatus() {
        List<String> res = given()
                .spec(apiHelper.requestSpecification)
                .when()
                .get("https://gorest.co.in/public/v2/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().jsonPath().getList("status");

        boolean isActive = res.stream().anyMatch(status -> status.equals("active"));
        Assert.assertTrue(isActive);

        List<String> statuses = res.stream().filter(status -> status.equalsIgnoreCase("active")).collect(Collectors.toList());
        Assert.assertFalse(statuses.isEmpty());
    }
}
