package apiPayload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookList {
    public String userId;
    public String username;


    List<Book> books =  new ArrayList<Book>();

    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
