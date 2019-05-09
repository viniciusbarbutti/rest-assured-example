
import com.google.gson.Gson;
import io.restassured.http.ContentType;

import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class ExampleTest {
    @Test
    public void getExample (){
        given()
                .contentType(ContentType.JSON)
                .param("foo1", "1")
        .when()
                .get("https://postman-echo.com/get")
        .then()
                .statusCode(200)
                .header("Server", "nginx")
                .body("headers.host", is("postman-echo.com"))
                .body("args.foo1", containsString("1"));
    }

    @Test
    public void postExample (){
        Gson gson = new Gson();
        BodyExample bodyExample = new BodyExample("1", "2");

        given()
                .contentType(ContentType.JSON)
                .body(gson.toJson(bodyExample))
        .when()
                .post("https://postman-echo.com/post")
        .then()
                .statusCode(200)
                .body("data.foo1", containsString("1"));
    }

    @Test
    public void getResponseExample(){
        Gson gson = new Gson();
        BodyExample bodyExample = new BodyExample("1", "2");

        Response response =
            given()
                .contentType(ContentType.JSON)
                .body(gson.toJson(bodyExample))
            .when()
                .post("https://postman-echo.com/post")
            .then()
                .statusCode(200)
                .body("data.foo1", containsString("1"))
            .extract()
                .response();

        System.out.println(response.toString());
    }
}
