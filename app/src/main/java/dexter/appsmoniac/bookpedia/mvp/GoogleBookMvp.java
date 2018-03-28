package dexter.appsmoniac.bookpedia.mvp;

import rx.Observable;
import dexter.appsmoniac.bookpedia.model.pojo.SearchResult;

/**
 * Created by absolutelysaurabh on 29/3/18.
 */

public interface GoogleBookMvp{

    interface Model{
        Observable<SearchResult> searchListOfBooks(String query);
    }

    interface View {
        void searhByQuery(String query);
        void showError();
        void showNoResults();
        void updateListView(SearchResult searchResult);
    }

    interface Presenter {
        void searchListOfBooks(String query);
    }
}
