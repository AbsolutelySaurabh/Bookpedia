package dexter.appsmoniac.bookpedia.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;

/**
 * Created by absolutelysaurabh on 29/3/18.
 */

public class BookApplication extends Application{

    private static BookApplication singleton;
    private static BookApplication getInstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("booksmine.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
