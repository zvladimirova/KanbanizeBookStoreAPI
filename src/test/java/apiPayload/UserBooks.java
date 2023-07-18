package apiPayload;

import java.util.ArrayList;
import java.util.List;

public class UserBooks {

    String userId;

    List<Isbn> collectionOfIsbns = new ArrayList<Isbn>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Isbn> getCollectionOfIsbns() {
        return collectionOfIsbns;
    }

    public void setCollectionOfIsbns(List<Isbn> collectionOfIsbns) {
        this.collectionOfIsbns = collectionOfIsbns;
    }


}
