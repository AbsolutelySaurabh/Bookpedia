package dexter.appsmoniac.bookpedia.model.http;

import dexter.appsmoniac.bookpedia.model.pojo.SearchResult;
import dexter.appsmoniac.bookpedia.mvp.GoogleBookMvp;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by absolutelysaurabh on 29/3/18.
 */

public class GoogleBooksService implements GoogleBookMvp.Model{

    @Override
    public Observable<SearchResult> searchListOfBooks(String query) {

        RxJavaCallAdapterFactory rxAdapter =
                RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GoogleBooksAPI.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();

        GoogleBooksAPI googleBooksService = retrofit.create(GoogleBooksAPI.class);
        Observable<SearchResult> searchResultObservable = googleBooksService.searchBook(query);

        return searchResultObservable;
    }
}
