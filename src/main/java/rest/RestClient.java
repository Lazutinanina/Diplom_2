package rest;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class RestClient {
    protected final String URL = "https://stellarburgers.nomoreparties.site/";
    protected final RequestSpecification reqSpec = given()
            .baseUri(URL)
            .log().all()
            .header("Content-type", "application/json");
    protected final RequestSpecification reqSpecGet = given()
            .baseUri(URL)
            .log().all();
}
