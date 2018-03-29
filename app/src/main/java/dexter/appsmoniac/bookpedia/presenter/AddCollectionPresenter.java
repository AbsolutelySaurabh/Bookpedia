package dexter.appsmoniac.bookpedia.presenter;

import dexter.appsmoniac.bookpedia.model.dao.CollectionDao;
import dexter.appsmoniac.bookpedia.model.pojo.Book;
import dexter.appsmoniac.bookpedia.mvp.CollectionMvp;

/**
 * Created by absolutelysaurabh on 29/3/18.
 */

public class AddCollectionPresenter implements CollectionMvp.AddPresenter {

    private CollectionMvp.Model model;

    public AddCollectionPresenter(){
        this.model = new CollectionDao();
    }

    @Override
    public int saveBook(Book book) {
        return this.model.saveBook(book);
    }

    @Override
    public void closeRealm() {
        this.model.closeRealm();
    }
}
