package apiEndpoits;


import apiPayload.UserIsbn;
import apiPayload.UserBooks;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookStoreEndPoints {

    public static Response getBooks(String username, String password) {

        Response response = given()
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get(Routes.getBooksUrl);
        return response;

    }

    public static Response addBook(String username, String password, UserBooks userBookPayload) {

        Response response = given()
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userBookPayload)
                .when()
                .post(Routes.postBooksUrl);
        return response;

    }

    public static Response updateBook(String username, String password, String isbn, UserIsbn userBookPayload) {
        Response response = given()
                .auth().preemptive().basic(username, password)
                .pathParam("ISBN", isbn)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userBookPayload)
                .when()
                .put(Routes.updateBookUrl);

        return response;

    }

    public static Response deleteBook(String username, String password, UserIsbn userBookPayload) {
        Response response = given()
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userBookPayload)
                .when()
                .delete(Routes.deleteBookUrl);

        return response;
    }


    public static Response getBookByIsbn(){
        Response response = given()
                .queryParam("ISBN", "9781491904244")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get(Routes.getBookUrl);

        return response;

    }

}
