import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthIntegrationTest {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4000";
    }

    static String token = "";

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
    void shouldReturnUnauthorizedOnInvalidLogin() {
        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "password12345"
                    }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401)
                .extract()
                .response();

        System.out.println("Response: " + response.getBody().asString());
    }

    @Test
    @Order(3)
    void shouldReturnOkOnValidToken() {

        Response response = RestAssured.given()
                .header("Authorization", "Bearer "+ token).
                when()
                .get("/auth/validate")
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println("Response: " + response.getBody().asString());
    }

    @Test
    @Order(4)
    void shouldReturnUnauthorizedOnInvalidToken() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer "+ "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0dXNlckB0ZXN0LmNvbSIsInJvbGUiOiJBRE1JTiI").
                when()
                .get("/auth/validate")
                .then()
                .statusCode(401)
                .extract()
                .response();

        System.out.println("Response: " + response.getBody().asString());
    }
}
