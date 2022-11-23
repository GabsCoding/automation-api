import DTO.Request.ContactsRequest;
import DTO.Response.ContactsResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ValidateTaskApiTest {
    private static final String ENDPOINT = "https://api-de-tarefas.herokuapp.com/contacts/";

    private static final String FILE_PATH = "src/test/resources/";

    private String id = null;

    @Test(dataProvider = "contactInformationCreate", priority = 1)
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
                .post(ENDPOINT)
                .then()
                .log()
                .all()
                .statusCode(201)
                .extract()
                .body()
                .as(ContactsResponse.class);

        Assert.assertNotNull(response.getData().getId());
        Assert.assertEquals(response.getData().getType(), "contacts");

        this.id = response.getData().getId();
    }

    @Test(priority = 2)
    public void validateSearchById() {
        given()
                .header("content-type", "application/json")
                .pathParams("id", this.id)
                .when()
                .log()
                .all()
                .get(ENDPOINT + "{id}")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File(FILE_PATH + "GetOneSchema.json")
                ))
                .log()
                .all();
    }

    @Test
    public void validateGeneralSearch() {
        given()
                .when()
                .log()
                .all()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File(FILE_PATH + "GetAllSchema.json")
                ))
                .log()
                .all();
    }

    @Test(dataProvider = "contactInformationUpdate", priority = 3)
    public void validateContactUpdate(
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
                .pathParams("id", this.id)
                .body(request)
                .log()
                .all()
                .when()
                .put(ENDPOINT + "{id}")
                .then()
                .statusCode(200)
                .log()
                .all()
                .extract()
                .body()
                .as(ContactsResponse.class);

        Assert.assertNotNull(response.getData().getId());
        Assert.assertEquals(response.getData().getType(), "contacts");}

    @Test(priority = 4)
    public void validateContactDeletion() {
        given()
                .pathParams("id", this.id)
                .when()
                .log()
                .all()
                .delete(ENDPOINT + "{id}")
                .then()
                .log()
                .all()
                .statusCode(204);
    }

    @DataProvider(name="contactInformationCreate")
    public Object[][] getCreateData() {
        return new Object[][] {
                {"Henri", "Marques", "fa@gmail.com", "21", "5559595959595", "Saint Edmund", "Bahia", "Salvador"},
        };
    }

    @DataProvider(name="contactInformationUpdate")
    public Object[][] getUpdateData() {
        return new Object[][] {
                {"John", "Doe", "ra@gmail.com", "34", "5558989898989", "Saint Road", "SP", "SP"},
        };
    }
}
