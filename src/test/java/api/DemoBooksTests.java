package api;

import api.dto.AddBookDto;
import api.dto.BooksDto;
import api.dto.IsbnDto;
import common.Log;
import data.ApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DemoBooksTests {

    @DataProvider(name = "userTestData")
    public Object[][] getTestData() {
        return new Object[][] {
                {"user1"},
                {"user2"},
                {"user3"}
        };
    }

    ApiHelper apiHelper = new ApiHelper();

    private static final String USERNAME = "TestYulia123";
    private static final String PASSWORD = "hS9J!DESr97d!ts";

    private static JSONObject userData;
    private static String token;
    private static String userId;

    @BeforeClass()
    public void createUser() {
        userData = new JSONObject();
        userData.put("userName", USERNAME);
        userData.put("password", PASSWORD);
        
        int statusCode = given()
                .spec(apiHelper.requestSpecification)
                .body(userData.toMap())
                .when()
                .post(ApiEndpoints.CREATE_USER)
                .getStatusCode();
        
        if (statusCode == HttpStatus.SC_CREATED) {
            Log.info("User successfully created");
        } else if (statusCode == HttpStatus.SC_NOT_ACCEPTABLE) {
            Log.info("User already exist");
        } else {
            Log.warn("Unexpected response while creating user: " + statusCode);
        }
    }

    @BeforeMethod()
    public String generateToken() {
        return given()
                .spec(apiHelper.requestSpecification)
                .body(userData.toMap())
                .when()
                .post(ApiEndpoints.GENERATE_TOKEN)
                .then()
                .spec(apiHelper.responseSpecification)
                .statusCode(HttpStatus.SC_OK)
                .extract().response().jsonPath().getString("token");
    }

    @BeforeMethod()
    public void loginUser() {
        Response response = given()
                .spec(apiHelper.requestSpecification)
                .body(userData.toMap())
                .when()
                .post(ApiEndpoints.LOGIN)
                .then()
                .spec(apiHelper.responseSpecification)
                .statusCode(HttpStatus.SC_OK)
                .extract().response();
        token = response.jsonPath().getString("token");
        userId = response.jsonPath().getString("userId");
    }

    @Step("Delete all users books")
    private void deleteAllBooks() {
        given()
                .spec(apiHelper.requestSpecification)
                // .header("Authorization", "Bearer " + token)
                .auth().oauth2(token)
                .when()
                .delete(ApiEndpoints.DELETE_ALL_BOOKS, userId)
                .then()
                .spec(apiHelper.responseSpecification)
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        Log.info("All books deleted");
    }

    @Step("Get first book isbn")
    private String getFirstBookIsbn() {
        BooksDto books = given()
                .spec(apiHelper.requestSpecification)
                .when()
                .get(ApiEndpoints.GET_ALL_BOOKS)
                .then()
                .spec(apiHelper.responseSpecification)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().body().as(BooksDto.class);

        return books.getBooks()[0].getIsbn();
    }

    @Step("Add book")
    private void addBook() {
        IsbnDto[] booksToAdd = {IsbnDto.builder().isbn(getFirstBookIsbn()).build()};

        AddBookDto requestData = AddBookDto.builder()
                .userId(userId)
                .collectionOfIsbns(booksToAdd)
                .build();

        given()
                .spec(apiHelper.requestSpecification)
                // .header("Authorization", "Bearer " + token)
                .auth().oauth2(token)
                .body(requestData)
                .when()
                .post(ApiEndpoints.GET_ALL_BOOKS)
                .then()
                .spec(apiHelper.responseSpecification)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test(description = "Check all users books")
    public void getAllUserBooks() {
        deleteAllBooks();
        addBook();

        String actualBookIsbn = given()
                .spec(apiHelper.requestSpecification)
                // .header("Authorization", "Bearer " + token)
                .auth().oauth2(token)
                .when()
                .get(ApiEndpoints.GET_USER_INFO, userId)
                .then()
                .spec(apiHelper.responseSpecification)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().body().as(BooksDto.class).getBooks()[0].getIsbn();

        Assert.assertEquals(actualBookIsbn, getFirstBookIsbn());

    }

}
