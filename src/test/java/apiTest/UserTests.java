package apiTest;

import apiEndpoits.UserEndPoints;
import apiPayload.User;
import apiPayload.UserDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests {

    UserDataProvider userDataProvider;

    @BeforeClass
    public void setupData() {
        this.userDataProvider = new UserDataProvider();
    }

    @Test(priority = 1)
    public void testCreateUser(ITestContext context) {

        User userPayload = this.userDataProvider.userWithValidData();

        Response response = UserEndPoints.createUser(userPayload);

        String id = response.jsonPath().getString("userID");
        context.setAttribute("userID", id);

        context.setAttribute("userName", userPayload.getUserName());
        context.setAttribute("password", userPayload.getPassword());

        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test(priority = 2)
    public void testGetUser(ITestContext context) {

        String id = (String) context.getAttribute("userID");
        String username = (String) context.getAttribute("userName");
        String password = (String) context.getAttribute("password");

        Response response = UserEndPoints.readUser(id, username, password);

        String responseUserName = response.jsonPath().getString("username");

        // Asserts
        Assert.assertEquals(responseUserName, username);
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(priority = 3)
    public void testCreateUserWithEmptyUsername() {

        User userWithEmptyUsername = this.userDataProvider.userWithEmptyUsername();

        Response response = UserEndPoints.createUser(userWithEmptyUsername);
        response.then();

        String errorCode = response.jsonPath().getString("code");
        String errorMessage = response.jsonPath().getString("message");

        // Asserts
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(errorCode, "1200");
        Assert.assertEquals(errorMessage, "UserName and Password required.");
    }
    @Test(priority = 4)
    public void testCreateUserWithEmptyPassword() {

        User userWithEmptyPassword = this.userDataProvider.userWithEmptyPassword();

        Response response = UserEndPoints.createUser(userWithEmptyPassword);
        response.then();

        String errorCode = response.jsonPath().getString("code");
        String errorMessage = response.jsonPath().getString("message");

        // Asserts
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(errorCode, "1200");
        Assert.assertEquals(errorMessage, "UserName and Password required.");
    }
    @Test (priority = 5)
    public void testCreateUserWithShortPassword() {

        User shortPasswordPayloadData= this.userDataProvider.userWithWithShortPassword();

        Response response = UserEndPoints.createUser(shortPasswordPayloadData);
        response.then();

        String errorCode = response.jsonPath().getString("code");
        String errorMessage = response.jsonPath().getString("message");

        // Asserts
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(errorCode, "1300");
        Assert.assertEquals(errorMessage, "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.");
    }
    @Test(priority = 6)
    public void testCreateUserWithPasswordWithoutUpperCase() {

        User passwordWithoutUpperCasePayloadData= this.userDataProvider.userWithPasswordWithoutUpperCase();

        Response response = UserEndPoints.createUser(passwordWithoutUpperCasePayloadData);
        response.then();

        String errorCode = response.jsonPath().getString("code");
        String errorMessage = response.jsonPath().getString("message");

        // Asserts
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(errorCode, "1300");
        Assert.assertEquals(errorMessage, "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.");
    }
    @Test (priority = 7)
    public void testCreateUserWithPasswordWithoutLowerCase() {

        User passwordWithoutLowerCasePayloadData= this.userDataProvider.userWithPasswordWithoutLowerCase();

        Response response = UserEndPoints.createUser(passwordWithoutLowerCasePayloadData);
        response.then();

        String errorCode = response.jsonPath().getString("code");
        String errorMessage = response.jsonPath().getString("message");

        // Asserts
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(errorCode, "1300");
        Assert.assertEquals(errorMessage, "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.");
    }

    @Test (priority = 8)
    public void testCreateUserWithPasswordWithoutSpecialChar() {

        User passwordWithoutSpecialCharPayloadData= this.userDataProvider.userWithPasswordWithoutSpecialChar();

        Response response = UserEndPoints.createUser(passwordWithoutSpecialCharPayloadData);
        response.then();

        String errorCode = response.jsonPath().getString("code");
        String errorMessage = response.jsonPath().getString("message");

        // Asserts
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(errorCode, "1300");
        Assert.assertEquals(errorMessage, "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.");
    }

    @Test (priority = 9)
    public void testCreateUserWithPasswordWithoutNumber() {

        User passwordWithoutSpecialCharPayloadData= this.userDataProvider.userWithPasswordWithoutNumber();

        Response response = UserEndPoints.createUser(passwordWithoutSpecialCharPayloadData);
        response.then();

        String errorCode = response.jsonPath().getString("code");
        String errorMessage = response.jsonPath().getString("message");

        // Asserts
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(errorCode, "1300");
        Assert.assertEquals(errorMessage, "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.");
    }
}
