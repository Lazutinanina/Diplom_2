package ingredients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import rest.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IngredientClient extends RestClient {

    public static final String INGREDIENTS = "/api/ingredients";

    public Response sendGetToIngredients() {
        return reqSpec
                .get(INGREDIENTS);
    }
    @Step("Выбираем ингредиенты")
    public List<String> getRandomIngredients() {
        Response response = sendGetToIngredients();

        List<String> allIngredientsList = response.body().path("data._id");
        List<String> selectedIngredients = new ArrayList<>();

        Random random = new Random();
        int numberOfIngredients = random.nextInt(allIngredientsList.size());

        for (int i = 0; i < numberOfIngredients; i++) {
            selectedIngredients.add(allIngredientsList.get(i));
        }

        return selectedIngredients;
    }
}
