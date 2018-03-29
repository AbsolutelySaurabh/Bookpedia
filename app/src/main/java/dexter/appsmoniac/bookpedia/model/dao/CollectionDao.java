package dexter.appsmoniac.bookpedia.model.dao;

import android.util.Log;

import dexter.appsmoniac.bookpedia.model.pojo.Book;
import dexter.appsmoniac.bookpedia.model.realm.po.RealmBook;
import dexter.appsmoniac.bookpedia.model.realm.util.RealmUtil;
import dexter.appsmoniac.bookpedia.mvp.CollectionMvp;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by absolutelysaurabh on 29/3/18.
 */

public class CollectionDao implements CollectionMvp.Model{

    private Realm realm;
    private boolean stateRealm;

    @Override
    public int saveBook(Book book) {
        this.openRealm();
        int result = BOOK_EXISTS_NOT_EXISTS;

        RealmBook realmBook = RealmUtil.convertParcelablBookToPOBook(book);
        if(this.getBook(realmBook) == null) {
            try{
                this.realm.executeTransaction(
                        realm1 -> realm.copyToRealmOrUpdate(realmBook)
                );
                result = PERSIST_OK;
            }catch (Exception e){
                Log.e("ERROR", "CollectionDAO - saveBook");
                e.printStackTrace();
                result = PERSIST_PROBLEM;
            }
        }
        return result;
    }

    @Override
    public RealmBook getBook(RealmBook realmBook) {

        this.openRealm();
        RealmResults<RealmBook> books =
                this.realm
                .where(RealmBook.class)
                .equalTo(RealmBook.ID, realmBook.getId())
                .findAll();

        if(!books.isEmpty()){
            return books.first();
        }
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

        this.openRealm();
        int result = PERSIST_PROBLEM;
        try{
            this.realm.executeTransaction(
                    realm -> {
                        realm.where(RealmBook.class)
                                .equalTo(RealmBook.ID, realmBook.getId())
                                .findAll()
                                .deleteAllFromRealm();
                    } );
                    result = PERSIST_OK;
        }catch (Exception e){
            Log.e("ERROR", "CollectionDAO - removeBook");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void openRealm() {
        this.stateRealm = true;
        if(this.realm == null){
            this.realm = Realm.getDefaultInstance();
        }
    }

    @Override
    public void closeRealm() {
        if(this.stateRealm){
            this.realm.close();
            this.realm = null;
        }
    }
}
