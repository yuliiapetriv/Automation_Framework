package data;

public interface ApiEndpoints {
    String BASE_URL = "https://demoqa.com";
    String CREATE_USER = BASE_URL + "/Account/v1/User";
    String GET_USER_INFO = BASE_URL + "/Account/v1/User/{userId}";
    String LOGIN = BASE_URL + "/Account/v1/Login";
    String GENERATE_TOKEN = BASE_URL + "/Account/v1/GenerateToken";
    String DELETE_ALL_BOOKS = BASE_URL + "/BookStore/v1/Books?UserId={userId}";
    String GET_ALL_BOOKS = BASE_URL + "/BookStore/v1/Books";
    String CREATE_USER_ENDPOINT = "https://reqres.in/api/users";
}
