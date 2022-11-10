import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;

public class ValidateTaskApi
{
    private static final String URI = "https://api-de-tarefas.herokuapp.com/";

    @Test
    public void validateGeneralSearch() {
        given()
                .when()
                .log()
                .all()
                .get(URI + "contacts")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/GetAllSchema.json")
                ))
                .log()
                .all();
    }

    @Test
    public void validateSearchById() {
        given()
                .header("content-type", "application/json")
                .pathParams("id", "3526")
                .when()
                .log()
                .all()
                .get(URI + "contacts/{id}")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/GetOneSchema.json")
                ))
                .log()
                .all();
    }
}
