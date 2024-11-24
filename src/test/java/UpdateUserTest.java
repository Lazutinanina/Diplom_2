import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class UpdateUserTest  extends UserTest {
    private final String userField;
    public UpdateUserTest(String userField) {
        this.userField = userField;
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList("name", "email", "password");
    }

    @Before
    public void changeUserData() {
       switch (userField) {
           case "name":
               user.setName(RandomStringUtils.randomAlphabetic(10));
               break;
           case "password":
                user.setPassword(RandomStringUtils.randomAlphabetic(10));
                break;
           case "email":
               user.setEmail(RandomStringUtils.randomAlphabetic(10).toLowerCase() + "@yandex.ru");
               break;
       }
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void updateUser() {
        Response response = userClient.patchUser(user, token);
        response.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true))
                .body("user.name", is(user.getName()))
                .body("user.email", is(user.getEmail()));
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void updateUserWithoutToken() {
        Response response = userClient.patchUser(user, "");
        response.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", is(false))
                .body("message", is("You should be authorised"));

    }
}
