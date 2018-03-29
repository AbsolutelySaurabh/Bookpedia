package dexter.appsmoniac.bookpedia.model.http;

import dexter.appsmoniac.bookpedia.model.pojo.SearchResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by absolutelysaurabh on 29/3/18.
 */

public interface GoogleBooksAPI {

    public static final String URL_BASE = "https://www.googleapis.com/books/v1/volumes/";

    @GET("./")
    Observable<SearchResult> searchBook(@Query("q") String q);

}