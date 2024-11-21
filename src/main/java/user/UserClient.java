package user;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import rest.RestClient;

public class UserClient extends RestClient {
    private final String AUTH = "/api/auth";

    @Step("Авторизация пользователя")
    public Response postToLogin(UserCredentials credentials) {
        return reqSpec
                .and()
                .body(credentials)
                .when()
                .post(AUTH + "/login");
    }

    @Step("Регистрация пользователя")
    public Response postToCreateUser(User user) {
        return reqSpec
                .and()
                .body(user)
                .when()
                .post(AUTH + "/register");
    }

    @Step("Удаление пользователя")
    public void deleteUser(User user, String accessToken) {
         reqSpec
            .header("Authorization", accessToken)
            .body(user)
            .when()
            .delete(AUTH + "/user");
    }

    @Step("Обновление данных пользователя")
    public Response patchUser(User user, String accessToken) {
        return reqSpec
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(AUTH + "/user");
    }
}
