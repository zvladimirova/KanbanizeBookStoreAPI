package apiPayload;

import java.util.ArrayList;
import java.util.List;

public class BookDataProvider {

    public UserBooks bookPayloadData(String userId, String isbn){
        UserBooks userBookPayload= new UserBooks();
        userBookPayload.setUserId(userId);

        List<Isbn> isbns = new ArrayList<Isbn>();
        isbns.add(new Isbn(isbn));
        userBookPayload.setCollectionOfIsbns(isbns);

        return userBookPayload;


    }
    
    public UserIsbn updateBookPayloadData(String isbn, String userId){
        UserIsbn updateDeleteBookPayload = new UserIsbn();
        updateDeleteBookPayload.setIsbn(isbn);
        updateDeleteBookPayload.setUserId(userId);

        return updateDeleteBookPayload;

    }

    public UserIsbn deleteBookPayloadData(String isbn, String userId){
        UserIsbn deleteBookPayload = new UserIsbn();
        deleteBookPayload.setIsbn(isbn);
        deleteBookPayload.setUserId(userId);

        return deleteBookPayload;

    }


}
