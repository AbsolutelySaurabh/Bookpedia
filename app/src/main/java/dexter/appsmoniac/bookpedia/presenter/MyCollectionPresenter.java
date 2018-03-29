package dexter.appsmoniac.bookpedia.presenter;


import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import dexter.appsmoniac.bookpedia.model.dao.CollectionDao;
import dexter.appsmoniac.bookpedia.model.realm.po.RealmBook;
import dexter.appsmoniac.bookpedia.mvp.CollectionMvp;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by absolutelysaurabh on 29/3/18.
 */

public class MyCollectionPresenter implements CollectionMvp.GridPresenter{

    private CollectionMvp.Model model;
    private CollectionMvp.GridView view;

    public MyCollectionPresenter(CollectionMvp.GridView view){
        this.model = new CollectionDao();
        this.view = view;
    }
    @Override
    public void getMyCollection() {
        Observable<RealmResults<RealmBook>> results = this.model.getCollection();
        results
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        realmResults -> EventBus.getDefault().postSticky(realmResults),
                        throwable -> {
                            view.showError();
                            throwable.printStackTrace();
                        },
                        () -> Log.d("LOG", "getMyCollection complete!")
                );
    }

    @Override
    public int removeBook(RealmBook realmBook) {
        return this.model.removeBook(realmBook);
    }

    @Override
    public void closeRealm() {
        this.model.closeRealm();
    }

}
