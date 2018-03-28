package dexter.appsmoniac.bookpedia.model.dao;

import dexter.appsmoniac.bookpedia.model.pojo.Book;
import dexter.appsmoniac.bookpedia.model.realm.po.RealmBook;
import dexter.appsmoniac.bookpedia.mvp.CollectionMvp;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by absolutelysaurabh on 29/3/18.
 */

public class CollectionDao implements CollectionMvp.Model{

    private static Realm realm;
    @Override
    public int saveBook(Book book) {
        return 0;
    }

    @Override
    public RealmBook getBook(RealmBook book) {
        return null;
    }

    @Override
    public Observable<RealmResults<RealmBook>> getCollection() {
        this.openRealm();

        return this.realm
                .where(RealmBook.class)
                .findAll()
                .asObservable();
    }

    @Override
    public int removeBook(RealmBook realmBook) {
        return 0;
    }

    @Override
    public void openRealm() {

    }

    @Override
    public void closeRealm() {

    }
}
