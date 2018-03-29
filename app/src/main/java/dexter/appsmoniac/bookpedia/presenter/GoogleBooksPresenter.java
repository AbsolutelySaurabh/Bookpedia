package dexter.appsmoniac.bookpedia.presenter;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import dexter.appsmoniac.bookpedia.model.http.GoogleBooksService;
import dexter.appsmoniac.bookpedia.model.pojo.SearchResult;
import dexter.appsmoniac.bookpedia.mvp.GoogleBookMvp;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
/**
 * Created by absolutelysaurabh on 29/3/18.
 */

public class GoogleBooksPresenter implements GoogleBookMvp.Presenter {

    private GoogleBookMvp.View view;
    private GoogleBookMvp.Model model;

    public GoogleBooksPresenter(GoogleBookMvp.View view){
        this.view = view;
        this.model = new GoogleBooksService();
    }

    @Override
    public void searchListOfBooks(String query) {
        Observable<SearchResult> resultObservable = model.searchListOfBooks(query);
        resultObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        searchResult -> EventBus.getDefault().postSticky(searchResult),
                        throwable -> {
                            view.showError();
                            throwable.printStackTrace();
                        },
                        ()-> Log.d("LOG", "searchListOfBooks complete!")

                );
    }
}
