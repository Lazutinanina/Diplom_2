import user.User;
import user.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

public class CreateUserTest {

    private UserClient userClient  = new UserClient();
    private User user;

    @Before
    public void setUp() {
        user = User.getRandom();
    }
    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUser  () {
        Response response = userClient.postToCreateUser(user);
        response.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true));

        String token = response.body().path("accessToken");
        userClient.deleteUser(token);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createUserWhenExists() {
        userClient.postToCreateUser(user);
        Response response = userClient.postToCreateUser(user);
        response.then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", is(false));
    }

    @Test
    @DisplayName("Создание пользователя без логина")
    public void createUserWithoutEmail() {
        user.setEmail(null);
        Response response = userClient.postToCreateUser(user);
        response.then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", is(false));
    }
    @Test
    @DisplayName("Создание пользователя без пароля")
    public void createUserWithoutPassword() {
        user.setPassword(null);
        Response response = userClient.postToCreateUser(user);
        response.then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", is(false));
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    public void createUserWithoutName() {
        user.setName(null);
        Response response = userClient.postToCreateUser(user);
        response.then().assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", is(false));
    }
}
