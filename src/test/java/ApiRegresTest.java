
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ApiRegresTest {


    @Test
    void shouldReturnUsers() {
        // Given - When - Then
        given()
                .baseUri("https://reqres.in/")
                // Выполняемые действия
                .when()
                .get("api/users?page=2")
                // Проверки
                .then()
                .statusCode(200);
    }

    @Test
    void shouldReturnSingleUsers() {
        given()
                .baseUri("https://reqres.in/")
                .when()
                .get("api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", notNullValue());

    }


    @Test
    void shouldReturnUsersList() {
        given()
                .baseUri("https://reqres.in/")
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .body("page", equalTo(2))
                .body("data.id", notNullValue())
                .body("data.first_name", notNullValue())
                .body("data.email", notNullValue())
                .body("data.last_name", notNullValue())
                .body("data.avatar", notNullValue())
                .statusCode(200);
    }

    @Test
    void successReg() {
        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "pistol");
        given()
                .baseUri("https://reqres.in/")
                .body(user)
                .contentType(ContentType.JSON)
                .when()
                .post("api/register")
                .then().log().all()
                .body("id", equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));


    }

    @Test
    void UnSuccessReg() {
        Map<String, String> user = new HashMap<>();
        user.put("email", "sydney@fife");
        given()
                .baseUri("https://reqres.in/")
                .body(user)
                .contentType(ContentType.JSON)
                .when()
                .post("api/register")
                .then().log().all()
                .body("error", equalTo("Missing password"))
                .statusCode(400);
    }

    @Test
    void shouldUpdate() {
        Map<String, String> user = new HashMap<>();
        user.put("name", "morpheus");
        user.put("job", "zion resident");
        given()
                .baseUri("https://reqres.in/")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("api/users/2")
                .then()
                .statusCode(200);

    }

    @Test
    void notFound() {
        given()
                .baseUri("https://reqres.in/")
                .when()
                .get("api/unknown/23")
                .then()
                .statusCode(404);
    }

    @Test
    void loginPost() {
        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "cityslicka");
        given()
                .baseUri("https://reqres.in/")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/login")
                .then().log().all()
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));

    }

    @Test
    void shouldFail() {
        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "cityslicka");
        given()
                .baseUri("https://reqres.in/")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/login")
                .then().log().all()
                .body("token", equalTo("QpwL5tke4Pnpja7X"));

    }
}






