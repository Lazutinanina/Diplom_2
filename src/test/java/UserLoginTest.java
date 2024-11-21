import user.*;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

public class UserLoginTest extends UserTest {
    @Test
    @DisplayName("Логин под существующим пользователем")
    public void checkLoginWithValidCredentials() {
        UserCredentials credentials = UserCredentials.from(user);
        Response response = userClient.postToLogin(credentials);
        response.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true));
    }

    @Test
    @DisplayName("Проверка входа с невалидным логином")
    public void checkLoginCourierIncorrectLoginNameResponse404() {
        user.setEmail(RandomStringUtils.randomAlphabetic(10) + "@yandex.ru");
        UserCredentials credentials = UserCredentials.from(user);
        Response response = userClient.postToLogin(credentials);
        response.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", is(false));
    }

    @Test
    @DisplayName("Проверка входа с невалидным паролем")
    public void checkLoginCourierIncorrectPasswordResponse404() {
        user.setPassword(RandomStringUtils.randomAlphanumeric(10));
        UserCredentials credentials = UserCredentials.from(user);
        Response response = userClient.postToLogin(credentials);
        response.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", is(false));
    }
}
