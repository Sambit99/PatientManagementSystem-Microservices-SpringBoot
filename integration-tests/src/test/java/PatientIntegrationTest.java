import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PatientIntegrationTest {

    static String token;

    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:4000";
    }

    @Test
    @Order(1)
    void shouldReturnOkWithValidToken() {
        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "password123"
                    }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("data", notNullValue())
                .body("data.token",notNullValue())
                .extract()
                .response();

        token = response.path("data.token").toString();

        System.out.println("Response: " + response.getBody().asString());
    }

    @Test
    @Order(2)
    void shouldReturnPatientsWithValidToken(){
        Response response = RestAssured.given()
                .header("Authorization", "Bearer "+ token)
                .when()
                .get("api/patients")
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println("Response: " + response.getBody().asString());
    }

}
