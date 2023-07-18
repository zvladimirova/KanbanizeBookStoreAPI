package apiTest;

import apiEndpoits.BookStoreEndPoints;
import apiEndpoits.UserEndPoints;
import apiPayload.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class BookStoreTests {
    BookDataProvider bookDataProvider;

    @BeforeClass
    public void createUser(ITestContext context) {

        this.bookDataProvider = new BookDataProvider();

        UserDataProvider userDataProvider = new UserDataProvider();
        User userPayload = userDataProvider.userWithValidData();

        Response response = UserEndPoints.createUser(userPayload);

        context.setAttribute("userName", userPayload.getUserName());
        context.setAttribute("password", userPayload.getPassword());

        String id = response.jsonPath().getString("userID");
        context.setAttribute("userID", id);

    }

    @Test(priority = 1)
    public void getBooksAndAddBookInCollection(ITestContext context) throws JsonProcessingException {

        String id = (String) context.getAttribute("userID");
        String username = (String) context.getAttribute("userName");
        String password = (String) context.getAttribute("password");

        Response response = BookStoreEndPoints.getBooks(username, password);

        String isbn = response.jsonPath().get("books[0].isbn").toString();

        context.setAttribute("isbn", isbn);

        // Prepare data for add book
        //this.bookDataProvider = new BookDataProvider();

        UserBooks userBooksPayload = this.bookDataProvider.bookPayloadData(id, isbn);

        //Add book in collection
        Response response2 = BookStoreEndPoints.addBook(username, password, userBooksPayload);

        // Extract added ISBN from response
        String responseIsbn = response2.jsonPath().getString("books[0].isbn");

        //Asserts
        Assert.assertEquals(response2.getStatusCode(), 201);
        Assert.assertEquals(responseIsbn, isbn);

    }

    @Test(priority = 2)
    public void replaceBookInCollection(ITestContext context) {

        String username = (String) context.getAttribute("userName");
        String password = (String) context.getAttribute("password");
        String id = (String) context.getAttribute("userID");
        String isbnOldOne = (String) context.getAttribute("isbn");

        // Get Books
        BookList bookList = BookStoreEndPoints.getBooks(username, password).body().as(BookList.class);

        // Get ISBN different from previous one (addedIsbn)
        String isbnNewOne = getNewIsbn(bookList, isbnOldOne);
        context.setAttribute("isbn", isbnNewOne);

        // Update Book
        UserIsbn updateBookPayload = this.bookDataProvider.updateBookPayloadData(isbnNewOne, id);

        // BookList updatedBookList = BookStoreEndPoints.updateBook(username, password, isbnOldOne, updateBookPayload).body().as(BookList.class);
        Response response = BookStoreEndPoints.updateBook(username, password, isbnOldOne, updateBookPayload);
        BookList updatedBookList = response.body().as(BookList.class);


        // Check if new Isbn exists in the collection
        Boolean isNewIsbnExists = validatingIsbnExistsInCollection(updatedBookList, isbnNewOne);

        // Confirm old Isbn not exists
        Boolean isOldIsbnExists = validatingIsbnExistsInCollection(updatedBookList, isbnOldOne);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(isNewIsbnExists, true);
        Assert.assertEquals(isOldIsbnExists, false);
        Assert.assertEquals(updatedBookList.getUserId(), id);
    }

    @Test(priority = 3)
    public void deleteBookInCollection(ITestContext context) throws JsonProcessingException {

        String username = (String) context.getAttribute("userName");
        String password = (String) context.getAttribute("password");
        String id = (String) context.getAttribute("userID");
        String isbn = (String) context.getAttribute("isbn");

        // Delete Book
        UserIsbn updateBookPayload = this.bookDataProvider.updateBookPayloadData(isbn, id);

        Response response = BookStoreEndPoints.deleteBook(username, password, updateBookPayload);

        // Status Code Validation
        Assert.assertEquals(response.getStatusCode(), 204);

        // Get all books in collection by userID to verify the book is deleted
        BookList bookList = UserEndPoints.readUser(id, username, password).body().as(BookList.class);

        Boolean deletedIsbnExist = validatingIsbnExistsInCollection(bookList, isbn);

        Assert.assertEquals(deletedIsbnExist, false);
    }

    @Test(priority = 4)
    public void addBookInCollectionWithInvalidIsbn(ITestContext context) {

        String id = (String) context.getAttribute("userID");
        String username = (String) context.getAttribute("userName");
        String password = (String) context.getAttribute("password");

        //Add Book with Invalid ISBN
        UserBooks userBooksPayload = this.bookDataProvider.bookPayloadData(id, "21212121212");

        Response response = BookStoreEndPoints.addBook(username, password, userBooksPayload);
        String errorMessage = response.jsonPath().getString("message");

        //Asserts
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(errorMessage, "ISBN supplied is not available in Books Collection!");

    }

    @Test(priority = 5)
    public void deleteNotExistingBookInCollection(ITestContext context) throws JsonProcessingException {

        String id = (String) context.getAttribute("userID");
        String username = (String) context.getAttribute("userName");
        String password = (String) context.getAttribute("password");

        // Get Books
        Response response = BookStoreEndPoints.getBooks(username, password);

        // Get the first ISBN from all books
        String addedIsbn = response.jsonPath().get("books[0].isbn").toString();

        // Add Book in Collection
        UserBooks userBooksPayload = this.bookDataProvider.bookPayloadData(id, addedIsbn);

        Response response2 = BookStoreEndPoints.addBook(username, password, userBooksPayload);
        Assert.assertEquals(response2.getStatusCode(), 201);

        // Get Books
        BookList bookList = BookStoreEndPoints.getBooks(username, password).body().as(BookList.class);

        // Get ISBN different from previous one (addedIsbn)
        String newIsbn = getNewIsbn(bookList, addedIsbn);

        // Delete New Book ISBN (not added in collection)
        UserIsbn deleteBookPayload = this.bookDataProvider.deleteBookPayloadData(newIsbn, id);

        Response response3 = BookStoreEndPoints.deleteBook(username, password, deleteBookPayload);

        String errorMessage = response3.jsonPath().getString("message");

        //Asserts
        Assert.assertEquals(response3.getStatusCode(), 400);
        Assert.assertEquals(errorMessage, "ISBN supplied is not available in User's Collection!");
    }

    @Test
    public void checkBookPagesByIsbn() throws JsonProcessingException {

        Book book = BookStoreEndPoints.getBookByIsbn().body().as(Book.class);
        int bookPages = book.getPages();

        Assert.assertEquals(bookPages, 278);
    }


    private String getNewIsbn(BookList bookList, String isbn1) {
        String isbn = null;

        for (Book book : bookList.getBooks()) {
            String bookIsbn = book.getIsbn();

            if (!bookIsbn.equals(isbn1)) {
                isbn = bookIsbn;

                break;
            }
        }
        return isbn;
    }

    private Boolean validatingIsbnExistsInCollection(BookList bookList, String isbn) {
        String currentBookIsbn = null;
        Boolean isNewIsbnExists = false;

        for (Book book : bookList.getBooks()) {
            currentBookIsbn = book.getIsbn();

            if (currentBookIsbn.equals(isbn)) {
                isNewIsbnExists = true;

                break;
            }
        }
        return isNewIsbnExists;
    }

}

