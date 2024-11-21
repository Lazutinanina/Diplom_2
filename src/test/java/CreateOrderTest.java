import ingredients.IngredientClient;
import order.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.*;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class CreateOrderTest extends UserTest {

    private IngredientClient ingredientClient = new IngredientClient();
    private OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void createOrderWithIngredients() {
       List<String> selectedIngredients = ingredientClient.getRandomIngredients();
       Map<String, List<String>> orderIngredients = new HashMap<>();
       orderIngredients.put("ingredients", selectedIngredients);
       Response response = orderClient.sendPostToCreateOrders(orderIngredients, token);
        response.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true));
    }

    @Test
    @DisplayName("Создание заказа c ингредиентами, без авторизации")
    public void createOrderWithoutAuth() {
        List<String> selectedIngredients = ingredientClient.getRandomIngredients();
        Map<String, List<String>> orderIngredients = new HashMap<>();
        orderIngredients.put("ingredients", selectedIngredients);
        Response response = orderClient.sendPostToCreateOrders(orderIngredients, "");
        response.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true));
    }

    @Test
    @DisplayName("Создание заказа c авторизацией, c неверным id ингредиента")
    public void createOrderWithWrongIngredientID() {
        Map<String, List<String>> ingredients = new HashMap<>();
        List<String> wrongIngredientsIDs = new ArrayList<>();
        wrongIngredientsIDs.add("wrongID");
        ingredients.put("ingredients", wrongIngredientsIDs);
        Response response = orderClient.sendPostToCreateOrders(ingredients, token);
        response.then().assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Создание заказ c авторизацией, без ингредиентов")
    public void createOrderWithoutIngredients() {
        Map<String, List<String>> ingredients = new HashMap<>();
        ingredients.put("ingredients", Collections.emptyList());
        Response response = orderClient.sendPostToCreateOrders(ingredients, token);
        response.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("success", is(false));
    }
}