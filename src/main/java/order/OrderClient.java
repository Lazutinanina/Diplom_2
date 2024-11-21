package order;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import rest.RestClient;
import java.util.List;
import java.util.Map;

public class OrderClient extends RestClient {
    private final String ORDERS = "/api/orders";
    @Step("Создаем заказ")
    public Response sendPostToCreateOrders(Map<String, List<String>> ingredients, String accessToken) {
        return reqSpec
                    .and()
                    .header("Authorization", accessToken)
                    .body(ingredients)
                    .when()
                    .post(ORDERS);
    }

    @Step("Получаем список заказов")
    public Response sendGetOrders(String accessToken) {
        return
                reqSpecGet
                        .and()
                        .header("Authorization", accessToken)
                        .when()
                        .get(ORDERS);
    }
}
