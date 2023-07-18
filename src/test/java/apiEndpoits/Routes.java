package apiEndpoits;

/*
Swagger URI --> https://demoqa.com/swagger/
*/

public class Routes {
    public static String baseUrl = "https://demoqa.com";

    // User Routes
    private static String accountsBaseRoute = baseUrl + "/Account/v1/User";
    public static String postUserUrl = accountsBaseRoute;
    public static String getUserUrl = accountsBaseRoute + "/{UUID}";

    // BookStore Routes
    private static String booksBaseRoute = baseUrl + "/BookStore/v1";
    public static String getBooksUrl = booksBaseRoute + "/Books";
    public static String postBooksUrl = booksBaseRoute + "/Books";

    public static String updateBookUrl = booksBaseRoute + "/Books/{ISBN}";
    public static String deleteBookUrl = booksBaseRoute + "/Book";
    public static String getBookUrl = booksBaseRoute + "/Book";


}
