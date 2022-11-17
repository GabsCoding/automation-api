import DTO.Request.ContactsRequest;
import DTO.Response.ContactsResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ValidateTaskApiTest {
    private static final String URI = "https://api-de-tarefas.herokuapp.com/";

    private static final String FILE_PATH = "src/test/resources/";

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
                        new File(FILE_PATH + "GetAllSchema.json")
                ))
                .log()
                .all();
    }

    @Test
    public void validateSearchById() {
        given()
                .header("content-type", "application/json")
                .pathParams("id", "156")
                .when()
                .log()
                .all()
                .get(URI + "contacts/{id}")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File(FILE_PATH + "GetOneSchema.json")
                ))
                .log()
                .all();
    }

    @Test(dataProvider = "contactInformation")
    public void validateContactRegistration(
            String name,
            String lastName,
            String email,
            String age,
            String phone,
            String address,
            String state,
            String city
    ) {
        ContactsRequest request = ContactsRequest
                .builder()
                .name(name)
                .lastName(lastName)
                .email(email)
                .age(age)
                .phone(phone)
                .address(address)
                .state(state)
                .city(city)
                .build();

        ContactsResponse response = given()
                .header("content-type", "application/json")
                .body(request)
                .when()
                .log()
                .all()
                .post(URI + "contacts")
                .then()
                .log()
                .all()
                .statusCode(201)
                .extract()
                .body()
                .as(ContactsResponse.class);

        Assert.assertNotNull(response.getData().getId());
        Assert.assertEquals(response.getData().getType(), "contacts");
    }

    @Test
    public void validateContactDeletion() {
        given()
                .pathParams("id", "3865")
                .when()
                .log()
                .all()
                .delete(URI + "contacts/{id}")
                .then()
                .log()
                .all()
                .statusCode(204);
    }

    @DataProvider(name="contactInformation")
    public Object[][] getData() {
        return new Object[][] {
                {"Henri", "Marques", "123@gmail.com", "21", "5559595959595", "Saint Edmund", "Bahia", "Salvador"},
                {"Gustavo", "Robert", "456@gmail.com", "19", "5559595959595", "Tars", "Rio de Janeiro", "Rio de Janeiro"},
                {"Tales", "Albino", "789@gmail.com", "28", "5559595959595", "Rash Tower", "Sao Paulo", "Sao Paulo"}
        };
    }
}
