import ingredients.IngredientClient;
import order.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

public class GetUserOrderTest extends UserTest {
    private IngredientClient ingredientClient = new IngredientClient();
    private OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получаем список заказов пользователя")
    public void getUserOrders() {
        List<String> selectedIngredients = ingredientClient.getRandomIngredients();
        Map<String, List<String>> orderIngredients = new HashMap<>();
        orderIngredients.put("ingredients", selectedIngredients);
        orderClient.sendPostToCreateOrders(orderIngredients, token);
        Response response = orderClient.sendGetOrders(token);
        response.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true));
    }

    @Test
    @DisplayName("Получаем список заказов без авторизации")
    public void getUserOrderWithoutToken() {
        List<String> selectedIngredients = ingredientClient.getRandomIngredients();
        Map<String, List<String>> orderIngredients = new HashMap<>();
        orderIngredients.put("ingredients", selectedIngredients);
        orderClient.sendPostToCreateOrders(orderIngredients, token);
        Response response = orderClient.sendGetOrders("");
        response.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", is(false))
                .body("message", is("You should be authorised"));
    }
}
