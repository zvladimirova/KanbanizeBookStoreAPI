package apiEndpoits;

import apiPayload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserEndPoints {

    public static Response createUser(User payload) {

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.postUserUrl);
        return response;

    }

    public static Response readUser(String id, String username, String password) {

        Response response = given()
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("UUID", id)
                .when()
                .get(Routes.getUserUrl);

        return response;

    }

}
